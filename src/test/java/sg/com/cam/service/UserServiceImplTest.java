package sg.com.cam.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sg.com.cam.entity.krisadmin.DepartmentEntity;
import sg.com.cam.entity.krisadmin.UserAdminEntity;
import sg.com.cam.entity.krm.UserKrmEntity;
import sg.com.cam.model.user.*;
import sg.com.cam.repository.krisadmin.UserRepository;
import sg.com.cam.repository.krm.UserKrmRepository;
import sg.com.cam.service.impl.UserServiceImpl;
import sg.com.cam.util.CustomException;
import sg.com.cam.util.Pager;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userServiceImpl;


    @Mock
    private UserRepository userRepository;

    @Mock
    private UserKrmRepository userKrmRepository;

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
    void getUserInfoNullParameter() throws Exception {
        try {
            userServiceImpl.getUserInfo(null);
        } catch (CustomException e) {
            assertTrue(e.getMessage().contains("can't be empty"));
        }
    }

    @Test
    void getUserInfoNotFound() throws Exception {
        try {
            userServiceImpl.getUserInfo("test");
        } catch (CustomException e) {
            assertTrue(e.getMessage().contains("not found"));
        }
    }

    @Test
    void getUserByCriteriaParamValidation() throws Exception {
        UserListRequest request = new UserListRequest();
        request.setStartIndex(0);
        request.setItemsPerPage(0);

        try {
            userServiceImpl.getUserByCriteria(request);
        } catch (CustomException e) {
            assertTrue(e.getMessage().contains("minimum is 1") || e.getMessage().contains("Items per page can't less than 1"));
        }
    }

    @Test
    void getUserByCriteriaCheckAscOrderBy() throws Exception{
        UserListRequest userListRequest = new UserListRequest();

        userListRequest.setStartIndex(1);
        userListRequest.setItemsPerPage(1);
        Pager pager = new Pager();
        when(userRepository.getUserByCriteria(pager)).thenReturn(new ArrayList<Object[]>());
        when(userRepository.countRoleUserByCriteria(pager)).thenReturn(5);
        userListRequest.setAscOrderBy("groupID");
        //first invocation
        userServiceImpl.getUserByCriteria(userListRequest);
        userListRequest.setAscOrderBy("groupName");
        //second invocation
        UserListResponse response = userServiceImpl.getUserByCriteria(userListRequest);
        //verify invocation
        verify(userRepository, times(2)).getUserByCriteria(ArgumentMatchers.any(Pager.class));
        verify(userRepository,times(2)).countRoleUserByCriteria(ArgumentMatchers.any(Pager.class));
        assertEquals(1, response.getStartIndex());
    }

    @Test
    void getUserByCriteriaChecksetDescOrderBy() throws Exception{
        UserListRequest userListRequest = new UserListRequest();

        userListRequest.setStartIndex(1);
        userListRequest.setItemsPerPage(1);
        Pager pager = new Pager();
        when(userRepository.getUserByCriteria(pager)).thenReturn(new ArrayList<Object[]>());
        when(userRepository.countRoleUserByCriteria(pager)).thenReturn(5);
        userListRequest.setDescOrderBy("groupID");
        //first invocation
        userServiceImpl.getUserByCriteria(userListRequest);
        userListRequest.setDescOrderBy("groupName");
        //second invocation
        UserListResponse response = userServiceImpl.getUserByCriteria(userListRequest);
        //verify invocation
        verify(userRepository, times(2)).getUserByCriteria(ArgumentMatchers.any(Pager.class));
        verify(userRepository,times(2)).countRoleUserByCriteria(ArgumentMatchers.any(Pager.class));
        assertEquals(1, response.getStartIndex());

    }

    @Test
    void setAllowLoginStatusNullParameter() throws Exception {
        try {
            SetAllowLoginRequest request = new SetAllowLoginRequest();
            request.setUserId(null);
            userServiceImpl.setUserAllowLoginStatus(request);
        } catch (CustomException e) {
            assertTrue(e.getMessage().contains("can't be empty"));
        }
    }

    @Test
    void setAllowLoginStatus() throws Exception {
        when(userRepository.getUserInfoQuery("UserId")).thenReturn(new UserAdminEntity());
        when(userKrmRepository.getUserInfoFromKRMQuery("UserId")).thenReturn(new UserKrmEntity());
        when(userRepository.getUserDepartment("UserId")).thenReturn(new DepartmentEntity());
        doNothing().when(userRepository).setUserAllowLoginStatus(new SetAllowLoginRequest());

        //mock set status
        SetAllowLoginRequest request = new SetAllowLoginRequest();
        request.setUserId("UserId");

        SetAllowLoginOperationRequest operationRequest = new SetAllowLoginOperationRequest();
        operationRequest.setOperation("test");
        operationRequest.setActionValue(true);
        operationRequest.setPath("test");
        List<SetAllowLoginOperationRequest> operationRequestList = new ArrayList<>();
        operationRequestList.add(operationRequest);
        request.setOperation(operationRequestList);
        userServiceImpl.setUserAllowLoginStatus(request);

        verify(userRepository, times(1)).getUserInfoQuery(ArgumentMatchers.any(String.class));
        verify(userKrmRepository,times(1)).getUserInfoFromKRMQuery(ArgumentMatchers.any(String.class));
        verify(userRepository,times(1)).getUserDepartment(ArgumentMatchers.any(String.class));
        verify(userRepository,times(1)).setUserAllowLoginStatus(ArgumentMatchers.any(SetAllowLoginRequest.class));
    }

    @Test
    void setRemoveUserNullParameter() throws Exception {
        try {
            SetRemoveUserRequest request = new SetRemoveUserRequest();
            request.setUserId("");
            userServiceImpl.setRemoveUser(request);
        } catch (CustomException e) {
            assertTrue(e.getMessage().contains("can't be empty"));
        }
    }

    @Test
    void setRemoveUser() throws Exception {
        SetRemoveUserRequest removeUserRequest = new SetRemoveUserRequest();
        removeUserRequest.setUserId("UserId");
        when(userRepository.getUserInfoQuery("UserId")).thenReturn(new UserAdminEntity());
        when(userRepository.getUserRoleMemberIds("UserId")).thenReturn(new ArrayList<>());
        doNothing().when(userRepository).setRemoveUser(removeUserRequest);
        doNothing().when(userRepository).removeAccessUser("UserId", "UserName", new ArrayList<>());
        doNothing().when(userKrmRepository).removeAccessUserKRM("UserId", "UserName");

        userServiceImpl.setRemoveUser(removeUserRequest);

        verify(userRepository, times(1)).getUserInfoQuery(ArgumentMatchers.any(String.class));
        verify(userRepository, times(1)).getUserRoleMemberIds(ArgumentMatchers.any(String.class));
    }

}
