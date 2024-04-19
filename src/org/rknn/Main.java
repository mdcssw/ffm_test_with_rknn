package org.rknn;

import java.lang.foreign.*;

import static org.rknn._rknn_matmul_tensor_attr.*;
import static org.rknn.rknn_matmul_api_h.*;

import org.rknn.rknn_matmul_info_t;
import org.rknn.rknn_matmul_io_attr;
import org.rknn.rknn_matmul_api_h;
import static org.rknn.rknn_tensor_mem.*;

public class Main {

	
    public static void create_random_matrix(Arena arena, MemorySegment mem, int size){
	//System.out.println("memory = " + mem);
	for (int i=0; i < size; i++) {
	    MemorySegment ptr = arena.allocate(ValueLayout.JAVA_FLOAT, size);
	    //System.out.println(i + " " + ptr);
	    mem.setAtIndex(AddressLayout.ADDRESS, i , ptr);
	    
	    MemorySegment memCheck = mem.getAtIndex(AddressLayout.ADDRESS, i);
	    //System.out.println(ptr.equals(memCheck) + " " + (ptr == memCheck) + " initial mem " + ptr + " memCheck = " + memCheck);
	    
	    for (long j=0 ; j < size; j++) {
		float randomNumber = (float)Math.random()*10;
		//System.out.println(j + " " + randomNumber);
	        ptr.setAtIndex(ValueLayout.JAVA_FLOAT , j, randomNumber);
		//float check = mem.getAtIndex(AddressLayout.ADDRESS, i).getAtIndex(ValueLayout.JAVA_FLOAT , j);
		//System.out.println((randomNumber == check) + " initial float " + randomNumber + " check = " + check);
	    }
	}
	//System.out.println("DONE random_matrix");
    }
    

    static void print_matrix_data(MemorySegment mem, int size){

	System.out.println("rknn matrice: " + mem);
	System.out.println("");
  
	for (int i = 0; i < size; ++i) {
	    System.out.print(i);
	    //System.out.print(" ");
	    MemorySegment internalPtr = mem.getAtIndex(AddressLayout.ADDRESS,i).reinterpret(size*4); //4 bytes
	    //System.out.println(internalPtr);
	    System.out.print("-");
	    for (int j = 0; j < size; ++j) {
	         float v = internalPtr.getAtIndex(ValueLayout.JAVA_FLOAT, j);
		 System.out.print(v);
		 System.out.print(" ");
	    }
	     System.out.println("");
	}
	System.out.println("rknn matrice /end");
    }      

    public static void bench(int size){
	try (Arena arena = Arena.ofConfined()) {
	    MemorySegment infos = rknn_matmul_info_t.allocate(arena);
	    MemorySegment context = arena.allocate(rknn_matmul_ctx,1);
	    MemorySegment ioAttr = rknn_matmul_io_attr.allocate(arena);
	    
	    rknn_matmul_info_t.M(infos,size);
	    rknn_matmul_info_t.K(infos,size);
	    rknn_matmul_info_t.N(infos,size);
	    rknn_matmul_info_t.type(infos,rknn_matmul_api_h.RKNN_FLOAT16_MM_FLOAT16_TO_FLOAT32());
	    rknn_matmul_info_t.B_layout(infos,(short) 0);
	    rknn_matmul_info_t.AC_layout(infos,(short) 0);

	    int ret = rknn_matmul_create(context,infos,ioAttr);
	    if (ret < 0) {
		System.out.println("rknn_matmul_create failed ! ret= " + ret);
	        System.exit(ret);
	    }

	    MemorySegment A = rknn_create_mem(context.get(rknn_matmul_ctx,0),
					      rknn_matmul_tensor_attr.size(rknn_matmul_io_attr.A(ioAttr)));
	    MemorySegment B = rknn_create_mem(context.get(rknn_matmul_ctx,0),
					      rknn_matmul_tensor_attr.size(rknn_matmul_io_attr.B(ioAttr)));
	    MemorySegment C = rknn_create_mem(context.get(rknn_matmul_ctx,0),
					      rknn_matmul_tensor_attr.size(rknn_matmul_io_attr.C(ioAttr)));
	    
	    create_random_matrix(arena, virt_addr(A), size);
	    //print_matrix_data(virt_addr(A), size);
	    create_random_matrix(arena, virt_addr(B), size);
	    //print_matrix_data(virt_addr(B), size);
	    //create_random_matrix(arena, virt_addr(C), size);
	    //print_matrix_data(virt_addr(C), size);

	    rknn_matmul_set_io_mem(context.get(rknn_matmul_ctx,0),
				   A,rknn_matmul_io_attr.A(ioAttr));
	    rknn_matmul_set_io_mem(context.get(rknn_matmul_ctx,0),
				   B,rknn_matmul_io_attr.B(ioAttr));
	    rknn_matmul_set_io_mem(context.get(rknn_matmul_ctx,0),
				   C,rknn_matmul_io_attr.C(ioAttr));

//	    System.out.println("Trying to multiply stuff");
	    Timer timer = new Timer();
	    timer.pause();
	    for(int i = 0 ; i < 100 ; ++i){
		timer.play();
		rknn_matmul_run(context.get(rknn_matmul_ctx,0));
		timer.pause();
	    }
	    System.out.println("Size = " + size + " time = " + timer.check() / 100);
	}
    }

    //http://www.itu.dk/~sestoft/papers/benchmarking.pdf
    public static class Timer {
	private long start, spent = 0;
	public Timer() { play(); }
	public double check() { return (System.nanoTime()-start+spent)/1e9; }
	public void pause() { spent += System.nanoTime()-start; }
	public void play() { System.gc(); start = System.nanoTime(); }
    };
	
    public static void main(String[] args) {
	bench(256);
	bench(512);
	bench(1024);
	bench(2048);
	bench(4096);
	bench(8192);
    }
}
