package com.thesis.Operational.Workflow.Management.and.Automation.System.services;

import com.thesis.Operational.Workflow.Management.and.Automation.System.exceptions.TokenNotAvailableException;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.Token;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.User;
import com.thesis.Operational.Workflow.Management.and.Automation.System.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    private final TokenService tokenService;

    private final PasswordEncoder encoder;

    @Value("${purge.daysToLive}")
    String timeToLive;

    public UserDetailsServiceImpl(UserRepository userRepository,TokenService tokenService, PasswordEncoder encoder) {

        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.encoder = encoder;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + email));

        return UserDetailsImpl.build(user);
    }


    public void confirmUser(String token) throws TokenNotAvailableException {

        Token confirmationToken = tokenService.findByToken(token);
        final User user = confirmationToken.getUser();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tokenCreationTime = confirmationToken.getCreatedDate();
        long hours = Duration.between(now, tokenCreationTime).getSeconds() / 3600;
        if (hours > 24) {
            throw new TokenNotAvailableException("token expired!!");
        }

        user.setEnabled(true);
        userRepository.save(user);
        tokenService.deleteConfirmationToken(confirmationToken.getId());

    }

    public void resetPassword(String token, String newPassword) throws TokenNotAvailableException {

        Token confirmationToken = tokenService.findByToken(token);
        final User user = confirmationToken.getUser();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tokenCreationTime = confirmationToken.getCreatedDate();
        long hours = Duration.between(now, tokenCreationTime).getSeconds() / 3600;
        if (hours > 1) {
            throw new TokenNotAvailableException("token expired!!");
        }
        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);
        tokenService.deleteConfirmationToken(confirmationToken.getId());

    }

    public User getUserFrom(UserDetailsImpl userDetails) {

        return userRepository.findByEmail(userDetails.getEmail()).orElse(null);
    }

}