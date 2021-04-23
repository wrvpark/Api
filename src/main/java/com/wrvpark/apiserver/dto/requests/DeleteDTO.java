package com.wrvpark.apiserver.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Vahid Haghighat
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeleteDTO {
    private String modifierId;
    private String reason;
    private String description;
    private String itemId;

    public DeleteDTO(ForumPostDeleteRequest request, String modifierId, String itemId) {
        this.modifierId = modifierId;
        this.itemId = itemId;
        this.reason = request.getReason();
        this.description = request.getDescription();
    }
}
