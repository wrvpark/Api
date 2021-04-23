package com.wrvpark.apiserver.dto.requests.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author Vahid Haghighat
 */
public class RegistrationRequest implements Serializable {
    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("lot_no")
    private int lotNo;

    @JsonProperty("password")
    private String password;

    @JsonProperty("confirm_password")
    private String confirmPassword;

    @JsonProperty("email")
    private String email;

    @JsonProperty("primary_phone")
    private String primaryPhone;

    @JsonProperty("secondary_phone")
    private String secondaryPhone;

    @JsonProperty("is_owner")
    private boolean isOwner;

    @JsonProperty("is_renter")
    private boolean isRenter;

//    @JsonProperty("is_board_member")
//    private boolean isBoardMember;
//
//    @JsonProperty("is_park_management")
//    private boolean isParkManagement;
//
//    @JsonProperty("is_admin")
//    private boolean isAdmin;

    @JsonProperty("picture")
    private String picture;

    @JsonProperty("doc_notification")
    private boolean docNotification;

    @JsonProperty("event_notification")
    private boolean eventNotification;

    @JsonProperty("salerent_notification")
    private boolean saleRentNotification;

    @JsonProperty("service_notification")
    private boolean serviceNotification;

    @JsonProperty("lostfound_notification")
    private boolean lostFoundNotification;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getLotNo() {
        return lotNo;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public String getPrimaryPhone() {
        return primaryPhone;
    }

    public String getSecondaryPhone() {
        return secondaryPhone;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public boolean isRenter() {
        return isRenter;
    }

//    public boolean isBoardMember() {
//        return isBoardMember;
//    }
//
//    public boolean isParkManagement() {
//        return isParkManagement;
//    }
//
//    public boolean isAdmin() {
//        return isAdmin;
//    }

    public String getPicture() {
        return picture;
    }

    public boolean isDocNotification() {
        return docNotification;
    }

    public boolean isEventNotification() {
        return eventNotification;
    }

    public boolean isSaleRentNotification() {
        return saleRentNotification;
    }

    public boolean isServiceNotification() {
        return serviceNotification;
    }

    public boolean isLostFoundNotification() {
        return lostFoundNotification;
    }
}