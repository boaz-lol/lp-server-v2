package com.boaz.lp.common.enum

/**
 * Ranked Tier in League of Legends
 */
enum class Tier(
    val displayName: String,
    val order: Int
) {
    IRON("Iron", 1),
    BRONZE("Bronze", 2),
    SILVER("Silver", 3),
    GOLD("Gold", 4),
    PLATINUM("Platinum", 5),
    EMERALD("Emerald", 6),
    DIAMOND("Diamond", 7),
    MASTER("Master", 8),
    GRANDMASTER("Grandmaster", 9),
    CHALLENGER("Challenger", 10),
    UNRANKED("Unranked", 0);

    fun isApex(): Boolean {
        return this in listOf(MASTER, GRANDMASTER, CHALLENGER)
    }

    companion object {
        fun fromString(tier: String?): Tier {
            if (tier == null) return UNRANKED
            return entries.find { it.name.equals(tier, ignoreCase = true) } ?: UNRANKED
        }

        fun compare(tier1: Tier, tier2: Tier): Int {
            return tier1.order.compareTo(tier2.order)
        }
    }
}
