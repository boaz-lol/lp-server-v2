package com.boaz.lp.admin.controller

import com.boaz.lp.admin.service.BatchStatisticsService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class DashboardController(
    private val batchStatisticsService: BatchStatisticsService
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
}
