package com.thesis.Operational.Workflow.Management.and.Automation.System.controllers;

import com.thesis.Operational.Workflow.Management.and.Automation.System.exceptions.TokenNotAvailableException;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.User;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.LoginRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.response.JwtResponse;
import com.thesis.Operational.Workflow.Management.and.Automation.System.repositories.RoleRepository;
import com.thesis.Operational.Workflow.Management.and.Automation.System.repositories.UserRepository;
import com.thesis.Operational.Workflow.Management.and.Automation.System.security.jwt.JwtUtils;
import com.thesis.Operational.Workflow.Management.and.Automation.System.services.TokenService;
import com.thesis.Operational.Workflow.Management.and.Automation.System.services.UserDetailsImpl;
import com.thesis.Operational.Workflow.Management.and.Automation.System.services.UserDetailsServiceImpl;
import com.thesis.Operational.Workflow.Management.and.Automation.System.services.UserService;
import io.swagger.annotations.Api;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Api(tags = "Authorization Controller")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;

    private final JwtUtils jwtUtils;

    private final TokenService tokenService;

    private final UserDetailsServiceImpl userDetailsService;

    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                          RoleRepository roleRepository, PasswordEncoder encoder, JwtUtils jwtUtils, TokenService tokenService,
                          UserDetailsServiceImpl userDetailsService, UserService userService) {

        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @RequestMapping(
            method = RequestMethod.POST,
            path = "login-swagger-method-not-seen-endpoint",
            consumes = {
                    MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                    MediaType.MULTIPART_FORM_DATA_VALUE
            },
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiIgnore
    public ResponseEntity<?> login(@Valid @ModelAttribute LoginRequest body) {
        var response = this.authenticateUser(body);

        if(response.getStatusCode() != HttpStatus.OK){
            return response;
        }

        return ResponseEntity.ok(new TokenResult(((JwtResponse)
                Objects.requireNonNull(Objects.requireNonNull(response.getBody()))).getToken()));
    }

    private static class TokenResult{
        @Setter
        @Getter
        private String access_token;

        public TokenResult(String access_token){
            this.access_token = access_token;
        }

    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        if (!userRepository.existsByEmail(loginRequest.getUsername()).booleanValue())
            return ResponseEntity.badRequest().body("error.accountNotFound");

        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService
                .loadUserByUsername(loginRequest.getUsername());
        User user = userService.findByEMail(loginRequest.getUsername());

        if (!userDetails.isEnabled()) {

            return ResponseEntity.badRequest().body("error.userNotActivated");
        }
        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body("error.wrongPass");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt, user, roles));
    }

    @PostMapping("/confirm/{token}")
    public ResponseEntity<String> confirmAccount(@Valid @PathVariable("token") String token) {

        try {
            userDetailsService.confirmUser(token);
        } catch (TokenNotAvailableException error) {
            return ResponseEntity.badRequest().body("error.tokenExpired");
        }
        return ResponseEntity.ok("activation.success");
    }
}