package com.wrvpark.apiserver.dto;

/**
 * @author Vahid Haghighat
 */
public class NotificationDTO {
    private boolean event;
    private boolean parkDocument;
    private boolean saleRent;
    private boolean service;
    private boolean lostFound;
    private String  userId;

    public NotificationDTO(boolean event, boolean parkDocument, boolean saleRent, boolean service, boolean lostFound, String userId) {
        this.event = event;
        this.parkDocument = parkDocument;
        this.saleRent = saleRent;
        this.service = service;
        this.lostFound = lostFound;
        this.userId = userId;
    }

    public boolean isEvent() {
        return event;
    }

    public void setEvent(boolean event) {
        this.event = event;
    }

    public boolean isParkDocument() {
        return parkDocument;
    }

    public void setParkDocument(boolean parkDocument) {
        this.parkDocument = parkDocument;
    }

    public boolean isSaleRent() {
        return saleRent;
    }

    public void setSaleRent(boolean saleRent) {
        this.saleRent = saleRent;
    }

    public boolean isService() {
        return service;
    }

    public void setService(boolean service) {
        this.service = service;
    }

    public boolean isLostFound() {
        return lostFound;
    }

    public void setLostFound(boolean lostFound) {
        this.lostFound = lostFound;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
