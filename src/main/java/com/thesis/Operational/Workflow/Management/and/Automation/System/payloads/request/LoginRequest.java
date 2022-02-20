package com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request;

import javax.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank(message = "username.blank")
    private String username;

    public String getGrant_type() {
        return grant_type;
    }

    public String getClient_id() {
        return client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public String grant_type;

    public String client_id;

    public String client_secret;

    @NotBlank(message = "password.blank")
    private String password;

    private String pushNotificationPlayerId;

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {

        this.username = username;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public String getPushNotificationPlayerId() {

        return pushNotificationPlayerId;
    }

    public void setPushNotificationPlayerId(String pushNotificationPlayerId) {

        this.pushNotificationPlayerId = pushNotificationPlayerId;
    }
}

