package com.wareland.user.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "revoked_tokens", indexes = {
        @Index(name = "idx_revoked_token_token", columnList = "token", unique = true)
})
public class RevokedToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 512)
    private String token;

    @Column(nullable = false)
    private Instant revokedAt;

    protected RevokedToken() {
        // JPA only
    }

    public RevokedToken(String token) {
        this.token = token;
        this.revokedAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public Instant getRevokedAt() {
        return revokedAt;
    }
}
