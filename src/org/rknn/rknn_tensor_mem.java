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
 * typedef struct _rknn_tensor_memory {
 *     void *virt_addr;
 *     uint64_t phys_addr;
 *     int32_t fd;
 *     int32_t offset;
 *     uint32_t size;
 *     uint32_t flags;
 *     void *priv_data;
 * } rknn_tensor_mem
 * }
 */
public class rknn_tensor_mem extends _rknn_tensor_memory {

    rknn_tensor_mem() {
        // Should not be called directly
    }
}
