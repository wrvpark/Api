package com.wrvpark.apiserver.dto.requests.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.Serializable;

/**
 * @author Vahid Haghighat
 */
public class LoginResponse implements Serializable {
    @JsonProperty("access_token")
    private final String accessToken;

    @JsonProperty("expires_in")
    private final String expiry;

    @JsonProperty("refresh_token")
    private final String refreshToken;

    @JsonProperty("refresh_expires_in")
    private final String refreshExpiresIn;

    public LoginResponse(JsonNode token) {
        if (token.has("access_token")) {
            accessToken = token.get("access_token").toString().replace("\"", "");
            refreshToken = token.get("refresh_token").toString().replace("\"", "");
            refreshExpiresIn = token.get("refresh_expires_in").toString();
            expiry = token.get("expires_in").toString();
        } else {
            accessToken = "Invalid Credentials";
            refreshToken = "Invalid Credentials";
            refreshExpiresIn = "0";
            expiry = "0";
        }
    }
    public String getExpiry() {
        return expiry;
    }
}
