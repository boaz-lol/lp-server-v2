package com.boaz.lp.admin.service

import com.boaz.lp.admin.dto.*
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class BatchStatisticsService(
    private val jdbcTemplate: JdbcTemplate
) {

    fun getRecentJobExecutions(limit: Int = 10): List<JobExecutionDto> {
        val sql = """
            SELECT e.JOB_EXECUTION_ID, i.JOB_NAME, e.STATUS, e.START_TIME, e.END_TIME,
                   e.EXIT_CODE, e.EXIT_MESSAGE, e.CREATE_TIME
            FROM BATCH_JOB_EXECUTION e
            JOIN BATCH_JOB_INSTANCE i ON e.JOB_INSTANCE_ID = i.JOB_INSTANCE_ID
            ORDER BY e.JOB_EXECUTION_ID DESC
            LIMIT ?
        """.trimIndent()
        return jdbcTemplate.query(sql, { rs, _ ->
            JobExecutionDto(
                id = rs.getLong("JOB_EXECUTION_ID"),
                jobName = rs.getString("JOB_NAME"),
                status = rs.getString("STATUS"),
                startTime = rs.getTimestamp("START_TIME")?.toLocalDateTime(),
                endTime = rs.getTimestamp("END_TIME")?.toLocalDateTime(),
                exitCode = rs.getString("EXIT_CODE"),
                exitMessage = rs.getString("EXIT_MESSAGE"),
                createTime = rs.getTimestamp("CREATE_TIME").toLocalDateTime()
            )
        }, limit)
    }

    fun getJobExecutionsPaged(page: Int, size: Int): List<JobExecutionDto> {
        val offset = page * size
        val sql = """
            SELECT e.JOB_EXECUTION_ID, i.JOB_NAME, e.STATUS, e.START_TIME, e.END_TIME,
                   e.EXIT_CODE, e.EXIT_MESSAGE, e.CREATE_TIME
            FROM BATCH_JOB_EXECUTION e
            JOIN BATCH_JOB_INSTANCE i ON e.JOB_INSTANCE_ID = i.JOB_INSTANCE_ID
            ORDER BY e.JOB_EXECUTION_ID DESC
            LIMIT ? OFFSET ?
        """.trimIndent()
        return jdbcTemplate.query(sql, { rs, _ ->
            JobExecutionDto(
                id = rs.getLong("JOB_EXECUTION_ID"),
                jobName = rs.getString("JOB_NAME"),
                status = rs.getString("STATUS"),
                startTime = rs.getTimestamp("START_TIME")?.toLocalDateTime(),
                endTime = rs.getTimestamp("END_TIME")?.toLocalDateTime(),
                exitCode = rs.getString("EXIT_CODE"),
                exitMessage = rs.getString("EXIT_MESSAGE"),
                createTime = rs.getTimestamp("CREATE_TIME").toLocalDateTime()
            )
        }, size, offset)
    }

    fun getTotalJobExecutionCount(): Long {
        return jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM BATCH_JOB_EXECUTION", Long::class.java
        ) ?: 0
    }

    fun getJobExecutionDetail(id: Long): JobExecutionDto? {
        val sql = """
            SELECT e.JOB_EXECUTION_ID, i.JOB_NAME, e.STATUS, e.START_TIME, e.END_TIME,
                   e.EXIT_CODE, e.EXIT_MESSAGE, e.CREATE_TIME
            FROM BATCH_JOB_EXECUTION e
            JOIN BATCH_JOB_INSTANCE i ON e.JOB_INSTANCE_ID = i.JOB_INSTANCE_ID
            WHERE e.JOB_EXECUTION_ID = ?
        """.trimIndent()
        val results = jdbcTemplate.query(sql, { rs, _ ->
            JobExecutionDto(
                id = rs.getLong("JOB_EXECUTION_ID"),
                jobName = rs.getString("JOB_NAME"),
                status = rs.getString("STATUS"),
                startTime = rs.getTimestamp("START_TIME")?.toLocalDateTime(),
                endTime = rs.getTimestamp("END_TIME")?.toLocalDateTime(),
                exitCode = rs.getString("EXIT_CODE"),
                exitMessage = rs.getString("EXIT_MESSAGE"),
                createTime = rs.getTimestamp("CREATE_TIME").toLocalDateTime()
            )
        }, id)
        return results.firstOrNull()
    }

    fun getStepExecutions(jobExecutionId: Long): List<StepExecutionDto> {
        val sql = """
            SELECT STEP_EXECUTION_ID, STEP_NAME, STATUS,
                   READ_COUNT, WRITE_COUNT, COMMIT_COUNT, ROLLBACK_COUNT,
                   READ_SKIP_COUNT, WRITE_SKIP_COUNT, PROCESS_SKIP_COUNT, FILTER_COUNT,
                   START_TIME, END_TIME, EXIT_CODE, EXIT_MESSAGE
            FROM BATCH_STEP_EXECUTION
            WHERE JOB_EXECUTION_ID = ?
            ORDER BY STEP_EXECUTION_ID
        """.trimIndent()
        return jdbcTemplate.query(sql, { rs, _ ->
            StepExecutionDto(
                id = rs.getLong("STEP_EXECUTION_ID"),
                stepName = rs.getString("STEP_NAME"),
                status = rs.getString("STATUS"),
                readCount = rs.getLong("READ_COUNT"),
                writeCount = rs.getLong("WRITE_COUNT"),
                commitCount = rs.getLong("COMMIT_COUNT"),
                rollbackCount = rs.getLong("ROLLBACK_COUNT"),
                readSkipCount = rs.getLong("READ_SKIP_COUNT"),
                writeSkipCount = rs.getLong("WRITE_SKIP_COUNT"),
                processSkipCount = rs.getLong("PROCESS_SKIP_COUNT"),
                filterCount = rs.getLong("FILTER_COUNT"),
                startTime = rs.getTimestamp("START_TIME")?.toLocalDateTime(),
                endTime = rs.getTimestamp("END_TIME")?.toLocalDateTime(),
                exitCode = rs.getString("EXIT_CODE"),
                exitMessage = rs.getString("EXIT_MESSAGE")
            )
        }, jobExecutionId)
    }

    fun getFailedJobExecutions(): List<JobExecutionDto> {
        val sql = """
            SELECT e.JOB_EXECUTION_ID, i.JOB_NAME, e.STATUS, e.START_TIME, e.END_TIME,
                   e.EXIT_CODE, e.EXIT_MESSAGE, e.CREATE_TIME
            FROM BATCH_JOB_EXECUTION e
            JOIN BATCH_JOB_INSTANCE i ON e.JOB_INSTANCE_ID = i.JOB_INSTANCE_ID
            WHERE e.STATUS = 'FAILED'
            ORDER BY e.JOB_EXECUTION_ID DESC
        """.trimIndent()
        return jdbcTemplate.query(sql) { rs, _ ->
            JobExecutionDto(
                id = rs.getLong("JOB_EXECUTION_ID"),
                jobName = rs.getString("JOB_NAME"),
                status = rs.getString("STATUS"),
                startTime = rs.getTimestamp("START_TIME")?.toLocalDateTime(),
                endTime = rs.getTimestamp("END_TIME")?.toLocalDateTime(),
                exitCode = rs.getString("EXIT_CODE"),
                exitMessage = rs.getString("EXIT_MESSAGE"),
                createTime = rs.getTimestamp("CREATE_TIME").toLocalDateTime()
            )
        }
    }

    fun getJobStatsSummary(): JobStatsSummaryDto {
        val sql = """
            SELECT
                COUNT(*) as total,
                SUM(CASE WHEN STATUS = 'COMPLETED' THEN 1 ELSE 0 END) as completed,
                SUM(CASE WHEN STATUS = 'FAILED' THEN 1 ELSE 0 END) as failed,
                SUM(CASE WHEN STATUS IN ('STARTED', 'STARTING') THEN 1 ELSE 0 END) as running
            FROM BATCH_JOB_EXECUTION
        """.trimIndent()
        return jdbcTemplate.queryForObject(sql) { rs, _ ->
            JobStatsSummaryDto(
                totalCount = rs.getLong("total"),
                completedCount = rs.getLong("completed"),
                failedCount = rs.getLong("failed"),
                runningCount = rs.getLong("running")
            )
        } ?: JobStatsSummaryDto(0, 0, 0, 0)
    }

    fun getHourlyStats(): List<TimeStatDto> {
        val sql = """
            SELECT
                FORMATDATETIME(START_TIME, 'HH') as time_bucket,
                COUNT(*) as total,
                SUM(CASE WHEN STATUS = 'COMPLETED' THEN 1 ELSE 0 END) as completed,
                SUM(CASE WHEN STATUS = 'FAILED' THEN 1 ELSE 0 END) as failed,
                AVG(TIMESTAMPDIFF(SECOND, START_TIME, END_TIME)) as avg_duration
            FROM BATCH_JOB_EXECUTION
            WHERE START_TIME IS NOT NULL
            GROUP BY FORMATDATETIME(START_TIME, 'HH')
            ORDER BY time_bucket
        """.trimIndent()
        return try {
            jdbcTemplate.query(sql) { rs, _ ->
                TimeStatDto(
                    timeBucket = "${rs.getString("time_bucket")}:00",
                    totalCount = rs.getLong("total"),
                    completedCount = rs.getLong("completed"),
                    failedCount = rs.getLong("failed"),
                    avgDurationSeconds = rs.getDouble("avg_duration").takeIf { !rs.wasNull() }
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun getDailyStats(): List<TimeStatDto> {
        val sql = """
            SELECT
                FORMATDATETIME(START_TIME, 'yyyy-MM-dd') as time_bucket,
                COUNT(*) as total,
                SUM(CASE WHEN STATUS = 'COMPLETED' THEN 1 ELSE 0 END) as completed,
                SUM(CASE WHEN STATUS = 'FAILED' THEN 1 ELSE 0 END) as failed,
                AVG(TIMESTAMPDIFF(SECOND, START_TIME, END_TIME)) as avg_duration
            FROM BATCH_JOB_EXECUTION
            WHERE START_TIME IS NOT NULL
            GROUP BY FORMATDATETIME(START_TIME, 'yyyy-MM-dd')
            ORDER BY time_bucket DESC
            LIMIT 30
        """.trimIndent()
        return try {
            jdbcTemplate.query(sql) { rs, _ ->
                TimeStatDto(
                    timeBucket = rs.getString("time_bucket"),
                    totalCount = rs.getLong("total"),
                    completedCount = rs.getLong("completed"),
                    failedCount = rs.getLong("failed"),
                    avgDurationSeconds = rs.getDouble("avg_duration").takeIf { !rs.wasNull() }
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
