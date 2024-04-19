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
 * struct _rknn_quant_params {
 *     char name[256];
 *     float *scale;
 *     int32_t scale_len;
 *     int32_t *zp;
 *     int32_t zp_len;
 * }
 * }
 */
public class _rknn_quant_params {

    _rknn_quant_params() {
        // Should not be called directly
    }

    private static final GroupLayout $LAYOUT = MemoryLayout.structLayout(
        MemoryLayout.sequenceLayout(256, rknn_matmul_api_h.C_CHAR).withName("name"),
        rknn_matmul_api_h.C_POINTER.withName("scale"),
        rknn_matmul_api_h.C_INT.withName("scale_len"),
        MemoryLayout.paddingLayout(4),
        rknn_matmul_api_h.C_POINTER.withName("zp"),
        rknn_matmul_api_h.C_INT.withName("zp_len"),
        MemoryLayout.paddingLayout(4)
    ).withName("_rknn_quant_params");

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

    private static final AddressLayout scale$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("scale"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * float *scale
     * }
     */
    public static final AddressLayout scale$layout() {
        return scale$LAYOUT;
    }

    private static final long scale$OFFSET = 256;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * float *scale
     * }
     */
    public static final long scale$offset() {
        return scale$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * float *scale
     * }
     */
    public static MemorySegment scale(MemorySegment struct) {
        return struct.get(scale$LAYOUT, scale$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * float *scale
     * }
     */
    public static void scale(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(scale$LAYOUT, scale$OFFSET, fieldValue);
    }

    private static final OfInt scale_len$LAYOUT = (OfInt)$LAYOUT.select(groupElement("scale_len"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int32_t scale_len
     * }
     */
    public static final OfInt scale_len$layout() {
        return scale_len$LAYOUT;
    }

    private static final long scale_len$OFFSET = 264;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int32_t scale_len
     * }
     */
    public static final long scale_len$offset() {
        return scale_len$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int32_t scale_len
     * }
     */
    public static int scale_len(MemorySegment struct) {
        return struct.get(scale_len$LAYOUT, scale_len$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int32_t scale_len
     * }
     */
    public static void scale_len(MemorySegment struct, int fieldValue) {
        struct.set(scale_len$LAYOUT, scale_len$OFFSET, fieldValue);
    }

    private static final AddressLayout zp$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("zp"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int32_t *zp
     * }
     */
    public static final AddressLayout zp$layout() {
        return zp$LAYOUT;
    }

    private static final long zp$OFFSET = 272;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int32_t *zp
     * }
     */
    public static final long zp$offset() {
        return zp$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int32_t *zp
     * }
     */
    public static MemorySegment zp(MemorySegment struct) {
        return struct.get(zp$LAYOUT, zp$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int32_t *zp
     * }
     */
    public static void zp(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(zp$LAYOUT, zp$OFFSET, fieldValue);
    }

    private static final OfInt zp_len$LAYOUT = (OfInt)$LAYOUT.select(groupElement("zp_len"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int32_t zp_len
     * }
     */
    public static final OfInt zp_len$layout() {
        return zp_len$LAYOUT;
    }

    private static final long zp_len$OFFSET = 280;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int32_t zp_len
     * }
     */
    public static final long zp_len$offset() {
        return zp_len$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int32_t zp_len
     * }
     */
    public static int zp_len(MemorySegment struct) {
        return struct.get(zp_len$LAYOUT, zp_len$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int32_t zp_len
     * }
     */
    public static void zp_len(MemorySegment struct, int fieldValue) {
        struct.set(zp_len$LAYOUT, zp_len$OFFSET, fieldValue);
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

