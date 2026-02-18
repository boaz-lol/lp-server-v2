package com.boaz.lp.common.enum

/**
 * Queue Type for League of Legends game modes
 */
enum class QueueType(
    val queueId: Int,
    val displayName: String,
    val description: String
) {
    // Ranked Queues
    RANKED_SOLO_5x5(420, "Ranked Solo/Duo", "5v5 Ranked Solo games"),
    RANKED_FLEX_SR(440, "Ranked Flex", "5v5 Ranked Flex games"),
    RANKED_FLEX_TT(470, "Ranked Flex TT", "3v3 Ranked Flex games (deprecated)"),

    // Normal Queues
    NORMAL_BLIND_5x5(430, "Normal Blind Pick", "5v5 Normal Blind Pick games"),
    NORMAL_DRAFT_5x5(400, "Normal Draft Pick", "5v5 Normal Draft Pick games"),

    // ARAM
    ARAM(450, "ARAM", "5v5 ARAM games"),

    // Special Modes
    CLASH(700, "Clash", "Clash games"),
    URF(900, "URF", "Ultra Rapid Fire"),

    // Others
    CUSTOM(0, "Custom", "Custom games"),
    UNKNOWN(-1, "Unknown", "Unknown queue type");

    companion object {
        fun fromQueueId(queueId: Int): QueueType {
            return entries.find { it.queueId == queueId } ?: UNKNOWN
        }

        fun isRanked(queueId: Int): Boolean {
            return when (fromQueueId(queueId)) {
                RANKED_SOLO_5x5, RANKED_FLEX_SR, RANKED_FLEX_TT -> true
                else -> false
            }
        }
    }
}
