// Generated by jextract

package org.rknn;

import java.lang.invoke.*;
import java.lang.foreign.*;
import java.nio.ByteOrder;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static java.lang.foreign.ValueLayout.*;
import static java.lang.foreign.MemoryLayout.PathElement.*;

/**
 * {@snippet lang=c :
 * struct _rknn_matmul_tensor_attr {
 *     char name[256];
 *     uint32_t n_dims;
 *     uint32_t dims[16];
 *     uint32_t size;
 *     rknn_tensor_type type;
 * }
 * }
 */
public class _rknn_matmul_tensor_attr {

    _rknn_matmul_tensor_attr() {
        // Should not be called directly
    }

    private static final GroupLayout $LAYOUT = MemoryLayout.structLayout(
        MemoryLayout.sequenceLayout(256, rknn_matmul_api_h.C_CHAR).withName("name"),
        rknn_matmul_api_h.C_INT.withName("n_dims"),
        MemoryLayout.sequenceLayout(16, rknn_matmul_api_h.C_INT).withName("dims"),
        rknn_matmul_api_h.C_INT.withName("size"),
        rknn_matmul_api_h.C_INT.withName("type")
    ).withName("_rknn_matmul_tensor_attr");

    /**
     * The layout of this struct
     */
    public static final GroupLayout layout() {
        return $LAYOUT;
    }

    private static final SequenceLayout name$LAYOUT = (SequenceLayout)$LAYOUT.select(groupElement("name"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * char name[256]
     * }
     */
    public static final SequenceLayout name$layout() {
        return name$LAYOUT;
    }

    private static final long name$OFFSET = 0;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * char name[256]
     * }
     */
    public static final long name$offset() {
        return name$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * char name[256]
     * }
     */
    public static MemorySegment name(MemorySegment struct) {
        return struct.asSlice(name$OFFSET, name$LAYOUT.byteSize());
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * char name[256]
     * }
     */
    public static void name(MemorySegment struct, MemorySegment fieldValue) {
        MemorySegment.copy(fieldValue, 0L, struct, name$OFFSET, name$LAYOUT.byteSize());
    }

    private static long[] name$DIMS = { 256 };

    /**
     * Dimensions for array field:
     * {@snippet lang=c :
     * char name[256]
     * }
     */
    public static long[] name$dimensions() {
        return name$DIMS;
    }
    private static final VarHandle name$ELEM_HANDLE = name$LAYOUT.varHandle(sequenceElement());

    /**
     * Indexed getter for field:
     * {@snippet lang=c :
     * char name[256]
     * }
     */
    public static byte name(MemorySegment struct, long index0) {
        return (byte)name$ELEM_HANDLE.get(struct, 0L, index0);
    }

    /**
     * Indexed setter for field:
     * {@snippet lang=c :
     * char name[256]
     * }
     */
    public static void name(MemorySegment struct, long index0, byte fieldValue) {
        name$ELEM_HANDLE.set(struct, 0L, index0, fieldValue);
    }

    private static final OfInt n_dims$LAYOUT = (OfInt)$LAYOUT.select(groupElement("n_dims"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * uint32_t n_dims
     * }
     */
    public static final OfInt n_dims$layout() {
        return n_dims$LAYOUT;
    }

    private static final long n_dims$OFFSET = 256;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * uint32_t n_dims
     * }
     */
    public static final long n_dims$offset() {
        return n_dims$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * uint32_t n_dims
     * }
     */
    public static int n_dims(MemorySegment struct) {
        return struct.get(n_dims$LAYOUT, n_dims$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * uint32_t n_dims
     * }
     */
    public static void n_dims(MemorySegment struct, int fieldValue) {
        struct.set(n_dims$LAYOUT, n_dims$OFFSET, fieldValue);
    }

    private static final SequenceLayout dims$LAYOUT = (SequenceLayout)$LAYOUT.select(groupElement("dims"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * uint32_t dims[16]
     * }
     */
    public static final SequenceLayout dims$layout() {
        return dims$LAYOUT;
    }

    private static final long dims$OFFSET = 260;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * uint32_t dims[16]
     * }
     */
    public static final long dims$offset() {
        return dims$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * uint32_t dims[16]
     * }
     */
    public static MemorySegment dims(MemorySegment struct) {
        return struct.asSlice(dims$OFFSET, dims$LAYOUT.byteSize());
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * uint32_t dims[16]
     * }
     */
    public static void dims(MemorySegment struct, MemorySegment fieldValue) {
        MemorySegment.copy(fieldValue, 0L, struct, dims$OFFSET, dims$LAYOUT.byteSize());
    }

    private static long[] dims$DIMS = { 16 };

    /**
     * Dimensions for array field:
     * {@snippet lang=c :
     * uint32_t dims[16]
     * }
     */
    public static long[] dims$dimensions() {
        return dims$DIMS;
    }
    private static final VarHandle dims$ELEM_HANDLE = dims$LAYOUT.varHandle(sequenceElement());

    /**
     * Indexed getter for field:
     * {@snippet lang=c :
     * uint32_t dims[16]
     * }
     */
    public static int dims(MemorySegment struct, long index0) {
        return (int)dims$ELEM_HANDLE.get(struct, 0L, index0);
    }

    /**
     * Indexed setter for field:
     * {@snippet lang=c :
     * uint32_t dims[16]
     * }
     */
    public static void dims(MemorySegment struct, long index0, int fieldValue) {
        dims$ELEM_HANDLE.set(struct, 0L, index0, fieldValue);
    }

    private static final OfInt size$LAYOUT = (OfInt)$LAYOUT.select(groupElement("size"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * uint32_t size
     * }
     */
    public static final OfInt size$layout() {
        return size$LAYOUT;
    }

    private static final long size$OFFSET = 324;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * uint32_t size
     * }
     */
    public static final long size$offset() {
        return size$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * uint32_t size
     * }
     */
    public static int size(MemorySegment struct) {
        return struct.get(size$LAYOUT, size$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * uint32_t size
     * }
     */
    public static void size(MemorySegment struct, int fieldValue) {
        struct.set(size$LAYOUT, size$OFFSET, fieldValue);
    }

    private static final OfInt type$LAYOUT = (OfInt)$LAYOUT.select(groupElement("type"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * rknn_tensor_type type
     * }
     */
    public static final OfInt type$layout() {
        return type$LAYOUT;
    }

    private static final long type$OFFSET = 328;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * rknn_tensor_type type
     * }
     */
    public static final long type$offset() {
        return type$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * rknn_tensor_type type
     * }
     */
    public static int type(MemorySegment struct) {
        return struct.get(type$LAYOUT, type$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * rknn_tensor_type type
     * }
     */
    public static void type(MemorySegment struct, int fieldValue) {
        struct.set(type$LAYOUT, type$OFFSET, fieldValue);
    }

    /**
     * Obtains a slice of {@code arrayParam} which selects the array element at {@code index}.
     * The returned segment has address {@code arrayParam.address() + index * layout().byteSize()}
     */
    public static MemorySegment asSlice(MemorySegment array, long index) {
        return array.asSlice(layout().byteSize() * index);
    }

    /**
     * The size (in bytes) of this struct
     */
    public static long sizeof() { return layout().byteSize(); }

    /**
     * Allocate a segment of size {@code layout().byteSize()} using {@code allocator}
     */
    public static MemorySegment allocate(SegmentAllocator allocator) {
        return allocator.allocate(layout());
    }

    /**
     * Allocate an array of size {@code elementCount} using {@code allocator}.
     * The returned segment has size {@code elementCount * layout().byteSize()}.
     */
    public static MemorySegment allocateArray(long elementCount, SegmentAllocator allocator) {
        return allocator.allocate(MemoryLayout.sequenceLayout(elementCount, layout()));
    }

    /**
     * Reinterprets {@code addr} using target {@code arena} and {@code cleanupAction) (if any).
     * The returned segment has size {@code layout().byteSize()}
     */
    public static MemorySegment reinterpret(MemorySegment addr, Arena arena, Consumer<MemorySegment> cleanup) {
        return reinterpret(addr, 1, arena, cleanup);
    }

    /**
     * Reinterprets {@code addr} using target {@code arena} and {@code cleanupAction) (if any).
     * The returned segment has size {@code elementCount * layout().byteSize()}
     */
    public static MemorySegment reinterpret(MemorySegment addr, long elementCount, Arena arena, Consumer<MemorySegment> cleanup) {
        return addr.reinterpret(layout().byteSize() * elementCount, arena, cleanup);
    }
}
