package com.wrvpark.apiserver.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wrvpark.apiserver.util.ConstantUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 * @author Vahid Haghighat
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateParkDocumentDTO implements Serializable {
    @JsonProperty("name")
    private String title;

    @JsonProperty("subId")
    private String subcategoryId;

    @JsonProperty("description")
    private String description;

    @JsonProperty("fileName")
    private List<String> fileNames;

    public String getFileNameString() {
        if (fileNames == null || fileNames.size() == 0)
            return "";
        StringBuilder builder = new StringBuilder();
        Iterator<String> iterator = fileNames.iterator();
        while (iterator.hasNext()) {
            builder.append(iterator.next());
            if (iterator.hasNext())
                builder.append(ConstantUtil.DELIMITER);
        }
        return builder.toString();
    }
}