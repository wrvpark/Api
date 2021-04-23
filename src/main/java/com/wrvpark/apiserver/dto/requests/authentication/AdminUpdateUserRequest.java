package com.wrvpark.apiserver.dto.requests.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * @author Vahid Haghighat
 */
public class AdminUpdateUserRequest {
    @JsonProperty ("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("lot_no")
    private List<Integer> lotNo;

    @JsonProperty("is_owner")
    private boolean isOwner;

    @JsonProperty("is_renter")
    private boolean isRenter;

    @JsonProperty("is_board_member")
    private boolean isBoardMember;

    @JsonProperty("is_park_management")
    private boolean isParkManagement;

    @JsonProperty("is_admin")
    private boolean isAdmin;

    @JsonProperty("is_visitor")
    private boolean isVisitor;

    @JsonProperty("is_unapproved")
    private boolean isUnapproved;

//    @JsonProperty("doc_notification")
//    private boolean docNotification;
//
//    @JsonProperty("event_notification")
//    private boolean eventNotification;
//
//    @JsonProperty("salerent_notification")
//    private boolean saleRentNotification;
//
//    @JsonProperty("service_notification")
//    private boolean serviceNotification;
//
//    @JsonProperty("lostfound_notification")
//    private boolean lostFoundNotification;
//
//    @JsonProperty("parkdocument_notification")
//    private boolean parkDocumentNotification;


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<Integer> getLotNo() {
        return lotNo;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public boolean isRenter() {
        return isRenter;
    }

    public boolean isBoardMember() {
        return isBoardMember;
    }

    public boolean isParkManagement() {
        return isParkManagement;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean isVisitor() {
        return isVisitor;
    }

    public boolean isUnapproved() {
        return isUnapproved;
    }

//    public boolean isDocNotification() {
//        return docNotification;
//    }
//
//    public boolean isEventNotification() {
//        return eventNotification;
//    }
//
//    public boolean isSaleRentNotification() {
//        return saleRentNotification;
//    }
//
//    public boolean isServiceNotification() {
//        return serviceNotification;
//    }
//
//    public boolean isLostFoundNotification() {
//        return lostFoundNotification;
//    }
//
//    public boolean isParkDocumentNotification() {
//        return parkDocumentNotification;
//    }
}