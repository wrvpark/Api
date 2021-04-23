package com.wrvpark.apiserver.domain;

import com.wrvpark.apiserver.dto.requests.authentication.RegistrationRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Vahid Haghighat
 * @author Isabel Ke
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "RVPARK_NOTIFICATIONS")
public class Notification implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid2")
    @Column(name = "ID")
    private String id;

    @Column(name = "EVENT", nullable = false)
    private boolean event;

    @Column(name = "PARKDOCUMENT",nullable = false)
    private boolean parkDocument;

    @Column(name = "SALE_RENT",nullable = false)
    private boolean saleRent;

    @Column(name = "SERVICE",nullable = false)
    private boolean service;

    @Column(name = "LOST_FOUND",nullable = false)
    private boolean lostFound;

    @OneToOne(mappedBy = "notification")
    private User user;

    public Notification(RegistrationRequest request) {
        this.event = request.isEventNotification();
        this.parkDocument = request.isDocNotification();
        this.saleRent = request.isSaleRentNotification();
        this.service = request.isServiceNotification();
        this.lostFound = request.isLostFoundNotification();
    }
}