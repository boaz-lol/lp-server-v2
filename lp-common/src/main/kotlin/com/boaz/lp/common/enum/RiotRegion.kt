package com.boaz.lp.common.enum

/**
 * Riot API Region
 *
 * Represents different regional servers in League of Legends
 */
enum class RiotRegion(
    val displayName: String,
    val platformId: String,
    val region: String
) {
    KR("Korea", "KR", "kr"),
    NA("North America", "NA1", "na"),
    EUW("Europe West", "EUW1", "euw"),
    EUNE("Europe Nordic & East", "EUN1", "eune"),
    JP("Japan", "JP1", "jp"),
    BR("Brazil", "BR1", "br"),
    LAN("Latin America North", "LA1", "lan"),
    LAS("Latin America South", "LA2", "las"),
    OCE("Oceania", "OC1", "oce"),
    TR("Turkey", "TR1", "tr"),
    RU("Russia", "RU", "ru"),
    SG("Singapore", "SG2", "sg"),
    PH("Philippines", "PH2", "ph"),
    TW("Taiwan", "TW2", "tw"),
    VN("Vietnam", "VN2", "vn"),
    TH("Thailand", "TH2", "th");

    companion object {
        fun fromPlatformId(platformId: String): RiotRegion? {
            return entries.find { it.platformId.equals(platformId, ignoreCase = true) }
        }

        fun fromRegion(region: String): RiotRegion? {
            return entries.find { it.region.equals(region, ignoreCase = true) }
        }
    }
}
