package consulting.gazman.ipaas.security;

import org.junit.jupiter.api.Test;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.junit.jupiter.api.Test;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;
@ActiveProfiles("test")  // Add this annotation
@EnableAutoConfiguration(exclude = {
    DataSourceAutoConfiguration.class,
    HibernateJpaAutoConfiguration.class
})
@SpringBootTest
class KeycloakClientIntegrationTest {

    private static final String SERVER_URL = "http://localhost:9090";
    private static final String REALM = "master";
    private static final String CLIENT_ID = "admin-cli";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";

    @Test
    void shouldConnectToKeycloak() {
        Keycloak keycloak = KeycloakBuilder.builder()
            .serverUrl("http://localhost:9090")
            .realm("master")
            .clientId("admin-cli")               
            .username("ipaas_keycloak_admin")    
            .password("ipaas_keycloak_pwd")
            .grantType(OAuth2Constants.PASSWORD)  
            .build();
    
        var serverInfo = keycloak.serverInfo().getInfo();
        assertNotNull(serverInfo);
        System.out.println("Successfully connected to Keycloak!");
    }
}