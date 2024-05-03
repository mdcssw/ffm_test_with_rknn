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
 * typedef struct rknn_matmul_info_t {
 *     int32_t M;
 *     int32_t K;
 *     int32_t N;
 *     rknn_matmul_type type;
 *     int16_t B_layout;
 *     int16_t B_quant_type;
 *     int16_t AC_layout;
 *     int16_t AC_quant_type;
 *     int32_t iommu_domain_id;
 *     int8_t reserved[36];
 * } rknn_matmul_info
 * }
 */
public class rknn_matmul_info extends rknn_matmul_info_t {

    rknn_matmul_info() {
        // Should not be called directly
    }
}
