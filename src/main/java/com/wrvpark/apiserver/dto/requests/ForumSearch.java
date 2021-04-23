package com.wrvpark.apiserver.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;

/**
 * @author Isabel Ke
 */
public class ForumSearch implements Serializable {
    @JsonProperty("keywords")
    private List<String> keywords;

    @JsonProperty("start_time")
    private String startTime;

    @JsonProperty("end_time")
    private String endTime;

    public List<String> getKeywords() {
        return keywords;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}
