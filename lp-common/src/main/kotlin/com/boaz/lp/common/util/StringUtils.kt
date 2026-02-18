package com.boaz.lp.common.util

/**
 * String utility functions
 */
object StringUtils {

    /**
     * Normalize summoner name (remove spaces, convert to lowercase)
     */
    fun normalizeSummonerName(name: String): String {
        return name.replace(" ", "").lowercase()
    }

    /**
     * Validate summoner name format
     */
    fun isValidSummonerName(name: String): Boolean {
        if (name.isBlank()) return false
        if (name.length < 3 || name.length > 16) return false

        // Riot allows alphanumeric and some special characters
        val validPattern = Regex("^[0-9a-zA-Z가-힣 ]+$")
        return validPattern.matches(name)
    }

    /**
     * Mask sensitive information
     */
    fun maskString(str: String, visibleChars: Int = 4): String {
        if (str.length <= visibleChars) return "*".repeat(str.length)
        return str.take(visibleChars) + "*".repeat(str.length - visibleChars)
    }
}
