package com.wrvpark.apiserver.dto.requests;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Isabel Ke
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class NewEventDTO {
    private String id;
    private String title;
    private String description;
    private String startTime;
    private String endTime;
    private String details;
    private String userId;
    private String fileName;
    private boolean isLimited;
    private String locSubId;
    private String descSubId;
    private boolean recurring;
    private String recurFrequency;
}
