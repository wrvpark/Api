package com.wrvpark.apiserver.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wrvpark.apiserver.util.ConstantUtil;

import java.util.Iterator;
import java.util.List;

/**
 * @author Vahid Haghighat
 */
public class NewItemRequest {
    @JsonProperty(value = "subcategory_id")
    private String subcategoryId;

    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "lot_no")
    private int lotNo;

    @JsonProperty(value = "description")
    private String description;

    @JsonProperty(value = "mls_link")
    private String mlsLink;

    @JsonProperty(value = "price")
    private String price;

    @JsonProperty(value = "contact_info")
    private String contactInfo;

    @JsonProperty(value = "image")
    private List<String> images;

    @JsonProperty(value = "status")
    private String status;

    public String getSubcategoryId() {
        return subcategoryId;
    }

    public String getTitle() {
        return title;
    }

    public int getLotNo() {
        return lotNo;
    }

    public String getDescription() {
        return description;
    }

    public String getMlsLink() {
        return mlsLink;
    }

    public String getPrice() {
        return price;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public List<String> getImage() {
        return images;
    }

    public String getImagesString() {
        if (images == null || images.size() == 0)
            return "";
        StringBuilder builder = new StringBuilder();
        Iterator<String> iterator = images.iterator();
        while (iterator.hasNext()) {
            builder.append(iterator.next());
            if (iterator.hasNext())
                builder.append(ConstantUtil.DELIMITER);
        }
        return builder.toString();
    }

    public String getStatus() {
        return status;
    }
}
