package com.wrvpark.apiserver.util;

/**
 * @author Isabel Ke
 * @author Vahid Haghighat
 * Original date:2020-02-07
 *
 * Description:constant class that stores all the constants
 */
public class ConstantUtil {
    //where the pictures will be stored
    public static final String UPLOAD_FOLDER_NAME = "uploads";

    public static String uploadFolder() {
        if (System.getenv("SPRING_ENVIRONMENT").equals("DEV"))
            return "src/main/resources/static/" + UPLOAD_FOLDER_NAME;
        return "app/static/" + UPLOAD_FOLDER_NAME;
    }

    //sub-category type
    public static final String LOCATION_SUB_TYPE="location";
    public static final String DESC_SUB_TYPE="description";
    public static final String STATUS_CREATED="CREATED";
    public static final String STATUS_DELETED="DELETED";

    //the audit log action types
    public static final String AUDIT_LOG_CREATED="CREATE";
    public static final String AUDIT_LOG_DELETED="DELETE";
    public static final String AUDIT_LOG_UPDATED="UPDATE";

    //item types
    public static final String TYPE_LOST_FOUND="Lost & Found";
    public static final String TYPE_SERVICES="Services";
    public static final String TYPE_SALE_RENT="For Sale or Rent";
    public static final String ITEM_STATUS="Active";

    //role types
    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_RENTER = "renter";
    public static final String ROLE_OWNER = "owner";
    public static final String ROLE_VISITOR = "visitor";
    public static final String ROLE_UNAPPROVED = "unapproved";
    public static final String ROLE_PARK_MANAGEMENT = "management";
    public static final String ROLE_BOARD_MEMBER = "board";

    //access group
    public static String [] MANAGEMENT_ROLE = {ROLE_ADMIN, ROLE_PARK_MANAGEMENT, ROLE_BOARD_MEMBER};
    public static String [] ALL_ROLE = {ROLE_ADMIN, ROLE_PARK_MANAGEMENT, ROLE_BOARD_MEMBER, ROLE_RENTER, ROLE_OWNER};
    public static String [] PUBLIC_ROLES = {ROLE_VISITOR, ROLE_UNAPPROVED};

    //Delimiter
    public static String DELIMITER = "<>";
}
