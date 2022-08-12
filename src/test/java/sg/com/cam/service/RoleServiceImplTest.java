package sg.com.cam.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import sg.com.cam.entity.krisadmin.*;
import sg.com.cam.model.group.*;
import sg.com.cam.repository.krisadmin.EventLogRepository;
import sg.com.cam.repository.krisadmin.RoleRepository;
import sg.com.cam.service.impl.RoleServiceImpl;
import sg.com.cam.util.CustomException;
import sg.com.cam.util.Pager;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class RoleServiceImplTest {


    @InjectMocks
    private RoleServiceImpl roleServiceImpl;

    @Mock
    private RoleRepository roleRepository;
    @Mock
    private AuditLogService auditLogService;
    @Mock
    private EventLogRepository eventLogRepository;
    private AutoCloseable closeable;

    @BeforeEach
    public void init() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }

    @Test
    void getRoleByNameNullParameter() throws Exception {
        //check null param
        try { roleServiceImpl.getRoleByName(null); }
        catch (CustomException e){ assertTrue(e.getMessage().contains("mandatory")); }
    }
    @Test
    void getRoleByNameRepositoryResultIsEmptyList() throws Exception {
        //check group does not exist
        when(roleRepository.findRoleByName("groupId")).thenReturn(new RoleEntity());
        try { roleServiceImpl.getRoleByName("groupId"); }
        catch (CustomException e){ assertTrue(e.getMessage().contains("found")); }
    }

    @Test
    void getRoleByNameCheckGroupName() throws Exception {
        //check for right input
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName("role one");
        roleEntity.setLastEditedOn(new Timestamp(System.currentTimeMillis()));
        roleEntity.setCreatedOn(new Timestamp(System.currentTimeMillis()));
        when(roleRepository.findRoleByName(anyString())).thenReturn(roleEntity);
        PermissionEntity permissionEntity = new PermissionEntity();
        permissionEntity.setDescription("FullAccess");
        when(roleRepository.getRolePermissionByName(anyString())).thenReturn(Arrays.asList(permissionEntity));
        UserAdminEntity userAdminEntity = new UserAdminEntity();
        userAdminEntity.setName("userName");
        when(roleRepository.getUserByRoleName(anyString())).thenReturn(Arrays.asList(userAdminEntity));
        //first invocation
        GroupInfoResponse response = (GroupInfoResponse) roleServiceImpl.getRoleByName("groupId");
        //verify invocation
        verify(roleRepository, times(1)).findRoleByName(ArgumentMatchers.any(String.class));
        verify(roleRepository,times(1)).getRolePermissionByName(ArgumentMatchers.any(String.class));
        verify(roleRepository,times(1)).getUserByRoleName(ArgumentMatchers.any(String.class));
    }

    @Test
    void getRoleByCriteriaCheckParameterValidation() throws Exception{
        GroupListRequest groupRequest = new GroupListRequest();
        groupRequest.setStartIndex(-1);
        groupRequest.setItemsPerPage(1);
        //check negatif value of startindex
        try { roleServiceImpl.getRoleByCriteria(groupRequest); }
        catch (CustomException e){ assertTrue(e.getMessage().contains("more")); }

        groupRequest.setStartIndex(1);
        groupRequest.setItemsPerPage(-1);
        //check negatif value of ItemPerPage
        try { roleServiceImpl.getRoleByCriteria(groupRequest); }
        catch (CustomException e){ assertTrue(e.getMessage().contains("more")); }

        groupRequest.setStartIndex(1);
        groupRequest.setItemsPerPage(1);
        groupRequest.setAscOrderBy("wrong param");
        //check AscOrderBy with wrong value
        try { roleServiceImpl.getRoleByCriteria(groupRequest); }
        catch (CustomException e){ assertTrue(e.getMessage().contains("invalid")); }

        groupRequest.setStartIndex(1);
        groupRequest.setItemsPerPage(1);
        groupRequest.setDescOrderBy("wrong param");
        //check DescOrderBy with wrong value
        try { roleServiceImpl.getRoleByCriteria(groupRequest); }
        catch (CustomException e){ assertTrue(e.getMessage().contains("invalid")); }
    }

    @Test
    void getRoleByCriteriaCheckAscOrderBy() throws Exception{
        GroupListRequest groupRequest = new GroupListRequest();

        groupRequest.setStartIndex(1);
        groupRequest.setItemsPerPage(1);
        Pager pager = new Pager();
        when(roleRepository.getRoleByCriteria(pager)).thenReturn(new ArrayList<RoleEntity>());
        when(roleRepository.countRoleByCriteria(pager)).thenReturn(5);
        groupRequest.setAscOrderBy("groupID");
        //first invocation
        roleServiceImpl.getRoleByCriteria(groupRequest);
        groupRequest.setAscOrderBy("groupName");
        //second invocation
        GroupInfoListResponse groupInfoListResponse = (GroupInfoListResponse) roleServiceImpl.getRoleByCriteria(groupRequest);
        //verify invocation
        verify(roleRepository, times(2)).getRoleByCriteria(ArgumentMatchers.any(Pager.class));
        verify(roleRepository,times(2)).countRoleByCriteria(ArgumentMatchers.any(Pager.class));
        assertEquals(1, groupInfoListResponse.getStartIndex());
    }

    @Test
    void getRoleByCriteriaChecksetDescOrderBy() throws Exception{
        GroupListRequest groupRequest = new GroupListRequest();
        groupRequest.setStartIndex(1);
        groupRequest.setItemsPerPage(1);
        Pager pager = new Pager();
        when(roleRepository.getRoleByCriteria(pager)).thenReturn(new ArrayList<RoleEntity>());
        when( roleRepository.countRoleByCriteria(pager)).thenReturn(5);
        groupRequest.setDescOrderBy("groupID");
        //first invocation
        roleServiceImpl.getRoleByCriteria(groupRequest);
        groupRequest.setDescOrderBy("groupName");
        //second invocation
        GroupInfoListResponse groupInfoListResponse = (GroupInfoListResponse) roleServiceImpl.getRoleByCriteria(groupRequest);
        //verify invocation
        verify(roleRepository, times(2)).getRoleByCriteria(ArgumentMatchers.any(Pager.class));
        verify(roleRepository,times(2)).countRoleByCriteria(ArgumentMatchers.any(Pager.class));
        assertEquals(1, groupInfoListResponse.getStartIndex());

    }

    @Test
    void getRoleByCriteriaCheckDetail() throws Exception{
        // check for normal or right use case
        GroupListRequest groupRequest = new GroupListRequest();
        groupRequest.setStartIndex(1);
        groupRequest.setItemsPerPage(1);
        groupRequest.setFilter("test");
        Pager pager = new Pager();
        pager.setPageNumber(groupRequest.getStartIndex());
        pager.setPageSize(groupRequest.getItemsPerPage());
        pager.setSearchCriteria(groupRequest.getFilter());
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName("groupOne");
        roleEntity.setLastEditedOn(new Timestamp(System.currentTimeMillis()));
        roleEntity.setCreatedOn(new Timestamp(System.currentTimeMillis()));
        PermissionEntity permissionEntity = new PermissionEntity();
        permissionEntity.setDescription("FullAccess");
        when(roleRepository.getRolePermissionByName(anyString())).thenReturn(Arrays.asList(permissionEntity));
        UserAdminEntity userAdminEntity = new UserAdminEntity();
        userAdminEntity.setName("userName");
        when(roleRepository.getUserByRoleName(anyString())).thenReturn(Arrays.asList(userAdminEntity));
        when(roleRepository.getRoleByCriteria(pager)).thenReturn(Arrays.asList(roleEntity));
        when(roleRepository.countRoleByCriteria(pager)).thenReturn(5);

        GroupInfoListResponse groupInfoListResponse = (GroupInfoListResponse) roleServiceImpl.getRoleByCriteria(groupRequest);
        //verify invocation
        verify(roleRepository).getRolePermissionByName(ArgumentMatchers.any(String.class));
        verify(roleRepository).getUserByRoleName(ArgumentMatchers.any(String.class));
        verify(roleRepository, times(1)).getRoleByCriteria(ArgumentMatchers.any(Pager.class));
        verify(roleRepository, times(1)).countRoleByCriteria(ArgumentMatchers.any(Pager.class));
        assertEquals(1, groupInfoListResponse.getStartIndex());
    }

    @Test
    void inserOrRemoveRoleMemberCheckMandatoryParamater() throws Exception{
        GroupMemberUserRequest groupMemberUserRequest = new GroupMemberUserRequest();
        groupMemberUserRequest.setOperation(new  GroupHelperRequest.OperationChild());

        groupMemberUserRequest.setGroupId(null);
        groupMemberUserRequest.getOperation().setValue("userId");
        groupMemberUserRequest.getOperation().setOp("Add");
        //check null on groupId
        try { roleServiceImpl.inserOrRemoveRoleMember(groupMemberUserRequest); }
        catch (CustomException e){ assertTrue(e.getMessage().contains("mandatory")); }

        groupMemberUserRequest.setGroupId("groupId");
        groupMemberUserRequest.getOperation().setValue("value");
        groupMemberUserRequest.getOperation().setOp(null);
        //check null on op
        try { roleServiceImpl.inserOrRemoveRoleMember(groupMemberUserRequest); }
        catch (CustomException e){ assertTrue(e.getMessage().contains("mandatory")); }

        groupMemberUserRequest.setGroupId("groupId");
        groupMemberUserRequest.getOperation().setValue(null);
        groupMemberUserRequest.getOperation().setOp("op");
        //check null on value
        try { roleServiceImpl.inserOrRemoveRoleMember(groupMemberUserRequest); }
        catch (CustomException e){ assertTrue(e.getMessage().contains("mandatory")); }
    }

    @Test
    void inserOrRemoveRoleMemberCheckNullOfRoleAndUser() throws Exception{
        GroupMemberUserRequest groupMemberUserRequest = new GroupMemberUserRequest();
        groupMemberUserRequest.setOperation(new  GroupHelperRequest.OperationChild());
        groupMemberUserRequest.setGroupId("groupId");
        groupMemberUserRequest.getOperation().setOp("Add");
        groupMemberUserRequest.getOperation().setValue("userName");
        when(roleRepository.findRoleByName("groupId")).thenReturn(null);
        //check if role is null
        try { roleServiceImpl.inserOrRemoveRoleMember(groupMemberUserRequest); }
        catch (CustomException e){ assertTrue(e.getMessage().contains("found")); }

        when(roleRepository.findRoleByName("groupId")).thenReturn(new RoleEntity());
        when(roleRepository.findUserByName("userName")).thenReturn(null);
        //check if user is null
        try { roleServiceImpl.inserOrRemoveRoleMember(groupMemberUserRequest); }
        catch (CustomException e){ assertTrue(e.getMessage().contains("found")); }
        //verify invocation
        verify(roleRepository, times(2)).findRoleByName(ArgumentMatchers.any(String.class));
        verify(roleRepository,times(1)).findUserByName(ArgumentMatchers.any(String.class));
    }

    @Test
    void inserOrRemoveRoleMemberCheckRemoveOrAdd() throws Exception{
        // check normal / right use case
        GroupMemberUserRequest groupMemberUserRequest = new GroupMemberUserRequest();
        groupMemberUserRequest.setOperation(new  GroupHelperRequest.OperationChild());
        groupMemberUserRequest.setGroupId("groupId");
        groupMemberUserRequest.getOperation().setValue("userName");

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRoleId(3L);
        roleEntity.setName("role one");
        UserAdminEntity userAdmin = new UserAdminEntity();
        userAdmin.setUserId(4L);
        userAdmin.setName("userOne");
        when(roleRepository.findRoleByName("groupId")).thenReturn(roleEntity);
        when(roleRepository.findUserByName("userName")).thenReturn(userAdmin);
        doNothing().when(roleRepository).insertRoleMember(new RoleMemberEntity());
        doNothing().when(roleRepository).removeRoleMember(new RoleMemberEntity());
        doNothing().when(auditLogService).save(new AuditLogEntity());
        //check add
        groupMemberUserRequest.getOperation().setOp("Add");
        roleServiceImpl.inserOrRemoveRoleMember(groupMemberUserRequest);
        //check remove
        groupMemberUserRequest.getOperation().setOp("Remove");
        roleServiceImpl.inserOrRemoveRoleMember(groupMemberUserRequest);
        //verify invocation
        verify(roleRepository, times(2)).findRoleByName(ArgumentMatchers.any(String.class));
        verify(roleRepository,times(2)).findUserByName(ArgumentMatchers.any(String.class));
        verify(roleRepository,times(1)).insertRoleMember(ArgumentMatchers.any(RoleMemberEntity.class));
        verify(roleRepository,times(1)).removeRoleMember(ArgumentMatchers.any(RoleMemberEntity.class));
        verify(auditLogService,times(2)).save(ArgumentMatchers.any(AuditLogEntity.class));
    }

    @Test
    void removeRoleByNameCheckNullParam() throws Exception{
        //check null param
        try { roleServiceImpl.removeRoleByName(null); }
        catch (CustomException e){ assertTrue(e.getMessage().contains("mandatory")); }

        //check whether group exist or not
        when(roleRepository.findRoleByName("groupId")).thenReturn(null);
        try { roleServiceImpl.removeRoleByName("groupId"); }
        catch (CustomException e){ assertTrue(e.getMessage().contains("found")); }
        //verify invocation
        verify(roleRepository, times(1)).findRoleByName(ArgumentMatchers.any(String.class));
    }

    @Test
    void removeRoleByNameCheckNullCompartmentId() throws Exception{
        RoleServiceImpl spyRoleServiceImpl = spy(roleServiceImpl);
        RoleEntity role = new RoleEntity();
        role.setRoleId(2L);
        role.setName("roleOne");
        when(roleRepository.findRoleByName("roleName")).thenReturn(role);
        when(roleRepository.getCompartmentByRoleId("roleName")).thenReturn(Arrays.asList(new CompartmentEntity()));
        doNothing().when(spyRoleServiceImpl).removeRole(anyLong());
        spyRoleServiceImpl.removeRoleByName("roleName");

        //verify invocation
        verify(roleRepository, times(1)).findRoleByName(ArgumentMatchers.any(String.class));
        verify(roleRepository, times(1)).getCompartmentByRoleId(ArgumentMatchers.any(String.class));
        verify(spyRoleServiceImpl, atLeast(1)).removeRole(ArgumentMatchers.any(Long.class));
    }

    @Test
    void removeRoleByNameCheckNotNullCompartmentId() throws Exception{
        RoleServiceImpl spyRoleServiceImpl = spy(roleServiceImpl);
        RoleEntity role = new RoleEntity();
        role.setRoleId(2L);
        role.setName("roleOne");
        when(roleRepository.findRoleByName("roleName")).thenReturn(role);
        CompartmentEntity compartmentEntity = new CompartmentEntity();
        compartmentEntity.setCompartmentId(1L);
        compartmentEntity.setName("compName");
        compartmentEntity.setType(1);
        when(roleRepository.getCompartmentByRoleId("roleName")).thenReturn(Arrays.asList(compartmentEntity));
        doReturn(new ArrayList<>()).when(spyRoleServiceImpl).getFileUnderCompartment(anyLong(), anyList());
        RequestFileEntity requestFileEntity = new RequestFileEntity();
        requestFileEntity.setSysId(1L);
        requestFileEntity.setStatus("ok");
        requestFileEntity.setFileTitle("req file one");
        when(roleRepository.getReqestFileByCompartmentId(1L)).thenReturn(Arrays.asList(requestFileEntity));
        doAnswer((invocation) -> {
            List<CompartmentEntity> lsAja = invocation.getArgument(0);
            lsAja.add(compartmentEntity);
            lsAja.add(compartmentEntity);
            return null;
        }).when(spyRoleServiceImpl).getChildCompartments(anyList(), anyLong());
        doNothing().when(spyRoleServiceImpl).RemoveRoleAndCompartment(anyLong(), anyLong());
        //call unit test method
        spyRoleServiceImpl.removeRoleByName("roleName");

        //verify invocation
        verify(roleRepository, atLeast(1)).findRoleByName(ArgumentMatchers.any(String.class));
        verify(roleRepository, atLeast(1)).getCompartmentByRoleId(ArgumentMatchers.any(String.class));
        verify(spyRoleServiceImpl, atLeast(1)).getFileUnderCompartment(ArgumentMatchers.any(), ArgumentMatchers.any());
        verify(spyRoleServiceImpl, atLeast(1)).RemoveRoleAndCompartment(ArgumentMatchers.any(Long.class), ArgumentMatchers.any(Long.class));
        verify(roleRepository, atLeast(1)).getReqestFileByCompartmentId(ArgumentMatchers.any(Long.class));
        verify(spyRoleServiceImpl, atLeast(1)).getChildCompartments(ArgumentMatchers.any(ArrayList.class), ArgumentMatchers.any(Long.class));
    }

    @Test
    void removeRoleCheckAll() throws Exception{
        RoleEntity role = new RoleEntity();
        role.setRoleId(2L);
        role.setName("roleOne");
        when(roleRepository.findRoleById(1L)).thenReturn(role);
        when(roleRepository.findRoleParentById(1L)).thenReturn(new ArrayList<>());
        doNothing().when(roleRepository).removeRole(1L);
        doNothing().when(auditLogService).save(new AuditLogEntity());
        doNothing().when(eventLogRepository).save(new EventLogEntity());
        roleServiceImpl.removeRole(1L);

        //verify invocation
        verify(roleRepository, times(1)).findRoleById(ArgumentMatchers.any(Long.class));
        verify(roleRepository, times(1)).findRoleParentById(ArgumentMatchers.any(Long.class));
        verify(roleRepository).removeRole(ArgumentMatchers.any(Long.class));
        verify(auditLogService).save(ArgumentMatchers.any(AuditLogEntity.class));
        verify(eventLogRepository).save(ArgumentMatchers.any(EventLogEntity.class));
    }

    @Test
    void deleteComptCheckAll() throws Exception{
        CompartmentEntity compartment = new CompartmentEntity();
        compartment.setCompartmentId(1L);
        compartment.setName("roleOne");
        compartment.setType(1);
        when(roleRepository.findCompartmentById(1L)).thenReturn(compartment);
        when(roleRepository.getCompartmentByParentId(1L)).thenReturn(new ArrayList<>());
        doNothing().when(roleRepository).removeCompartment(1L);
        doNothing().when(auditLogService).save(new AuditLogEntity());
        doNothing().when(eventLogRepository).save(new EventLogEntity());
        roleServiceImpl.deleteCompt(1L);

        //verify invocation
        verify(roleRepository, times(1)).findCompartmentById(ArgumentMatchers.any(Long.class));
        verify(roleRepository, times(1)).getCompartmentByParentId(ArgumentMatchers.any(Long.class));
        verify(roleRepository).removeCompartment(ArgumentMatchers.any(Long.class));
        verify(auditLogService).save(ArgumentMatchers.any(AuditLogEntity.class));
        verify(eventLogRepository).save(ArgumentMatchers.any(EventLogEntity.class));
    }

    @Test
    void getFileUnderCompartmentCheckAll() throws Exception{
        RoleServiceImpl spyRoleServiceImpl = spy(roleServiceImpl);
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileId(1L);
        fileEntity.setTitle("file");
        when(roleRepository.getFileByCompartmentId(1L)).thenReturn(Arrays.asList(fileEntity));
        CompartmentEntity compartmentEntity = new CompartmentEntity();
        compartmentEntity.setCompartmentId(1L);
        compartmentEntity.setName("compName");
        List<CompartmentEntity> lsCompartmentEntityList = Arrays.asList(compartmentEntity);
        doNothing().when(spyRoleServiceImpl).getChildCompartments(lsCompartmentEntityList, 1L);
        when(roleRepository.getCompartmentByParentId(1L)).thenReturn(new ArrayList<>());

        spyRoleServiceImpl.getFileUnderCompartment(1L, new ArrayList<FileEntity>());

        //verify invocation
        verify(roleRepository, times(1)).getFileByCompartmentId(ArgumentMatchers.any(Long.class));
        verify(roleRepository, times(1)).getCompartmentByParentId(ArgumentMatchers.any(Long.class));
        verify(spyRoleServiceImpl, atLeast(1)).getChildCompartments(ArgumentMatchers.any(List.class), ArgumentMatchers.any(Long.class));

    }
}
