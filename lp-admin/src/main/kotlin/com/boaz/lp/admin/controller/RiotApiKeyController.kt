package com.boaz.lp.admin.controller

import com.boaz.lp.admin.service.RiotApiKeyService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping("/settings")
class RiotApiKeyController(
    private val riotApiKeyService: RiotApiKeyService
) {

    @GetMapping("/riot-api-key")
    fun riotApiKeyPage(model: Model): String {
        model.addAttribute("currentKey", riotApiKeyService.getCurrentKeyMasked() ?: "Not configured")
        return "settings/riot-api-key"
    }

    @PostMapping("/riot-api-key")
    fun updateRiotApiKey(
        @RequestParam("apiKey") apiKey: String,
        redirectAttributes: RedirectAttributes
    ): String {
        return try {
            riotApiKeyService.updateApiKey(apiKey)
            redirectAttributes.addFlashAttribute("successMessage", "API Key가 성공적으로 변경되었습니다.")
            "redirect:/settings/riot-api-key"
        } catch (e: Exception) {
            redirectAttributes.addFlashAttribute("errorMessage", e.message)
            "redirect:/settings/riot-api-key"
        }
    }
}
