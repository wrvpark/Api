package com.wrvpark.apiserver.domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wrvpark.apiserver.dto.requests.authentication.AdminUpdateUserRequest;

/**
 * @author Vahid Haghighat
 */
public class Role {
    private final boolean isOwner;
    private final boolean isRenter;
    private final boolean isBoardMember;
    private final boolean isParkManagement;
    private final boolean isAdmin;
    private final boolean isVisitor;
    private final boolean isUnapproved;

    public Role(boolean isOwner, boolean isRenter, boolean isBoardMember, boolean isParkManagement, boolean isAdmin, boolean isVisitor, boolean isUnapproved) {
        this.isOwner = isOwner;
        this.isRenter = isRenter;
        this.isBoardMember = isBoardMember;
        this.isParkManagement = isParkManagement;
        this.isAdmin = isAdmin;
        this.isVisitor = isVisitor;
        this.isUnapproved = isUnapproved;
    }

    public Role (String input) {
        ObjectMapper jsonMapper = new ObjectMapper();
        JsonNode roles = null;
        try {
            roles = jsonMapper.readTree(input);
        } catch (Exception ignored) {}
        this.isAdmin = roles != null && roles.get("isAdmin").asBoolean();
        this.isBoardMember = roles != null && roles.get("isBoardMember").asBoolean();
        this.isOwner = roles != null && roles.get("isOwner").asBoolean();
        this.isRenter = roles != null && roles.get("isRenter").asBoolean();
        this.isParkManagement = roles != null && roles.get("isParkManagement").asBoolean();
        this.isVisitor = roles != null && roles.get("isVisitor").asBoolean();
        this.isUnapproved = roles != null && roles.get("isUnapproved").asBoolean();
    }

    public Role (AdminUpdateUserRequest input) {
        this.isAdmin = input.isAdmin();
        this.isOwner = input.isOwner();
        this.isRenter = input.isRenter();
        this.isBoardMember = input.isBoardMember();
        this.isUnapproved = input.isUnapproved();
        this.isVisitor = input.isVisitor();
        this.isParkManagement = input.isParkManagement();
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

    @Override
    public String toString() {
        return "{" +
                "\"isOwner\": " + isOwner +
                ", \"isRenter\": " + isRenter +
                ", \"isBoardMember\": " + isBoardMember +
                ", \"isParkManagement\": " + isParkManagement +
                ", \"isAdmin\": " + isAdmin +
                ", \"isVisitor\": " + isVisitor +
                ", \"isUnapproved\": " + isUnapproved +
                '}';
    }
}