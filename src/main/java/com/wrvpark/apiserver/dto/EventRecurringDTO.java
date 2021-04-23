package com.wrvpark.apiserver.dto;

import com.wrvpark.apiserver.domain.EventRecuring;
import com.wrvpark.apiserver.util.TimeUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author Isabel Ke
 * Original date:2020-02-07
 *
 * Description:customize the EventRecurring that will be returned to the client-side
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventRecurringDTO {
    private String startDate;
    private String endDate;
    private String frequency;

    public EventRecurringDTO(EventRecuring recuring) {
        this.startDate = TimeUtil.convertDateToString(recuring.getStartDate());
        this.endDate = TimeUtil.convertDateToString(recuring.getEndDate());
        this.frequency = recuring.getFrequency();

    }
}
