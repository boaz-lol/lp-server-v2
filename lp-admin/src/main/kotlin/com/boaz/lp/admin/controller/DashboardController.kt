package com.boaz.lp.admin.controller

import com.boaz.lp.admin.service.BatchStatisticsService
import com.boaz.lp.admin.service.BatchTriggerService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
class DashboardController(
    private val batchStatisticsService: BatchStatisticsService,
    private val batchTriggerService: BatchTriggerService
) {

    @GetMapping("/")
    fun dashboard(model: Model): String {
        try {
            model.addAttribute("summary", batchStatisticsService.getJobStatsSummary())
            model.addAttribute("recentJobs", batchStatisticsService.getRecentJobExecutions(10))
        } catch (e: Exception) {
            model.addAttribute("dbError", "배치 메타데이터 테이블을 조회할 수 없습니다: ${e.message}")
        }
        return "dashboard"
    }

    @PostMapping("/batch/trigger")
    fun triggerBatchJob(redirectAttributes: RedirectAttributes): String {
        try {
            val message = batchTriggerService.triggerJob()
            redirectAttributes.addFlashAttribute("triggerSuccess", message)
        } catch (e: Exception) {
            redirectAttributes.addFlashAttribute("triggerError", e.message)
        }
        return "redirect:/"
    }
}
