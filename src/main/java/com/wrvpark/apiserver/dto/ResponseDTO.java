package com.wrvpark.apiserver.dto;

import com.wrvpark.apiserver.domain.Response;
import com.wrvpark.apiserver.util.CommActions;
import com.wrvpark.apiserver.util.TimeUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * @author Vahid Haghighat
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseDTO implements Serializable {
    private String originalPostId;
    private String responderName;
    private String responderId;
    private String details;
    private String image;
    private String responseTime;

    public ResponseDTO(Response response) {
        this.originalPostId = response.getForum().getId();
        this.responderName = response.getResponder().getFirstName();
        this.responderId = response.getResponder().getId();
        this.details = response.getDetails();
        this.responseTime = TimeUtil.convertDateToString(response.getDate());
        this.image = "";
        if(response.getImage() != null && !response.getImage().isEmpty()) {
            this.image = CommActions.generateURL(response.getImage());
        }
    }
}
