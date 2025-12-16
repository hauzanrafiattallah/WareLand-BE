package com.wareland.user.service;

import com.wareland.user.model.RevokedToken;
import com.wareland.user.repository.RevokedTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final RevokedTokenRepository revokedTokenRepository;

    public AuthService(RevokedTokenRepository revokedTokenRepository) {
        this.revokedTokenRepository = revokedTokenRepository;
    }

    @Transactional
    public void logout(String bearerToken) {
        if (bearerToken == null || bearerToken.isBlank()) {
            return;
        }
        String token = bearerToken;
        if (bearerToken.startsWith("Bearer ")) {
            token = bearerToken.substring(7);
        }
        if (!token.isBlank() && !revokedTokenRepository.existsByToken(token)) {
            revokedTokenRepository.save(new RevokedToken(token));
        }
    }
}
