package com.wrvpark.apiserver.dto;

import com.wrvpark.apiserver.domain.Event;
import com.wrvpark.apiserver.util.CommActions;
import com.wrvpark.apiserver.util.TimeUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Isabel Ke
 * Original date:2020-02-07
 *
 * Description:customize the event that will be returned to the client-side
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventDTO {
    private String id;
    private String title;
    private String description;
    private String createTime;
    private String startTime;
    private String endTime;
    private String details;
    private UserDTO creator;
    private String fileName;
    private String fileURL;
    private boolean isLimited;
    private SubcategoryDTO locationSubcategory;
    private SubcategoryDTO descSubcategory;
    private boolean recurring;
    private String recurFrequency;

    public EventDTO(Event event) {
        this.id = event.getId();
        this.title = event.getTitle();
        this.description = event.getDescription();
        this.createTime= TimeUtil.convertDateToString(event.getCreateTime());
        this.startTime = TimeUtil.convertDateToString(event.getStartTime());
        this.endTime = TimeUtil.convertDateToString(event.getEndTime());
        this.details = event.getDetails();
        this.creator = new UserDTO(event.getCreator());
        this.fileName = event.getFileName();
        this.fileURL = CommActions.generateURL(this.fileName);
        this.isLimited = event.isLimited();
        this.locationSubcategory = new SubcategoryDTO(event.getLocationSubcategory());
        this.descSubcategory = new SubcategoryDTO(event.getDescSubcategory());
        this.recurring = event.getRecurring() != null;
        this.recurFrequency = this.recurring ? event.getRecurring().getFrequency() : null;
    }
}
