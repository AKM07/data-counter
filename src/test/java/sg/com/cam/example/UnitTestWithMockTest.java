package sg.com.cam.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sg.com.cam.controller.TestController;
import sg.com.cam.entity.krisadmin.UserAdminEntity;
import sg.com.cam.model.user.TestResponse;
import sg.com.cam.service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UnitTestWithMockTest {
    @InjectMocks
    private TestController testController;

    @Mock
    private UserService userService;

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
    void index() throws Exception {
        UserAdminEntity user = new UserAdminEntity();
        user.setUserId(1L);
        user.setEmail("test");

        assertEquals("go", "go");
    }
}
