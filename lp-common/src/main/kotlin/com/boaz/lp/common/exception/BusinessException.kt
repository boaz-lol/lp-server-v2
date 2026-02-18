package com.boaz.lp.common.exception

/**
 * Base exception for business logic errors
 */
open class BusinessException(
    val errorCode: ErrorCode,
    override val message: String = errorCode.message,
    cause: Throwable? = null
) : RuntimeException(message, cause) {

    val httpStatus: Int
        get() = errorCode.httpStatus

    val code: String
        get() = errorCode.code

    override fun toString(): String {
        return "BusinessException(errorCode=$errorCode, message='$message', httpStatus=$httpStatus)"
    }
}

/**
 * Specific business exceptions
 */
class SummonerNotFoundException(
    summonerName: String,
    cause: Throwable? = null
) : BusinessException(
    errorCode = ErrorCode.SUMMONER_NOT_FOUND,
    message = "Summoner not found: $summonerName",
    cause = cause
)

class MatchNotFoundException(
    matchId: String,
    cause: Throwable? = null
) : BusinessException(
    errorCode = ErrorCode.MATCH_NOT_FOUND,
    message = "Match not found: $matchId",
    cause = cause
)

class RiotApiException(
    message: String,
    cause: Throwable? = null
) : BusinessException(
    errorCode = ErrorCode.RIOT_API_ERROR,
    message = message,
    cause = cause
)

class RiotApiRateLimitException(
    message: String = "Riot API rate limit exceeded",
    cause: Throwable? = null
) : BusinessException(
    errorCode = ErrorCode.RIOT_API_RATE_LIMIT,
    message = message,
    cause = cause
)

class ModelPredictionException(
    message: String = "Model prediction failed",
    cause: Throwable? = null
) : BusinessException(
    errorCode = ErrorCode.MODEL_PREDICTION_FAILED,
    message = message,
    cause = cause
)

class InvalidInputException(
    message: String,
    cause: Throwable? = null
) : BusinessException(
    errorCode = ErrorCode.INVALID_INPUT,
    message = message,
    cause = cause
)
