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
 * typedef struct _rknn_output {
 *     uint8_t want_float;
 *     uint8_t is_prealloc;
 *     uint32_t index;
 *     void *buf;
 *     uint32_t size;
 * } rknn_output
 * }
 */
public class rknn_output extends _rknn_output {

    rknn_output() {
        // Should not be called directly
    }
}

