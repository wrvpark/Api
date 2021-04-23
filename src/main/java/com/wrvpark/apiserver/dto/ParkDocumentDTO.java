package com.wrvpark.apiserver.dto;

import com.wrvpark.apiserver.domain.ParkDocument;
import com.wrvpark.apiserver.util.CommActions;
import com.wrvpark.apiserver.util.ConstantUtil;
import com.wrvpark.apiserver.util.TimeUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Isabel Ke
 * @author Vahid Haghighat
 * Original date:2020-02-18
 *
 * Description:A customized park document that will be returned to the client-side
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ParkDocumentDTO {

    private String id;
    private String name;
    private SubcategoryDTO subCategory;
    private UserDTO creator;
    private String updateTime;
    private String description;
    private List<String> fileName;
    private List<String> url;
    private String status;

    public ParkDocumentDTO(ParkDocument document) {
        this.id = document.getId();
        this.name = document.getTitle();
        this.updateTime = TimeUtil.convertDateToString(document.getUpdateTime());
        this.description = document.getDescription();
        this.fileName = new ArrayList<>();
        this.url = new ArrayList<>();

        if (document.getFileName() != null && !document.getFileName().isEmpty()) {
            String[] files = document.getFileName().split(ConstantUtil.DELIMITER);
            for (String fileString : files) {
                fileName.add(fileString);
                url.add(CommActions.generateURL(fileString));
            }
        }
        this.status = document.getStatus();
        this.subCategory = new SubcategoryDTO(document.getSubCategory());
        this.creator = new UserDTO(document.getCreator());
    }
}
