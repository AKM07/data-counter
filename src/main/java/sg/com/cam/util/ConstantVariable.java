package sg.com.cam.util;

public class ConstantVariable {

    public static final String SCHEMA_USER = "urn:ietf:params:scim:schemas:core:2.0:User";
    public static final String SCHEMA_USER_ENTERPRISE = "urn:ietf:params:scim:schemas:extension:enterprise:2.0:User";
    public static final String SCHEMA_USER_EXTENTION = "urn:ietf:params:scim:schemas:extension:cam:2.0:User";
    public static final String SCHEMA_USER_ERROR = "urn:ietf:params:scim:api:messages:2.0:Error";
    public static final String SCHEMA_LIST_RESPONSE = "urn:ietf:params:scim:api:messages:2.0:ListResponse";
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final String ORDER_BY_ASC = "ASC";
    public static final String ORDER_BY_DESC = "DESC";
    public static final String AUTHENTICATION_ERROR_CODE = "401";
    public static final String NOT_FOUND_ERROR_CODE = "404";
    public static final String CAM_ACCOUNT_ID = "CAM_ACCOUNT_ID";
    public static final String CAM_SECRET_KEY = "CAM_SECRET_KEY";
    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String DATE_FORMAT_NO_TIMEZONE = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final int OPERATION_TYPE_CREATE = 0;
    public static final int OPERATION_TYPE_DELETE = 1;
    public static final int OPERATION_TYPE_UPDATE = 2;
    public static final int OPERATION_TYPE_APPROVE = 3;
    public static final int OPERATION_TYPE_REJECT = 4;
    public static final int OPERATION_TYPE_SEARCH = 5;
    public static final int OPERATION_TYPE_LOGIN = 6;
    public static final int CompartmentType_Role = 0;
    public static final int CompartmentType_Subject = 1;
    public static final int TrusteeType_GROUP = 0;
    public static final int TrusteeType_GROUPROLE = 1;
    public static final int TrusteeType_ROLE = 2;
    public static final int TrusteeType_USER = 3;


}
