package com.wrvpark.apiserver.dto.requests.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Vahid Haghighat
 */
public class ForgotPassword {
    @JsonProperty(value = "email")
    private String email;

    public String getEmail() { return email; }
}
