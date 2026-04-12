package com.boaz.lp.admin.controller

import com.boaz.lp.admin.service.BatchStatisticsService
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

    @GetMapping("/jobs")
    fun jobList(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int,
        model: Model
    ): String {
        model.addAttribute("jobs", batchStatisticsService.getJobExecutionsPaged(page, size))
        model.addAttribute("currentPage", page)
        model.addAttribute("pageSize", size)
        model.addAttribute("totalCount", batchStatisticsService.getTotalJobExecutionCount())
        return "batch/jobs"
    }

    @GetMapping("/jobs/{id}")
    fun jobDetail(@PathVariable id: Long, model: Model): String {
        val job = batchStatisticsService.getJobExecutionDetail(id)
            ?: return "redirect:/batch/jobs"
        model.addAttribute("job", job)
        model.addAttribute("steps", batchStatisticsService.getStepExecutions(id))
        return "batch/job-detail"
    }

    @GetMapping("/jobs/failed")
    fun failedJobs(model: Model): String {
        model.addAttribute("jobs", batchStatisticsService.getFailedJobExecutions())
        return "batch/failed-jobs"
    }

    @GetMapping("/statistics")
    fun statistics(model: Model): String {
        model.addAttribute("hourlyStats", batchStatisticsService.getHourlyStats())
        model.addAttribute("dailyStats", batchStatisticsService.getDailyStats())
        return "batch/statistics"
    }
}
