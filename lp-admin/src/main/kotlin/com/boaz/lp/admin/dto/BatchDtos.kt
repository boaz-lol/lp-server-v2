package com.boaz.lp.admin.dto

import java.time.LocalDateTime

data class JobExecutionDto(
    val id: Long,
    val jobName: String,
    val status: String,
    val startTime: LocalDateTime?,
    val endTime: LocalDateTime?,
    val exitCode: String?,
    val exitMessage: String?,
    val createTime: LocalDateTime
)

data class StepExecutionDto(
    val id: Long,
    val stepName: String,
    val status: String,
    val readCount: Long,
    val writeCount: Long,
    val commitCount: Long,
    val rollbackCount: Long,
    val readSkipCount: Long,
    val writeSkipCount: Long,
    val processSkipCount: Long,
    val filterCount: Long,
    val startTime: LocalDateTime?,
    val endTime: LocalDateTime?,
    val exitCode: String?,
    val exitMessage: String?
)

data class JobStatsSummaryDto(
    val totalCount: Long,
    val completedCount: Long,
    val failedCount: Long,
    val runningCount: Long
)

data class TimeStatDto(
    val timeBucket: String,
    val totalCount: Long,
    val completedCount: Long,
    val failedCount: Long,
    val avgDurationSeconds: Double?
)
