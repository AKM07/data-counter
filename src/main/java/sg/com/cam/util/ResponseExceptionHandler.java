package sg.com.cam.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sg.com.cam.model.base.ErrorResponse;

import java.util.ArrayList;

@ControllerAdvice
public class ResponseExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(ResponseExceptionHandler.class);

    @ExceptionHandler(CustomException.class)
    private ResponseEntity<?> errException(CustomException cusEx) {
        logger.info("Custom Exception");
        logger.error(cusEx.getMessage(), cusEx);
        ErrorResponse errRes = new ErrorResponse();
        errRes.setSchemas(new ArrayList<String>(){{add(ConstantVariable.SCHEMA_USER_ERROR);}});
        errRes.setDetail(cusEx.getMessage());
        if (ConstantVariable.AUTHENTICATION_ERROR_CODE.equals(cusEx.getCode())){
            errRes.setStatus("401");
            return new ResponseEntity<Object>(errRes, HttpStatus.UNAUTHORIZED);
        } else if (ConstantVariable.NOT_FOUND_ERROR_CODE.equals(cusEx.getCode())) {
            errRes.setStatus("404");
            return new ResponseEntity<Object>(errRes, HttpStatus.NOT_FOUND);
        }
        errRes.setStatus("500");
        return new ResponseEntity<Object>(errRes, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<?> errException(Exception ex) {
        logger.error(ex.getMessage(), ex);
        ErrorResponse errRes = new ErrorResponse();
        errRes.setSchemas(new ArrayList<String>(){{add("urn:ietf:params:scim:api:messages:2.0:Error");}});
        errRes.setDetail(ex.getMessage());
        errRes.setStatus("500");
        return new ResponseEntity<Object>(errRes, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
