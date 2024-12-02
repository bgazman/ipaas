package consulting.gazman.ipaas.security.service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;


import org.springframework.stereotype.Service;

import consulting.gazman.ipaas.security.client.KeycloakClient;
import consulting.gazman.ipaas.security.exception.KeycloakException;
import consulting.gazman.ipaas.security.model.IpaasUser;


@Service
public class UserService {
    private final KeycloakClient keycloakClient;

    public UserService(KeycloakClient keycloakClient) {
        this.keycloakClient = keycloakClient;
    }

    public void updateUser(String userId, IpaasUser user) {
        keycloakClient.updateUser(userId, user);
        
        // Optionally verify the update
        if (!keycloakClient.verifyUserUpdate(userId, user)) {
            throw new KeycloakException("User update verification failed");
        }
    }

    public void updateUserAttribute(String key, String value, String userId) {
        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put(key, Collections.singletonList(value));
        keycloakClient.updateUserAttributes(userId, attributes);
    }

    public void updateMultipleAttributes(String userId, Map<String, String> attributes) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateMultipleAttributes'");
    }
}
// @Service
// @Slf4j
// public class UserService {
//     private final KeycloakClient keycloakClient;

//     public UserService(KeycloakClient keycloakClient) {
//         this.keycloakClient = keycloakClient;
//     }

//     public void createUser(IpaasUser user, String initialPassword) {
//         try {
//             // Create user in Keycloak
//             UserRepresentation created = keycloakClient.createUser(user);
            
//             // Set initial password
//             keycloakClient.setUserPassword(created.getId(), initialPassword, true);
            
//             // Assign default role
//             keycloakClient.assignRole(created.getId(), "USER");
            
//             log.info("Successfully created user: {}", user.getUsername());
//         } catch (KeycloakException e) {
//             log.error("Failed to create user", e);
//             throw e;
//         }
//     }
// }