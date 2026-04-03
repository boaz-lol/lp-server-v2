package com.boaz.lp.api.auth.oauth

import com.boaz.lp.common.enum.MemberStatus
import com.boaz.lp.common.enum.OAuthProvider
import com.boaz.lp.storage.entity.MemberEntity
import com.boaz.lp.storage.repository.MemberRepository
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CustomOAuth2UserService(
    private val memberRepository: MemberRepository
) : DefaultOAuth2UserService() {

    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User = super.loadUser(userRequest)
        val registrationId = userRequest.clientRegistration.registrationId

        val userInfo = when (registrationId) {
            "google" -> OAuth2UserInfo.ofGoogle(oAuth2User.attributes)
            "kakao" -> OAuth2UserInfo.ofKakao(oAuth2User.attributes)
            else -> throw IllegalArgumentException("Unsupported OAuth2 provider: $registrationId")
        }

        val member = saveOrUpdate(userInfo)

        val attributes = oAuth2User.attributes.toMutableMap()
        attributes["memberId"] = member.id!!
        attributes["role"] = member.role.name

        val nameAttributeKey = userRequest.clientRegistration.providerDetails.userInfoEndpoint.userNameAttributeName
        return DefaultOAuth2User(emptyList(), attributes, nameAttributeKey)
    }

    private fun saveOrUpdate(userInfo: OAuth2UserInfo): MemberEntity {
        val member = memberRepository.findByProviderAndProviderId(userInfo.provider, userInfo.providerId)
            .orElseGet {
                MemberEntity(
                    provider = userInfo.provider,
                    providerId = userInfo.providerId,
                    email = userInfo.email,
                    nickname = userInfo.nickname,
                    profileImageUrl = userInfo.profileImageUrl
                )
            }

        member.nickname = userInfo.nickname
        member.profileImageUrl = userInfo.profileImageUrl
        member.lastLoginAt = LocalDateTime.now()

        return memberRepository.save(member)
    }
}
