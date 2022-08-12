package sg.com.cam.util.interceptor;

import org.aspectj.lang.Aspects;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sg.com.cam.util.interceptor.authentication.CamAuthenticationAspect;

@Configuration
public class InterceptorConfig {
    @Bean
    public CamAuthenticationAspect getCamAuthenticationAspectBean() {
        return Aspects.aspectOf(CamAuthenticationAspect.class);
    }
}
