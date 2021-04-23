package com.wrvpark.apiserver.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Isabel Ke
 */
public class ForumPostDeleteRequest {
    @JsonProperty(value = "reason")
    private String reason;

    @JsonProperty(value = "description")
    private String description;

    public String getReason() {
        return reason;
    }

    public String getDescription() {
        return description;
    }
}
