package com.wrvpark.apiserver.dto;


import com.wrvpark.apiserver.domain.NonParkDocumentLog;
import com.wrvpark.apiserver.util.TimeUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author Isabel Ke
 * Original date:2020-03-07
 *
 * Description:customize the audit log that will be returned to the client-side
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuditLogDTO{
    private String id;
    private String title;
    private String itemId;
    private String description;
    private CategoryDTO category;
    private UserDTO modifier;
    private String createTime;
    private String action;//UPDATE, DELETE, CREATE


    public AuditLogDTO(NonParkDocumentLog log) {
        this.id=log.getId();
        this.setItemId(log.getId());
        this.setAction(log.getAction());
        this.setCreateTime(TimeUtil.convertDateToString(log.getCreateTime()));
        this.setDescription(log.getDescription());
        this.setTitle(log.getReason());
        //set the modifier
        this.setModifier(new UserDTO(log.getModifier()));
        //add the category
       this.setCategory(new CategoryDTO(log.getCategory()));
    }
}
