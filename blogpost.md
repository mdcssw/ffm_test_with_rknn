# Introduction

Do you know Foreign Function Memory(FFM) ?  
It's a part of the Java runtime that allows developer to manipulate raw memory.  
It's been in development for the past 3 JDKs, and is now coming as a full-fledged into [JDK 22](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/lang/foreign/package-summary.html) !  
We've been taking a look at this last iteration, and how to do stuff with it.  
Moreover, the tool [jextract](https://github.com/openjdk/jextract) generates java sources from a C header, allowing quick binding to a native library.
Since we're working on embedded development at this time, we took an example specific to embedded development.  
We're trying to multiply matrices on an Neural Processing Unit (aka NPU, basically a specialized CPU).  
We'll compare the performances with similar C++ program on matrix multiplications. 

As the JDK Enhancement Proposal is quite detailed, we'll try to give you a simpler overview and examples.

# What is Foreign Function Memory, and how do I use it?

/!\ As the API has changed over the last few iterations, be sure to check the documentation for its latest version: [JDK22 iteration](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/lang/foreign/package-summary.html) !  
Very basically, FFM is trying to replace JNI, with particular focusses on safety, performance and generality.  
So, this provides a new way to work with raw memory and native library.  
A bunch of them are fashionable, particularly in the AI world currently.


## 5 minutes tutorial

Since it has a strong focus on native library linking, let's link some libraries !
Note, the following examples are full examples.
They are compilable & executable with a JDK 22 such as this one TODOLINK.

### Find some native code.

This small program simply looks for a function that you pass as argument into stdlib.
To do that, it first create a linker which is going to lookup stdlib via `defaultLookup1()` message send.
Then we can search for our native function and print its address.
```java
import java.lang.foreign.Linker;
import java.lang.foreign.SymbolLookup;
import java.lang.foreign.MemorySegment;
import java.util.Optional;

public class FindStdLibFunction {
    public static void main(String[] args) {
        Linker linker = Linker.nativeLinker();
        SymbolLookup stdlib = linker.defaultLookup();
        Optional<MemorySegment> stdlibFunction = stdlib.find(args[0]).orElseThrow();
        System.out.println(stdlibFunction.toString());
    }
}
```
The important points to note here, are that native libraries are found by an instance of the [Linker class](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/lang/foreign/Linker.html).

### Execute some native code
More interestingly, the following example actually executes a stdlib function: [abs](https://cplusplus.com/reference/cstdlib/abs/).

This code starts by creating a java object that knows how to execute the native function.
To create the [MethodHandle](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/lang/invoke/MethodHandle.html), it needs:
1. The address of the function that, we found in the previous code snippet;
2. The method type signature, that we can find in a C header or the documentation.

Second, we need a way to pass our arguments to the native function.
For that purpose, we use an arena.
An arena basically represent a block of raw memory that is outside of the java heap that you can manipulate as raw memory.
Arena can allocate some pointers, [MemorySegment](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/lang/foreign/MemorySegment.html), towards the memory it represents.
Here, we allocate the space for two integers.
Then, we set their values with the method [setAtIndex](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/lang/foreign/MemorySegment.html#setAtIndex(java.lang.foreign.ValueLayout.OfInt,long,int)).

Finally, once our function and our arguments are ready, we use [invokeExact](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/lang/invoke/MethodHandle.html#invokeExact(java.lang.Object...)) to execute the native function.  
```java
import java.lang.foreign.Arena;
import java.lang.foreign.Linker;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;
import java.util.Optional;

public class AbsTestRun {
  public static void main(String[] args) {
	  
	  MethodHandle absMethodHandle =
	    linker.downcallHandle(
				  Linker.nativeLinker().defaultLookup().find("abs").orElseThrow(),
				  FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT));

    try (Arena arena = Arena.ofConfined()) {
	    MemorySegment ptr = arena.allocate(ValueLayout.JAVA_INT, 2);
	    ptr.setAtIndex(ValueLayout.JAVA_INT , 0, 10);
	    ptr.setAtIndex(ValueLayout.JAVA_INT , 1, -10);

	    int positive = (int)absMethodHandle.invokeExact(ptr.getAtIndex(ValueLayout.JAVA_INT, 0));
	    int negative = (int)absMethodHandle.invokeExact(ptr.getAtIndex(ValueLayout.JAVA_INT, 1));
	    
	    System.out.println("abs of negative = " + negative + " positive = " + positive);
	  } catch(Throwable e){ e.printStackTrace(); }
  }
}
```
The most important thing to remember here is that we have to type every operation related to the raw memory, by using layouts defined in [ValueLayout](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/lang/foreign/ValueLayout.html).  

# A more tasteful example

We implemented a microbenchmark of matrice multiplication on a Neural Processing Unit (NPU).
[KNN-Toolkit](https://github.com/rockchip-linux/rknn-toolkit) provides users a way to use Rockchip NPUs.
The toolkit gives us the [library file](https://github.com/rockchip-linux/rknn-toolkit2/blob/master/rknpu2/runtime/Android/librknn_api/armeabi-v7a/librknnrt.so) (.so) as well as the [header file](https://github.com/rockchip-linux/rknn-toolkit2/blob/master/rknpu2/runtime/Android/librknn_api/include/rknn_matmul_api.h) to communicate with it.
To evaluate whether that'd be useful for our embedded java, we tested it !
To do that, we used [jextract](https://github.com/openjdk/jextract) to generate a bunch of java boilerplate code, and we used it to write a java program that uses the NPU.
On top of that, we developed a code similar to a C++ microbench done by TODO & TOASK

## Data first, code later 

./jdk/bin/java --enable-native-access=ALL-UNNAMED -cp . org.rknn.JavaMicroBenchmarks  

Size = 256 seconds = 0.00126035807  
Size = 512 seconds = 0.0038540170400000002  
Size = 1024 seconds = 0.0115888624  
Size = 2048 seconds = 0.06323490967999999  
Size = 4096 seconds = 0.84754899555  
Size = 8192 seconds = 14.975589004349999  

roc_rk3588s_pc:/data/microdoc # ./cppMicroBenchmarks  
Size = 256 seconds = 0.00027  
Size = 512 seconds = 0.00088  
Size = 1024 seconds = 0.0044  
Size = 2048 seconds = 0.03026  
Size = 4096 seconds = 0.78822  
Size = 8192 seconds = 14.7231  

## Experimental setup

We executed two microbenchmarks:
- A C++ microbenchmark, which is a slightly modified version of this [blogpost](https://clehaxze.tw/gemlog/2023/09-02-benchmarking-rk3588-npu-matrix-multiplcation-performance-ep2.gmi);  
- A Java microbenchmark very similar to the C++ one, written from scratch, with the help of the java files generated jextract.  

We run both of those microbenchmark on the [rk3588s](https://en.t-firefly.com/product/industry/rocrk3588spc) device through ADB.  
rkn3588s is an android 64 bit system.  

Those benchmarks both allocate two square matrices of some size provided as parameters.
The values benchmarked code is **only** the call to the framework multiplying the matrices [rknn_matmul_run](https://github.com/rockchip-linux/rknn-toolkit2/blob/master/rknpu2/runtime/Android/librknn_api/include/rknn_matmul_api.h#L346).  
We're multipling those matrices together a hundred time, and report the average execution time.
The point of those benchmarks is to calculate the overhead of using java while multiplying matrices relative to the matrices size, in regard to matrice multiplication speed.  


## Important FFM bits

### Pointer operations

[MemorySegment](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/lang/foreign/MemorySegment.html) are basically pointers objects with safeguards.
The data pointed by a given memory can be interpreted by the method get and getAtIndex.
You also have to provide a layout, to tell the framework FFM how much bits the framework should read, and how it should interpret them.  
The method `getAtIndex` is useful when you the data you manipulate is a collection of the same data type, whereas `get` makes more sense in the case of structures.  
In our example, we use `getAtIndex` multiple time.
There are instances of `get` uses in the Jextract generated code.
Similarly, use `set` an `setAtIndex` to set the bits representing your data.

There is a caveat that I want to address though.
All 4 of those methods are checking that the memory you are accessing is indeed accessible from that pointer.
When you create a new pointer, it comes with the expected size.
The [following code](https://github.com/hogoww/rknn/blob/main/src/org/rknn/JavaMicroBenchmarks.java#L22) works: TODOCHECKLINE
```
MemorySegment ptr = arena.allocate(ValueLayout.JAVA_FLOAT, size);
ptr.setAtIndex(ValueLayout.JAVA_FLOAT , j, (float)Math.random()*10);
```
However, the [following code](https://github.com/hogoww/rknn/blob/main/src/org/rknn/JavaMicroBenchmarks.java) doesn't: TODOCHECKLINE
```
TODO SNIPPET WITH GET
```

### Layouts
The primitive types use layouts defined in [ValueLayout](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/lang/foreign/ValueLayout.html).  
For addresses, you should use an [AddressLayout](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/lang/foreign/AddressLayout.html).
For more complex layouts such as strucs, you might want to use a [MemoryLayout](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/lang/foreign/MemoryLayout.html) instead.




## So, how much does it cost me to use java ?
# Lesson learned 
# Comparison to FFI?
# Advantages and inconvenients
