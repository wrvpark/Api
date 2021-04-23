package com.wrvpark.apiserver.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wrvpark.apiserver.util.ConstantUtil;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 * @author Isabel Ke
 */
public class NewForumPost implements Serializable {
    @JsonProperty("title")
    private String title;

    @JsonProperty("type")
    private String type;

    @JsonProperty("details")
    private String details;

    //private UserDTO creator;

    @JsonProperty("file")
    private List<String> files;

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getDetails() {
        return details;
    }

    public List<String> getFiles() {
        return files;
    }

    public String getFilesString() {
        if (files == null || files.size() == 0)
            return "";
        StringBuilder builder = new StringBuilder();
        Iterator<String> iterator = files.iterator();
        while (iterator.hasNext()) {
            builder.append(iterator.next());
            if (iterator.hasNext())
                builder.append(ConstantUtil.DELIMITER);
        }
        return builder.toString();
    }
}
