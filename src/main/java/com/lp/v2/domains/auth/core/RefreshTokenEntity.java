package com.lp.v2.domains.auth.core;

import com.lp.v2.common.Auditable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshTokenEntity extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "token", length = 512, unique = true, nullable = false)
    private String token;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Builder
    public RefreshTokenEntity(String token, Long accountId) {
        this.token = token;
        this.accountId = accountId;
    }
}
