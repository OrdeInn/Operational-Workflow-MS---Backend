package com.thesis.Operational.Workflow.Management.and.Automation.System.services;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.Token;
import com.thesis.Operational.Workflow.Management.and.Automation.System.repositories.TokenRepository;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    private final TokenRepository tokenRepository;

    public TokenService(TokenRepository tokenRepository) {

        this.tokenRepository = tokenRepository;
    }

    public Token findByToken(String confirmationToken) {

        return tokenRepository.findByConfirmationToken(confirmationToken).orElse(null);
    }

    public void saveConfirmationToken(Token token) {

        tokenRepository.save(token);
    }

    public void deleteConfirmationToken(Long id) {

        tokenRepository.deleteById(id);
    }
}
