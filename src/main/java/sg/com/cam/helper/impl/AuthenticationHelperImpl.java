package sg.com.cam.helper.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sg.com.cam.helper.AuthenticationHelper;
import sg.com.cam.util.CustomException;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service("authenticationHelper")
public class AuthenticationHelperImpl implements AuthenticationHelper {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationHelperImpl.class);

    private final String AUTHORIZATION_HEADER = "Authorization";
//    private ServerSettingRepository serverSettingRepository;
//
//    /*start of setter injection to ease unit testing*/
//    @Autowired
//    public void setServerSettingRepository(@Qualifier("serverSettingRepository") ServerSettingRepository serverSettingRepository) {
//        this.serverSettingRepository = serverSettingRepository;
//    }

//    private void checkAccountIdAndHeader(HttpServletRequest request, Map<String, String> requestHeaders, Map<String, String> requestParams) throws Exception {
//            String accountIdRequest = requestParams.get("accountid");
//            ServerSettingEntity accountServerSetting = serverSettingRepository.getServerSettingBySettingKey(ConstantVariable.CAM_ACCOUNT_ID);
//            ServerSettingEntity secretServerSetting = serverSettingRepository.getServerSettingBySettingKey(ConstantVariable.CAM_SECRET_KEY);
//            String accountId = accountServerSetting.getSettingCurrentValue() != null ? accountServerSetting.getSettingCurrentValue() : accountServerSetting.getSettingDefaultValue();
//            logger.info("accountId : {} vs accountIdRequest : {} ", accountId, accountIdRequest);
//            if (!accountIdRequest.equals(accountId)) {
//                 new CustomException("401", "Unauthorised Request, Invalid account id");
//            }
//
//            String httpMethod = request.getMethod().toUpperCase();
//            String protocol = request.getScheme().toLowerCase();
//            String hostName = request.getServerName().toLowerCase();
//            String hostPort = request.getServerPort() + "";
//            hostName = hostPort.equals("80") ? hostName : String.format("%s:%s", hostName, hostPort);
//            String requestURI = request.getRequestURI().toLowerCase();
//            String parameters = constructAndSortParam(requestParams);
//            logger.info("requestURI : {}", requestURI);
//            String combinedString = String.format("%s&%s://%s%s?%s", httpMethod, protocol, hostName, requestURI, parameters);
//
//            //comparing auth with generated one
//            String secretKey = secretServerSetting.getSettingCurrentValue() != null ? secretServerSetting.getSettingCurrentValue():secretServerSetting.getSettingDefaultValue();
//            SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HMACSHA256");
//            Mac mac = Mac.getInstance("HMACSHA256");
//            mac.init(signingKey);
//            byte[] rawHmac = mac.doFinal(combinedString.getBytes());
//            String authString = Base64.getEncoder().encodeToString(rawHmac);
//
//            String authHeader = requestHeaders.get(AUTHORIZATION_HEADER);
//
//            logger.info("authString : {}", authString);
//            logger.info("authHeader : {}", authHeader);
//            if (!authHeader.equals(authString)) {
//                throw new CustomException("401", "Unauthorised Request, Invalid authorization header");
//            }
//
//    }

    private String constructAndSortParam(Map<String, String> requestParams) {
        return "accountid=" + requestParams.get("accountid") + "&nonce=" + requestParams.get("nonce") + "&ts=" + requestParams.get("ts");
    }

    private void checkNonce(Map<String, String> requestParams) throws Exception{
        String nonceRequest = requestParams.getOrDefault("nonce", "");
        if (!nonceRequest.equals("")) {
            int nonce = Integer.parseInt(nonceRequest);
            if (nonce < 0) {
               throw  new CustomException("401", "Unauthorised Request, Invalid nonce");
            }
        }
    }

    private void checkTimestamp(Map<String, String> requestParams) throws Exception{
            String timestampRequest = requestParams.getOrDefault("ts", "");
            Date currentDate = new Date();
            //get current to date to determine grace period
            if (!timestampRequest.equals("")) {
                long reqdtl = Long.parseLong(timestampRequest);
                long currentMillisecond = currentDate.getTime();
                long compare = currentMillisecond - reqdtl;
                long calculate = TimeUnit.MILLISECONDS.toMinutes(compare); //calculate if within 3 minutes
                logger.info("currentMillisecond " + currentMillisecond);
                logger.info("reqdtl " + reqdtl);
                logger.info("calculate " + calculate);
                if (calculate > 3) {
                    throw new CustomException("401", "Unauthorised Request, Invalid request datetime");
                }
            }
    }

    public void validateRequest() throws Exception{
//        try {
//            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
//                    .getRequestAttributes()).getRequest();
//            Map<String, String> requestParams = convertRequestMap(request.getParameterMap());
//            Map<String, String> headerParams = new HashMap<>();
//            headerParams.put(AUTHORIZATION_HEADER, request.getHeader(AUTHORIZATION_HEADER));
//            logger.info("authorization header : {}", headerParams.get(AUTHORIZATION_HEADER));
//            checkNonce(requestParams);
//            checkTimestamp(requestParams);
//            checkAccountIdAndHeader(request, headerParams, requestParams);
//        }catch (Exception ex){
//            logger.error(ex.getMessage(), ex);
//            throw new CustomException(ConstantVariable.AUTHENTICATION_ERROR_CODE, ex.getMessage());
//        }
    }

    private Map<String, String> convertRequestMap(Map<String, String[]> rawValue) throws Exception {
        Map<String, String> requestParams = new HashMap<>();
        if (rawValue.get("accountid") == null) { throw new CustomException("accountid param is mandatory");}
        if (rawValue.get("nonce") == null) { throw new CustomException("nonce param is mandatory");}
        if (rawValue.get("ts") == null) { throw new CustomException("ts param is mandatory");}
        requestParams.put("accountid", rawValue.get("accountid")[0]);
        requestParams.put("nonce", rawValue.get("nonce")[0]);
        requestParams.put("ts", rawValue.get("ts")[0]);
        return requestParams;
    }

}
