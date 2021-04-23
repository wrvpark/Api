package com.wrvpark.apiserver.dto;

import com.wrvpark.apiserver.domain.Item;
import com.wrvpark.apiserver.util.CommActions;
import com.wrvpark.apiserver.util.ConstantUtil;
import com.wrvpark.apiserver.util.TimeUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Chen Zhao
 * @author Vahid Haghighat
 * Original date:2020-02-018
 *
 * Description:customize the item that will be returned to the client-side
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemDTO {
    private String id;
    private SubcategoryDTO subcategory;
    private String title;
    private int lotNo;
    private String description;
    private String mlsLink;
    private String price;
    private String constructorInfo;
    private List<String> image;
    private UserDTO creator;
    private String createTime;
    private UserDTO renter;
    private String status;
    private String itemType;
    private List<String> urls;

    public ItemDTO(Item item) {
        this.id = item.getId();
        this.subcategory = new SubcategoryDTO(item.getSubcategory());
        this.title = item.getTitle();
        this.lotNo = item.getLotNo();
        this.description = item.getDescription();
        this.mlsLink = item.getMlsLink();
        this.price = item.getPrice();
        this.constructorInfo = item.getConstructorInfo();
        this.creator = new UserDTO(item.getCreator());
        this.createTime = TimeUtil.convertDateToString(item.getCreateTime());
        this.renter = item.getRenter() == null ? null : new UserDTO(item.getRenter());
        this.status = item.getStatus();
        this.itemType = item.getItemType();

        this.image = new ArrayList<>();
        this.urls = new ArrayList<>();
        if(item.getImage() != null && !item.getImage().isEmpty())
        {
            String[] files = item.getImage().split(ConstantUtil.DELIMITER);
            for (String fileString : files) {
                image.add(fileString);
                urls.add(CommActions.generateURL(fileString));
            }
        }
    }

    public String getImageString() {
        if (image == null || image.size() == 0)
            return "";
        StringBuilder builder = new StringBuilder();
        Iterator<String> iterator = image.iterator();
        while (iterator.hasNext()) {
            builder.append(iterator.next());
            if (iterator.hasNext())
                builder.append(ConstantUtil.DELIMITER);
        }
        return builder.toString();
    }
}
