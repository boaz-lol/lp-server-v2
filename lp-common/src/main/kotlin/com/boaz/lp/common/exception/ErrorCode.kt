package com.boaz.lp.common.exception

/**
 * Error codes for the application
 */
enum class ErrorCode(
    val code: String,
    val message: String,
    val httpStatus: Int
) {
    // Common errors (1xxx)
    INVALID_INPUT("1001", "Invalid input", 400),
    RESOURCE_NOT_FOUND("1002", "Resource not found", 404),
    INTERNAL_SERVER_ERROR("1003", "Internal server error", 500),
    UNAUTHORIZED("1004", "Unauthorized", 401),
    FORBIDDEN("1005", "Forbidden", 403),

    // Summoner errors (2xxx)
    SUMMONER_NOT_FOUND("2001", "Summoner not found", 404),
    SUMMONER_ALREADY_EXISTS("2002", "Summoner already exists", 409),
    INVALID_SUMMONER_NAME("2003", "Invalid summoner name", 400),

    // Match errors (3xxx)
    MATCH_NOT_FOUND("3001", "Match not found", 404),
    INVALID_MATCH_ID("3002", "Invalid match ID", 400),
    MATCH_DATA_UNAVAILABLE("3003", "Match data unavailable", 503),

    // Riot API errors (4xxx)
    RIOT_API_ERROR("4001", "Riot API error", 502),
    RIOT_API_RATE_LIMIT("4002", "Riot API rate limit exceeded", 429),
    RIOT_API_UNAVAILABLE("4003", "Riot API unavailable", 503),
    RIOT_API_KEY_INVALID("4004", "Invalid Riot API key", 401),

    // Python Model errors (5xxx)
    MODEL_PREDICTION_FAILED("5001", "Model prediction failed", 500),
    MODEL_SERVICE_UNAVAILABLE("5002", "Model service unavailable", 503),

    // Database errors (6xxx)
    DATABASE_ERROR("6001", "Database error", 500),
    DATA_INTEGRITY_ERROR("6002", "Data integrity error", 409);

    companion object {
        fun fromCode(code: String): ErrorCode? {
            return entries.find { it.code == code }
        }
    }
}
