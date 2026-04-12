package com.boaz.lp.admin.controller

import com.boaz.lp.admin.service.BatchStatisticsService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/batch")
class BatchStatsController(
    private val batchStatisticsService: BatchStatisticsService
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping("/jobs")
    fun jobList(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int,
        model: Model
    ): String {
        try {
            model.addAttribute("jobs", batchStatisticsService.getJobExecutionsPaged(page, size))
            model.addAttribute("currentPage", page)
            model.addAttribute("pageSize", size)
            model.addAttribute("totalCount", batchStatisticsService.getTotalJobExecutionCount())
        } catch (e: Exception) {
            log.warn("배치 메타데이터 테이블 조회 실패: {}", e.message)
            model.addAttribute("dbError", "배치 메타데이터 테이블을 조회할 수 없습니다. 배치 서버(lp-batch)가 한 번 이상 실행되어야 테이블이 생성됩니다.")
        }
        return "batch/jobs"
    }

    @GetMapping("/jobs/{id}")
    fun jobDetail(@PathVariable id: Long, model: Model): String {
        try {
            val job = batchStatisticsService.getJobExecutionDetail(id)
                ?: return "redirect:/batch/jobs"
            model.addAttribute("job", job)
            model.addAttribute("steps", batchStatisticsService.getStepExecutions(id))
        } catch (e: Exception) {
            log.warn("배치 메타데이터 테이블 조회 실패: {}", e.message)
            model.addAttribute("dbError", "배치 메타데이터 테이블을 조회할 수 없습니다. 배치 서버(lp-batch)가 한 번 이상 실행되어야 테이블이 생성됩니다.")
        }
        return "batch/job-detail"
    }

    @GetMapping("/jobs/failed")
    fun failedJobs(model: Model): String {
        try {
            model.addAttribute("jobs", batchStatisticsService.getFailedJobExecutions())
        } catch (e: Exception) {
            log.warn("배치 메타데이터 테이블 조회 실패: {}", e.message)
            model.addAttribute("dbError", "배치 메타데이터 테이블을 조회할 수 없습니다. 배치 서버(lp-batch)가 한 번 이상 실행되어야 테이블이 생성됩니다.")
        }
        return "batch/failed-jobs"
    }

    @GetMapping("/statistics")
    fun statistics(model: Model): String {
        try {
            model.addAttribute("hourlyStats", batchStatisticsService.getHourlyStats())
            model.addAttribute("dailyStats", batchStatisticsService.getDailyStats())
        } catch (e: Exception) {
            log.warn("배치 메타데이터 테이블 조회 실패: {}", e.message)
            model.addAttribute("dbError", "배치 메타데이터 테이블을 조회할 수 없습니다. 배치 서버(lp-batch)가 한 번 이상 실행되어야 테이블이 생성됩니다.")
        }
        return "batch/statistics"
    }
}
