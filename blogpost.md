# Introduction

Do you know Foreign Function Memory (FFM) ?
It's a part of the Java runtime that allows developer to directly manipulate virtual memory contents.
It has been in development for the past few JDKs, and is now coming as a fully-fledged feature into [JDK 22](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/lang/foreign/package-summary.html)!
We've been taking a look at this latest iteration, and how to do stuff with it.

Moreover, the tool [`jextract`](https://github.com/openjdk/jextract) generates Java sources from a C header, thus providing a primitive way to call functions provided by the native library.
Since we're working on embedded development at this time, we took an example that is specific to embedded development.
We're multiplying matrices on a Neural Processing Unit (a.k.a., NPU, basically a specialized processor).
We'll compare the performances with similar C++ code.

As the *JDK Enhancement Proposal* for FFM is quite detailed, we'll try to give you a simpler overview and examples.

# What is Foreign Function Memory (FFM), and how do I use it?

/!\ As the API has been changing over the last few iterations, be sure to check the documentation for the version you plan to use: [JDK22 iteration](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/lang/foreign/package-summary.html)!
Very basically, FFM is trying to replace JNI, with a particular focus on *safety*, *performance* and *generality*.
So, this provides a new way to work with virtual memory contents and native libraries.
The use of a bunch of native libraries is quite fashionable at the moment, particularly in the AI world.

## 5 minutes tutorial

Since it has a strong focus on native library linking, let's link some libraries!
Notice that the following are full examples.
They are compilable and executable with a JDK 22, such as this one: **TODOLINK**.

### Find some native code

This small program simply looks for a function, that you pass as argument, in commonly used libraries.
To do that, it first creates a linker, which is then going to search commonly used libraries via the `defaultLookup()` method.
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

The important point to note here, is that native libraries are found by an instance of the [`Linker` class](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/lang/foreign/Linker.html).

### Execute some native code

More interestingly, the following example actually executes a function implemented by commonly used libraries: [`abs`](https://cplusplus.com/reference/cstdlib/abs/).

This code starts by creating a Java object that knows how to execute the native function.
To create the [`MethodHandle`](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/lang/invoke/MethodHandle.html) instance, it needs:
1. The address of the function that we found in the previous code snippet, and
2. The method type signature, that we can find in a C header or the documentation.

Afterward, we need a way to pass our arguments to the native function.
For that purpose, we use an arena.
An arena is a block of virtual memory whose contents you can modify, and that is outside the Java heap.
Inside the arena, we can allocate some memory blocks, represented by [`MemorySegment`](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/lang/foreign/MemorySegment.html).
A `MemorySegment` holds a *pointer* to the memory block it represents, and sometimes also the *size* of the memory block.
Here, we allocate memory space for two 32-bits signed integers.
Then, we set their values with the method [`setAtIndex`](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/lang/foreign/MemorySegment.html#setAtIndex(java.lang.foreign.ValueLayout.OfInt,long,int)).

Finally, once our function and arguments are ready, we call [`invokeExact`](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/lang/invoke/MethodHandle.html#invokeExact(java.lang.Object...)) to execute the native function, and retrieve its return value.

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

The most important thing to remember here is that we have to specify the type for every operation related to the virtual memory accesses, using the layouts defined in [`ValueLayout`](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/lang/foreign/ValueLayout.html).

# A more tasteful example

We implemented a micro-benchmark of matrix multiplication on a Neural Processing Unit.
[KNN-Toolkit](https://github.com/rockchip-linux/rknn-toolkit) provides a way to use *Rockchip* NPUs on devices that have them.
The toolkit gives us the [shared library (`librknnrt.so`)](https://github.com/rockchip-linux/rknn-toolkit2/blob/master/rknpu2/runtime/Android/librknn_api/armeabi-v7a/librknnrt.so) as well as the [C header file (`rknn_matmul_api.h`)](https://github.com/rockchip-linux/rknn-toolkit2/blob/master/rknpu2/runtime/Android/librknn_api/include/rknn_matmul_api.h) in order to communicate with the NPU.
To evaluate whether that would be useful for our embedded Java, we tested it!
To do that, we used [`jextract`](https://github.com/openjdk/jextract) to generate a bunch of Java boilerplate code, and we used it to write a Java program that uses the NPU.
On top of that, we developed code similar to a C++ micro-benchmark implemented by **TODOADDAUTHORANDLINK**.

## Data first, code later

```shell
roc_rk3588s_pc:/data/microdoc # ./jdk/bin/java --enable-native-access=ALL-UNNAMED -cp . org.rknn.JavaMicroBenchmarks

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
```

## Experimental setup

We executed two micro-benchmarks:
- A C++ micro-benchmark, which is a slightly modified version of this [blog post](https://clehaxze.tw/gemlog/2023/09-02-benchmarking-rk3588-npu-matrix-multiplcation-performance-ep2.gmi);
- A Java micro-benchmark that is very similar to the C++ one, written from scratch, with the help of Java sources generated by `jextract`.

We run both of those micro-benchmark on the [`rk3588s`](https://en.t-firefly.com/product/industry/rocrk3588spc) device through ADB.
`rkn3588s` runs a 64-bits Android operating system.

Those benchmarks both allocate two square matrices of some size provided as a parameter.
The benchmark covers **only** the call to the framework multiplying the matrices [`rknn_matmul_run`](https://github.com/rockchip-linux/rknn-toolkit2/blob/master/rknpu2/runtime/Android/librknn_api/include/rknn_matmul_api.h#L346).
We're multiplying those matrices together one hundred times, before reporting the average execution time.
The point of those benchmarks is to calculate the overhead of using Java while multiplying matrices relative to the matrices size, compared to matrix multiplication speed.

## So, how much does it cost me to use Java ?

**TODO**: Add the baseline to the table to show where the percentages come from.

| Matrix size | C++     | Java    | Relative speed |
| ----------- | ------- | ------- | -------------- |
| 256         | 0.00027 | 0.00126 | 21.43%        |
| 512         | 0.00088 | 0.00385 | 22.86%        |
| 1024        | 0.00441 | 0.01159 | 38.05%        |
| 2048        | 0.03026 | 0.06324 | 47.85%        |
| 4096        | 0.78822 | 0.84755 | 92.99%        |
| 8192        | 14.7231 | 14.9756 | 98.31%        |

From that table, we can clearly see that the bigger the matrix is, the more time is spent in the NPU.

On small matrix multiplications, the context switch imposes a heavy toll.
> **TODO** How do you know? ---------^^^^^^^^^^^^^^

But on bigger matrices, it is barely noticeable!

## Important FFM bits

We're now explaining some details that were not obvious to us, while we were writing this blog post, and the accompanying examples and benchmarks.

### Pointer operations

The data pointed to by a given `MemorySegment` can be interpreted by the method `get` and `getAtIndex`.
You also have to provide a layout, to tell the FFM framework how many bits the framework should read, and how it should interpret them.
The method `getAtIndex` is useful when the data you manipulate is an array of the same data type, whereas `get` makes more sense in the case of structures.
In our example, we use `getAtIndex` multiple time.
There are instances of `get` uses in the code generated by `jextract`.
Similarly, use `set` an `setAtIndex` to set the bits representing your data.

There is a caveat that I want to address though, which we encountered while trying to check that our matrices where correct by printing them.
A `MemorySegment` checks that memory access are in bounds, given the *size* it contains.
When you create a new `MemorySegment` via `Arena.allocate`, it comes with the correct size.
The [following code](https://github.com/hogoww/rknn/blob/main/src/org/rknn/JavaMicroBenchmarks.java#L22) works: **TODOCHECKLINE**

```java
// Allocating a new array of floats, and setting its first element to a random float.
MemorySegment ptr = arena.allocate(ValueLayout.JAVA_FLOAT, size);
ptr.setAtIndex(ValueLayout.JAVA_FLOAT, 0, (float) Math.random() * 10);
```

However, calling `MemorySegment.getAtIndex` (see the [following code](https://github.com/hogoww/rknn/blob/main/src/org/rknn/JavaMicroBenchmarks.java)) creates a `MemorySegment` with an **incorrect size**: **TODOCHECKLINE**

```java
// Trying to access some row of the matrix, and then an element of that row.
MemorySegment row = matrix.getAtIndex(AddressLayout.ADDRESS, 0);
float v = row.getAtIndex(ValueLayout.JAVA_FLOAT, 0);
// An exception is thrown!
```

The exception is thrown because the `MemorySegment` object checks that memory accesses are in bounds.
When a pointer is freshly allocated, the `MemorySegment` knows its size.
However, when a `MemorySegment` object is created from an arbitrary pointer, the object doesn't know the size of the memory block that the pointer points to.
In that case, the `MemorySegment` object holds a size of zero, which causes every memory access to fail bound checks.

In order to correct that previous snippet, you can do the following:

```java
// Trying to access some row of the matrix, and then an element of that row.
MemorySegment row = matrix.getAtIndex(AddressLayout.ADDRESS, 0).reinterpret(size * Float.BYTES);
float v = row.getAtIndex(ValueLayout.JAVA_FLOAT, 0);
```

[`MemorySegment.reinterpret`](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/lang/foreign/MemorySegment.html#reinterpret(long)) sets the size of the memory block pointed to by the `MemorySegment` object, and thus how much memory you're allowed to access from that `MemorySegment`.

Note that `jextract` works around this issue, by providing `MemorySegment` objects whose sizes are `Long.MAX_VALUE`, whenever a pointer is returned from a function call.
But using that size essentially disables bound checks, which can cause unintended out-of-bounds accesses.

### Layouts

The primitive types use layouts defined in [`ValueLayout`](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/lang/foreign/ValueLayout.html).
For addresses, you should use an [`AddressLayout`](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/lang/foreign/AddressLayout.html).
For more complex layouts such as structures, you might want to use a [`MemoryLayout`](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/lang/foreign/MemoryLayout.html) instead.

# Conclusion

In this blog post you saw how to use the new feature *Foreign Function Memory* in JDK 22.
You read some very basic examples as well as a real world example of using FFM to use an NPU.
Finally, we described details that were not obvious to us.

# Possible other sections, not sure how they'd look.

- Lesson learned
- Comparison to FFI?
- Advantages and drawbacks
