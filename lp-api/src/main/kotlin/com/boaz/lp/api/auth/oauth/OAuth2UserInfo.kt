package com.boaz.lp.api.auth.oauth

import com.boaz.lp.common.enum.OAuthProvider

data class OAuth2UserInfo(
    val provider: OAuthProvider,
    val providerId: String,
    val email: String,
    val nickname: String,
    val profileImageUrl: String?
) {
    companion object {
        fun ofGoogle(attributes: Map<String, Any>): OAuth2UserInfo {
            return OAuth2UserInfo(
                provider = OAuthProvider.GOOGLE,
                providerId = attributes["sub"] as String,
                email = attributes["email"] as String,
                nickname = attributes["name"] as String,
                profileImageUrl = attributes["picture"] as? String
            )
        }

        fun ofKakao(attributes: Map<String, Any>): OAuth2UserInfo {
            val kakaoAccount = attributes["kakao_account"] as? Map<*, *> ?: emptyMap<String, Any>()
            val profile = kakaoAccount["profile"] as? Map<*, *> ?: emptyMap<String, Any>()
            return OAuth2UserInfo(
                provider = OAuthProvider.KAKAO,
                providerId = attributes["id"].toString(),
                email = kakaoAccount["email"] as? String ?: "",
                nickname = profile["nickname"] as? String ?: "",
                profileImageUrl = profile["profile_image_url"] as? String
            )
        }
    }
}
