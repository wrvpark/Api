package com.wrvpark.apiserver.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author Vahid Haghighat
 */
public class RefreshRequest implements Serializable {
    @JsonProperty("refresh_token")
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }
}
