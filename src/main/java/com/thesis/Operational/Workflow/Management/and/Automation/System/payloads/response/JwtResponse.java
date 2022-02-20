package com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.response;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JwtResponse {

    private String token;

    private String type = "Bearer";

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private List<String> roles;

    public JwtResponse(String accessToken, Long id, String email, List<String> roles) {

        this.token = accessToken;
        this.id = id;
        this.email = email;
        this.roles = roles;
    }

    public JwtResponse(String token, User user, List<String> roles) {

        this.token = token;
        this.id = user.getId();
        this.firstName = user.getFName();
        this.lastName = user.getLName();
        this.email = user.getEmail();
        this.roles = roles;
    }
}
