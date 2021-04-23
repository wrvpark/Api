package com.wrvpark.apiserver.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Vahid Haghighat
 */
public class PostResponseDTO {
    @JsonProperty("details")
    private String details;

    @JsonProperty("image")
    private String image;

    public String getDetails() {
        return details;
    }

    public String getImage() {
        return image;
    }
}
