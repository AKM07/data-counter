package sg.com.cam.helper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sg.com.cam.entity.krisadmin.ServerSettingEntity;
import sg.com.cam.helper.impl.AuthenticationHelperImpl;
import sg.com.cam.repository.krisadmin.ServerSettingRepository;
import sg.com.cam.util.ConstantVariable;
import sg.com.cam.util.CustomException;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class AuthenticationHelperImplTest {

    @InjectMocks
    private AuthenticationHelperImpl authenticationHelperImpl;
    @Mock
    private ServerSettingRepository serverSettingRepository;
    @Mock
    HttpServletRequest request;

    private AutoCloseable closeable;

    @BeforeEach
    public void init() {
        closeable = MockitoAnnotations.openMocks(this);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }

    @Test
    void validateRequestCheckMandatoryField() throws Exception{
        String dtime = String.valueOf(System.currentTimeMillis());
        Map<String, String[]> map = Map.of("nonce", new String[]{"92834923"}, "ts", new String[]{dtime});
        when(request.getParameterMap()).thenReturn(map);
        try {  authenticationHelperImpl.validateRequest(); }
        catch (CustomException e){ assertTrue(e.getMessage().contains("mandatory")); }

        map = Map.of("accountid",new String[]{"cam"}, "ts", new String[]{dtime});
        when(request.getParameterMap()).thenReturn(map);
        try {  authenticationHelperImpl.validateRequest(); }
        catch (CustomException e){ assertTrue(e.getMessage().contains("mandatory")); }

        map = Map.of("accountid",new String[]{"cam"}, "nonce", new String[]{"92834923"});
        when(request.getParameterMap()).thenReturn(map);
        try {  authenticationHelperImpl.validateRequest(); }
        catch (CustomException e){ assertTrue(e.getMessage().contains("mandatory")); }
    }

    @Test
    void validateRequestCheckAuthorizationMatching() throws Exception{
        String dtime = String.valueOf(System.currentTimeMillis());
        Map<String, String[]> map = Map.of("accountid",new String[]{"cam"}, "nonce", new String[]{"92834923"}, "ts", new String[]{dtime});
        when(request.getParameterMap()).thenReturn(map);
        when(request.getMethod()).thenReturn("get");
        when(request.getScheme()).thenReturn("http");
        when(request.getServerName()).thenReturn("localhost");
        when(request.getServerPort()).thenReturn(8080);
        when(request.getRequestURI()).thenReturn("http://");
        when(request.getRequestURI()).thenReturn("http://");
        when(request.getHeader("Authorization")).thenReturn("header");
        ServerSettingEntity serverSettingEntity =new ServerSettingEntity();
        serverSettingEntity.setSettingCurrentValue("cam");
        when(serverSettingRepository.getServerSettingBySettingKey(ConstantVariable.CAM_ACCOUNT_ID)).thenReturn(serverSettingEntity);
        when(serverSettingRepository.getServerSettingBySettingKey(ConstantVariable.CAM_SECRET_KEY)).thenReturn(serverSettingEntity);
        try {  authenticationHelperImpl.validateRequest(); }
        catch (CustomException e){ assertTrue(e.getMessage().contains("Invalid authorization header")); }
        verify(serverSettingRepository, times(2)).getServerSettingBySettingKey(ArgumentMatchers.any(String.class));
    }

}
