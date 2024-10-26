package consulting.gazman.ipaas.common.logging;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest(classes = LoggingDemoTest.TestConfig.class)
class LoggingDemoTest {

    @SpringBootApplication
    static class TestConfig {
    }

    @Test
    void demonstrateLogging() {
        // Set MDC values
        LoggingContext.put("correlationId", "123-abc-456");
        LoggingContext.put("requestId", "req-789");
        LoggingContext.put("userId", "john.doe");
        LoggingContext.put("source", "logging-test");

        // Log different levels
        log.debug("This is a debug message");
        log.info("Processing something important");
        log.warn("Something unexpected happened");
        
        try {
            throw new RuntimeException("Simulated error");
        } catch (Exception e) {
            log.error("An error occurred", e);
        }

        // Clear context
        LoggingContext.clear();
        log.info("This message should have no MDC values");
    }
}