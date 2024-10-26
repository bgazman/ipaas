package consulting.gazman.ipaas.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;




@SpringBootTest
public class ApiGatewayIntegrationTest {

    @Test
    void contextLoads() {
        // This test will fail if the application context cannot start
        assertThat(true).isTrue();
    }
}