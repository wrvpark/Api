package com.wrvpark.apiserver.domain;

import com.wrvpark.apiserver.dto.requests.NewEventDTO;
import com.wrvpark.apiserver.util.TimeUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Vahid Haghighat
 * @author Isabel Ke
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "RVPARK_EVENT_RECURINGS")
public class EventRecuring implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid2")
    @Column(name = "ID")
    private String id;

    @OneToOne(mappedBy = "recurring")
    private Event event;

    @Column(name = "START_DATE", nullable = false)
    private Date startDate;

    @Column(name = "END_DATE",nullable = false)
    private Date endDate;

    /*
    weekly,monthly,a specific day on each week
     */
    @Column(name = "FREQUENCY_DAYS")
    private String frequency;

    public EventRecuring(NewEventDTO eventDTO) {
        this.startDate = TimeUtil.convertStringToDate(eventDTO.getStartTime());
        this.endDate = TimeUtil.convertStringToDate(eventDTO.getEndTime());
        this.frequency = eventDTO.getRecurFrequency();
    }


}