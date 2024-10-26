package consulting.gazman.ipaas.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "keycloak")
@Configuration
@Getter
@Setter
public class KeycloakProperties {
    private String authServerUrl;
    private String realm;
    private String clientId;
    private String clientSecret;
    private String adminUsername;
    private String adminPassword;
    private boolean publicClient;
    private boolean useResourceRoleMappings;
    public void setResource(String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setResource'");
    }
    public String getResource() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getResource'");
    }
}



