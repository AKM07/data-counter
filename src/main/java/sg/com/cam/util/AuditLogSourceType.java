package sg.com.cam.util;


public enum AuditLogSourceType {
    SystemSetting(0, "System Setting"),
    Role(1, "Role"),
    RoleMember(2, "Role Member"),
    Compartment(3, "Compartment"),
    CompartmentTrustee(4, "Compartment Trustee"),
    GroupRole(5, "Group Role"),
    GroupRoleMember(6, "Group Role Member"),
    NTGroup(7,"NT Group"),
    NTGroupMember(8,"NT Group Member"),
    Department(9,"Department"),
    Permission(10,"Permission"),
    SecurityLevel(11,"Security Level"),
    OrgChartAdmin(12,"OrgChart Admin"),
    OrgChartAdminPermission(13,"OrgChart Admin Permission"),
    File(14,"File"),
    FileTrustee(15,"File Trustee"),
    MasterACL(16,"Master ACL"),
    Cases(17,"Cases"),
    CaseOfficer(18,"Case Officer"),
    FileAction(19,"File Action"),
    FileLevel(20,"File Level"),
    FileLevelDependency(21,"File Level Dependency"),
    FileSeparator(22,"File Separator"),
    HardcopyDisposalAction(23,"Hardcopy Disposal Action"),
    SoftcopyDisposalAction(24,"Softcopy Disposal Action"),
    Status(25,"Status"),
    User(26,"User"),
    Group(27,"Group"),
    Sequences(28,"Sequences"),
    EventsSubscription(29,"Events Subscription"),
    EventsQuery(30,"Events Query"),
    Subject(31,"Subject"),
    FileRequest(32,"FileRequest"),
    PurposeUser(34,"Purpose User"),
    WorkFlowGroupUser(35,"WorkFlow GroupUser"),
    WorkFlowGroup(36,"WorkFlow Group")
    ,AutoSendReport(34,"Auto Send Report")
    ,WorkFlowRule(37,"WorkFlow Rule")
    ,Tag(38,"Tag"),
    RegistrationFailure(39,"Registration Failure"),
    RegistrationSuccess(40,"Registration Success"),
    LoginFailure(41,"Login Failure"),
    LoginSuccess(42,"Login Success"),
    MFASetupInstruction(43,"MFA Setup Instruction"),
    MFARegistration(44,"MFA Registration"),
    MFALogin(45,"MFA Login"),
    Sensitivity(46,"Sensitivity"),
    ;


    private AuditLogSourceType(int value, String name) {
        this.value = value;
        this.type = name;
    }

    private int value;
    private String type;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }






}
