package com.boaz.lp.common.dto

import java.time.LocalDateTime

/**
 * Generic API Response wrapper
 */
data class ApiResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val error: ErrorResponse? = null,
    val timestamp: LocalDateTime = LocalDateTime.now()
) {
    companion object {
        fun <T> success(data: T): ApiResponse<T> {
            return ApiResponse(
                success = true,
                data = data,
                error = null
            )
        }

        fun <T> error(error: ErrorResponse): ApiResponse<T> {
            return ApiResponse(
                success = false,
                data = null,
                error = error
            )
        }

        fun <T> error(code: String, message: String, details: String? = null): ApiResponse<T> {
            return ApiResponse(
                success = false,
                data = null,
                error = ErrorResponse(code, message, details)
            )
        }
    }
}

/**
 * Error response details
 */
data class ErrorResponse(
    val code: String,
    val message: String,
    val details: String? = null
)

/**
 * Pagination information
 */
data class PageInfo(
    val page: Int,
    val size: Int,
    val totalElements: Long,
    val totalPages: Int,
    val isFirst: Boolean,
    val isLast: Boolean
)

/**
 * Paginated response
 */
data class PagedResponse<T>(
    val content: List<T>,
    val pageInfo: PageInfo
)
