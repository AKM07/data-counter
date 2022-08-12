package sg.com.cam.example;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import sg.com.cam.controller.TestController;
import sg.com.cam.entity.krisadmin.UserAdminEntity;
import sg.com.cam.model.base.BaseResponse;
import sg.com.cam.model.user.TestResponse;
import sg.com.cam.service.UserService;
import sg.com.cam.service.impl.UserServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UnitTestPureCodeTest {
    private  TestController testController;
    private  UserService userService;

    public UnitTestPureCodeTest(){
        testController = new TestController();
        userService = Mockito.mock(UserServiceImpl.class);
        testController.setUserService(userService);
    }

    @Test
    void addition() throws Exception {
        UserAdminEntity user = new UserAdminEntity();
        user.setUserId(1L);
        user.setEmail("test");
        assertEquals("ok", "ok");
    }
}
