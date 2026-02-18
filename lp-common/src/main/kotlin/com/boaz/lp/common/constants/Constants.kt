package com.boaz.lp.common.constants

/**
 * Application-wide constants
 */
object Constants {

    /**
     * Riot API related constants
     */
    object RiotApi {
        const val BASE_URL_ASIA = "https://asia.api.riotgames.com"
        const val BASE_URL_AMERICAS = "https://americas.api.riotgames.com"
        const val BASE_URL_EUROPE = "https://europe.api.riotgames.com"
        const val BASE_URL_SEA = "https://sea.api.riotgames.com"

        const val PLATFORM_URL_FORMAT = "https://%s.api.riotgames.com"

        // Rate limit headers
        const val HEADER_RATE_LIMIT_TYPE = "X-Rate-Limit-Type"
        const val HEADER_RETRY_AFTER = "Retry-After"
    }

    /**
     * Cache related constants
     */
    object Cache {
        const val SUMMONER_CACHE_TTL_MINUTES = 60L
        const val MATCH_CACHE_TTL_MINUTES = 1440L  // 24 hours
        const val RANK_CACHE_TTL_MINUTES = 30L
    }

    /**
     * Pagination defaults
     */
    object Pagination {
        const val DEFAULT_PAGE_SIZE = 20
        const val MAX_PAGE_SIZE = 100
        const val DEFAULT_PAGE = 0
    }

    /**
     * Match related constants
     */
    object Match {
        const val MAX_MATCH_HISTORY = 100
        const val DEFAULT_MATCH_COUNT = 20
    }
}
