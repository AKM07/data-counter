package sg.com.cam.util;


public class ConstantsLog {

    /**
     *
     * <summary>This list of event ID can be expanded.</summary>
     *<summary>The System settings event ID range from 1000 - 9999.</summary>
     *<summary>General settings</summary>
     *
     */
    public static final Integer GENERAL_SETTINGS = 1000;
    /**
     *<summary>The role event ID range from 10000 - 19999.</summary>
     *<summary>Role name change event</summary>
     */
    public static final Integer ROLE_NAME_CHANGE = 10000;
    /**
     *<summary>New Role</summary>
     */
    public static final Integer ADD_ROLE_CHANGE = 10001;
    /**
     * <summary>Delete Role</summary>
     */
    public static final Integer DELETE_ROLE_CHANGE = 10002;
    /**
     * <summary>Role description change event</summary>
     */
    public static final Integer ROLE_DESCRIPTION_CHANGE = 10003;
    /**
     * <summary>Role security change event</summary>
     */
    public static final Integer ROLE_SECURITY_CHANGE = 10004;
    /**
     * <summary>Role department change</summary>
     */
    public static final Integer ROLE_DEPARTMENT_CHANGE = 10005;


    /**
     * <summary>Role member add change event</summary>
     */
    public static final Integer ADD_ROLE_MEMBER_CHANGE = 10006;
    /**
     * <summary>Role member delete change event</summary>
     */
    public static final Integer DELETE_ROLE_MEMBER_CHANGE = 10007;
    /**
     * <summary>One to one Role member change event</summary>
     */
    public static final Integer ONE_TO_ONE_ROLE_MEMBER_CHANGE = 10008;
    /**
     * <summary>Role Profile Form change</summary>
     */
    public static final Integer ROLE_PROFILEFORM_CHANGE = 10009;

    /**
     * <summary>The group role event ID range from 20000 - 29999.</summary>
     * <summary>Group Role name change event</summary>
     */
    public static final Integer GROUP_ROLE_NAME_CHANGE = 20000;

    /**
     * <summary>The group role event ID range from 20000 - 29999.</summary>
     * <summary>Group Role name change event</summary>
     */
    public static final Integer GROUP_ROLE_DESCRIPTION_CHANGE = 20001;

    /**
     * <summary>The compartment event ID range from 30000 - 39999.</summary>
     * <summary>Compartment name change event</summary>
     */
    public static final Integer COMPARTMENT_NAME_CHANGE = 30000;
    /**
     * <summary>New Role</summary>
     */
    public static final Integer ADD_COMPARTMENT_CHANGE = 30001;
    /**
     * <summary>Delete Role</summary>
     */
    public static final Integer DELETE_COMPARTMENT_CHANGE = 30002;
    /**
     * <summary>Role description change event</summary>
     */
    public static final Integer COMPARTMENT_DESCRIPTION_CHANGE = 30003;
    /**
     * <summary>Role security change event</summary>
     */
    public static final Integer COMPARTMENT_SECURITY_CHANGE = 30004;
    /**
     * <summary>Role member change event</summary>
     */
    public static final Integer COMPARTMENT_ACL_CHANGE = 30005;

    /**
     * <summary>The subject event ID range from 40000 - 49999.</summary>
     *<summary>Subject name change event</summary>
     */
    public static final Integer SUBJECT_NAME_CHANGE = 40000;
    /*
     * <summary>New Subject</summary>
     */
    public static final Integer ADD_SUBJECT_CHANGE = 40001;
    /**
     * <summary>Delete Subject</summary>
     */
    public static final Integer DELETE_SUBJECT_CHANGE = 40002;
    /**
     * <summary>Subject description change event</summary>
     */
    public static final Integer SUBJECT_DESCRIPTION_CHANGE = 40003;
    /**
     * <summary>Subject security change event</summary>
     */
    public static final Integer SUBJECT_SECURITY_CHANGE = 40004;
    /**
     * <summary>Subject member change event</summary>
     */
    public static final Integer SUBJECT_ACL_CHANGE = 40005;

    /**
     * <summary>The fileref event ID range from 50000 - 59999.</summary>
     *<summary>FileRef name change event</summary>
     */
    public static final Integer FILEREF_NAME_CHANGE = 50000;
    /**
     * <summary>FileRef title change event</summary>
     */
    public static final Integer FILEREF_TITLE_NAME_CHANGE = 50001;
    /**
     * <summary>FileRef moved to different compartment event</summary>
     */
    public static final Integer FILEREF_MOVE_TO_DIFF_COMPT = 50002;
    /**
     * <summary>Refile event</summary>
     */
    public static final Integer FILEREF_REFILE = 50003;
    /**
     * <summary>Delete FileRef</summary>
     */
    public static final Integer FILEREF_DELETE = 50004;
    /**
     * <summary>New FileRef</summary>
     */
    public static final Integer FILEREF_ADD = 50005;
    /**
     * <summary>FileRef security grading change</summary>
     */
    public static final Integer FILEREF_SECURITY_GRADING_CHANGE = 50006;
    /**
     * <summary>FileRef security method change</summary>
     */
    public static final Integer FILEREF_SECURITY_METHOD_CHANGE = 50007;
    /**
     * <summary>FileRef status change
     */
    public static final Integer FILEREF_STATUS_CHANGE = 50008;
    /**
     * <summary>FileRef is closed change
     */
    public static final Integer FILEREF_ISClOSED = 50009;
    /**
     * <summary>FileRef ACL change
     */
    public static final Integer FILEREF_ACL_CHANGE = 50010;
    /**
     * <summary>Case name change event</summary>
     */
    public static final Integer CASE_NAME_CHANGE = 51000;
    /**
     * <summary>Case title change event</summary>
     */
    public static final Integer CASE_TITLE_NAME_CHANGE = 51001;
    /**
     * <summary>Case moved to different compartment event</summary>
     */
    public static final Integer CASE_MOVE_TO_DIFF_COMPT = 51002;
    /**
     * <summary>Case Refile event</summary>
     */
    public static final Integer CASE_REFILE = 51003;
    /**
     * <summary>Delete Case</summary>
     */
    public static final Integer CASE_DELETE = 51004;
    /**
     * <summary>New Case</summary>
     */
    public static final Integer CASE_ADD = 51005;
    /**
     * <summary>Case status change
     */
    public static final Integer CASE_STATUS_CHANGE = 51006;
    /**
     * <summary>Case is closed change
     */
    public static final Integer CASE_ISClOSED = 51007;
    /**
     * <summary>Master Enforcer Enforce Document </summary>
     */
    public static final Integer DOCUMENT_MASTER_ENFORCER = 52001;
    /**
     * <summary>Copy Enforcer Enforce Document </summary>
     */
    public static final Integer DOCUMENT_COPY_ENFORCER = 52002;
    /**
     * <summary>Update Enforcer Enforce Document </summary>
     */
    public static final Integer DOCUMENT_UPDATE_ENFORCER = 52003;
    /**
     * <summary>New Document</summary>
     */
    public static final Integer NEW_DOCUMENT = 52004;
}