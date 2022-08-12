package sg.com.cam.util.interceptor.authentication;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sg.com.cam.helper.AuthenticationHelper;

@Aspect
public class CamAuthenticationAspect {
    private static final Logger logger = LoggerFactory.getLogger(CamAuthenticationAspect.class);

    @Autowired
    private AuthenticationHelper authenticationHelper;

    @Around("@annotation(CamAuthentication)")
    public Object trace(ProceedingJoinPoint joinPoint) throws Throwable {
        authenticationHelper.validateRequest();
        Object result = joinPoint.proceed();
        return result;
    }

}
