package com.boaz.lp.common.enum

/**
 * Ranked Division in League of Legends
 *
 * Note: Master, Grandmaster, and Challenger tiers don't have divisions
 */
enum class Rank(
    val displayName: String,
    val order: Int
) {
    I("I", 4),
    II("II", 3),
    III("III", 2),
    IV("IV", 1),
    NONE("None", 0);  // For Master, Grandmaster, Challenger

    companion object {
        fun fromString(rank: String?): Rank {
            if (rank == null) return NONE
            return entries.find { it.name.equals(rank, ignoreCase = true) } ?: NONE
        }

        fun compare(rank1: Rank, rank2: Rank): Int {
            return rank1.order.compareTo(rank2.order)
        }
    }
}
