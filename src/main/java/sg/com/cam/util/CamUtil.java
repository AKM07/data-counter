package sg.com.cam.util;

import java.sql.Timestamp;

public class CamUtil {
    public static boolean checkNull(Object obj) {
        if (null == obj || "".equals(obj)) {
            return false;
        }
        return true;
    }

    public static Timestamp getCurrentTime() {
        Timestamp time = new Timestamp(System.currentTimeMillis());
        return time;
    }

//    public static AuditLogEntity newLogEntity(String strDesc, Integer sourceType,
//                                              Long sourceId, Integer operation, String user) {
//        AuditLogEntity log = new AuditLogEntity();
//        log.setSourceId(sourceId);
//        log.setSourceType(sourceType);
//        log.setOperation(operation);
//        strDesc = strDesc.length()>3997?strDesc.substring(0,3997).concat("..."):strDesc;//xiangyi #7254
//        log.setDescription(strDesc);
//        log.setPerformedBy(user);
//        log.setPerformedOn(CamUtil.getCurrentTime());
//        log.setModule("KRISADMIN");
//        return log;
//    }
//
//    public static EventLogEntity newEventLogEntity(Integer eventId, Long sysId,
//                                                   String oldValue, String newValue, Integer processId) {
//        EventLogEntity log = new EventLogEntity();
//
//        log.setEventId(eventId);
//        log.setSysId(sysId);
//        log.setOldValue(oldValue);
//        log.setNewValue(newValue);
//        log.setProcessed(processId);
//        log.setPerformedOn(CamUtil.getCurrentTime());
//        return log;
//    }



}
