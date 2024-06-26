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
 * struct _rknn_matmul_shape {
 *     int32_t M;
 *     int32_t K;
 *     int32_t N;
 * }
 * }
 */
public class _rknn_matmul_shape {

    _rknn_matmul_shape() {
        // Should not be called directly
    }

    private static final GroupLayout $LAYOUT = MemoryLayout.structLayout(
        rknn_matmul_api_h.C_INT.withName("M"),
        rknn_matmul_api_h.C_INT.withName("K"),
        rknn_matmul_api_h.C_INT.withName("N")
    ).withName("_rknn_matmul_shape");

    /**
     * The layout of this struct
     */
    public static final GroupLayout layout() {
        return $LAYOUT;
    }

    private static final OfInt M$LAYOUT = (OfInt)$LAYOUT.select(groupElement("M"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int32_t M
     * }
     */
    public static final OfInt M$layout() {
        return M$LAYOUT;
    }

    private static final long M$OFFSET = 0;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int32_t M
     * }
     */
    public static final long M$offset() {
        return M$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int32_t M
     * }
     */
    public static int M(MemorySegment struct) {
        return struct.get(M$LAYOUT, M$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int32_t M
     * }
     */
    public static void M(MemorySegment struct, int fieldValue) {
        struct.set(M$LAYOUT, M$OFFSET, fieldValue);
    }

    private static final OfInt K$LAYOUT = (OfInt)$LAYOUT.select(groupElement("K"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int32_t K
     * }
     */
    public static final OfInt K$layout() {
        return K$LAYOUT;
    }

    private static final long K$OFFSET = 4;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int32_t K
     * }
     */
    public static final long K$offset() {
        return K$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int32_t K
     * }
     */
    public static int K(MemorySegment struct) {
        return struct.get(K$LAYOUT, K$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int32_t K
     * }
     */
    public static void K(MemorySegment struct, int fieldValue) {
        struct.set(K$LAYOUT, K$OFFSET, fieldValue);
    }

    private static final OfInt N$LAYOUT = (OfInt)$LAYOUT.select(groupElement("N"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int32_t N
     * }
     */
    public static final OfInt N$layout() {
        return N$LAYOUT;
    }

    private static final long N$OFFSET = 8;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int32_t N
     * }
     */
    public static final long N$offset() {
        return N$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int32_t N
     * }
     */
    public static int N(MemorySegment struct) {
        return struct.get(N$LAYOUT, N$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int32_t N
     * }
     */
    public static void N(MemorySegment struct, int fieldValue) {
        struct.set(N$LAYOUT, N$OFFSET, fieldValue);
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

