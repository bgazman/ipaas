package consulting.gazman.ipaas.security;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.restassured.RestAssured;
import static org.awaitility.Awaitility.await;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class TestContainerBase {
    
    protected static final Logger log = LoggerFactory.getLogger(TestContainerBase.class);
    
    @BeforeAll
    static void waitForInfrastructure() {
        log.info("Waiting for infrastructure services...");
        
        await()
            .atMost(60, TimeUnit.SECONDS)
            .pollInterval(1, TimeUnit.SECONDS)
            .ignoreExceptions()
            .until(() -> {
                // Check Keycloak
                try {
                    int status = RestAssured.get("http://localhost:9090/health").getStatusCode();
                    log.info("Keycloak health check status: {}", status);
                    return status == 200;
                } catch (Exception e) {
                    log.warn("Keycloak not ready yet: {}", e.getMessage());
                    return false;
                }
            });
            
        log.info("Infrastructure services are ready!");
    }
}