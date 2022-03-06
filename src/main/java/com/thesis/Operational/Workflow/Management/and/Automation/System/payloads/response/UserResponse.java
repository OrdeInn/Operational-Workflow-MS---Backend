package com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.response;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserResponse {

    private Long id;

    private String fName;

    private String lName;

    private String email;

    private boolean enabled;

    private List<String> roles = new ArrayList<>();

    public UserResponse(User user) {

        this.id = user.getId();
        this.fName = user.getFName();
        this.lName = user.getLName();
        this.email = user.getEmail();
        this.enabled = user.isEnabled();

        user.getRoles().forEach(role -> {
            roles.add(role.getName().toString());
        });
    }
}
