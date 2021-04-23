package com.wrvpark.apiserver.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Isabel Ke
 *
 * A dto class that receives search conditions from the client-side
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SearchDTO {
    private String name;
    private String subId;
    private String startTime;
    private String endTime;
    private String uId;
}
