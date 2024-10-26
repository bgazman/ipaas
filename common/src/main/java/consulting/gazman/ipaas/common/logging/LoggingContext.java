package consulting.gazman.ipaas.common.logging;

import lombok.experimental.UtilityClass;
import org.slf4j.MDC;
import java.util.Map;
import java.util.UUID;

@UtilityClass
public class LoggingContext {
    
    public static final String CORRELATION_ID = "correlationId";
    public static final String REQUEST_ID = "requestId";
    
    public static void put(String key, String value) {
        if (value != null) {
            MDC.put(key, value);
        }
    }

    public static String get(String key) {
        return MDC.get(key);
    }

    public static void remove(String key) {
        MDC.remove(key);
    }

    public static void clear() {
        MDC.clear();
    }

    public static void initializeRequest() {
        put(REQUEST_ID, UUID.randomUUID().toString());
        if (get(CORRELATION_ID) == null) {
            put(CORRELATION_ID, UUID.randomUUID().toString());
        }
    }

    public static Map<String, String> getCopyOfContextMap() {
        return MDC.getCopyOfContextMap();
    }

    public static void setContextMap(Map<String, String> contextMap) {
        if (contextMap != null) {
            MDC.setContextMap(contextMap);
        }
    }
}