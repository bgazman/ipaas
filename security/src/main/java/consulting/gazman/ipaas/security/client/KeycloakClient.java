package consulting.gazman.ipaas.security.client;


import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import consulting.gazman.ipaas.security.config.KeycloakProperties;
import consulting.gazman.ipaas.security.exception.KeycloakException;
import consulting.gazman.ipaas.security.model.IpaasUser;
import jakarta.ws.rs.NotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KeycloakClient {
    
    private final Keycloak keycloak;
    private final KeycloakProperties properties;

    // public KeycloakClient(KeycloakProperties properties) {
    //     this.properties = properties;
    // }
    public KeycloakClient(KeycloakProperties properties) {
        this.properties = properties;
        this.keycloak = initializeKeycloak();
    }

    public String createUser(String username, String email,
     Map<String, List<String>> attributes) {

        return "";
     }
       
    private Keycloak initializeKeycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(properties.getAuthServerUrl())
                .realm(properties.getRealm())
                .clientId(properties.getClientId())
                .clientSecret(properties.getClientSecret())
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .build();

                
    }
public void updateUserAttribute(String userId, String key, String value) {
    Objects.requireNonNull(userId, "User ID cannot be null");
    Objects.requireNonNull(key, "Attribute key cannot be null");
    Objects.requireNonNull(value, "Attribute value cannot be null");

    try {
        RealmResource realmResource = keycloak.realm(properties.getRealm());
        UserResource userResource = realmResource.users().get(userId);

        if (userResource == null) {
            log.error("User not found: {}", userId);
            throw new KeycloakException("User not found in Keycloak");
        }

        UserRepresentation user = userResource.toRepresentation();
        Map<String, List<String>> attributes = user.getAttributes();
        if (attributes == null) {
            attributes = new HashMap<>();
            user.setAttributes(attributes);
        }

        attributes.put(key, Collections.singletonList(value));
        userResource.update(user);

        log.debug("Successfully updated attribute '{}' for user: {}", key, userId);
    } catch (NotFoundException e) {
        log.error("User not found in Keycloak: {}", userId, e);
        throw new KeycloakException("User not found in Keycloak", e);
    } catch (Exception e) {
        log.error("Error updating attribute '{}' for user: {} in Keycloak", key, userId, e);
        throw new KeycloakException("Failed to update user attribute in Keycloak", e);
    }
}
    // public void updateUserAttribute(String userId, String key, String value) {
    //     try {
    //         UserResource userResource = keycloak.realm(properties.getRealm())
    //                 .users().get(userId);
            
    //         UserRepresentation user = userResource.toRepresentation();
            
    //         Map<String, List<String>> attributes = user.getAttributes();
    //         if (attributes == null) {
    //             attributes = new HashMap<>();
    //         }
            
    //         List<String> valueList = Arrays.asList(value); // Using Arrays.asList instead of Collections.singletonList
    //         attributes.put(key, valueList);
            
    //         user.setAttributes(attributes);
    //         userResource.update(user);
            
    //         log.debug("Successfully updated attribute {} for user: {}", key, userId);
    //     } catch (Exception e) {
    //         log.error("Error updating user attribute in Keycloak", e);
    //         throw new KeycloakException("Failed to update user attribute in Keycloak", e);
    //     }
    // }

    public void assignRole(String userId, String roleName) {
        try {
            RoleRepresentation role = keycloak.realm(properties.getRealm())
                    .roles().get(roleName).toRepresentation();

            keycloak.realm(properties.getRealm())
                    .users().get(userId)
                    .roles().realmLevel()
                    .add(Arrays.asList(role)); // Using Arrays.asList instead of Collections.singletonList
        } catch (Exception e) {
            log.error("Error assigning role in Keycloak", e);
            throw new KeycloakException("Failed to assign role in Keycloak", e);
        }
    }

    public void updateUserAttributes(String userId, Map<String, List<String>> attributes) {
        try {
            UserResource userResource = keycloak.realm(properties.getRealm())
                    .users().get(userId);
            
            UserRepresentation user = userResource.toRepresentation();
            
            Map<String, List<String>> existingAttributes = user.getAttributes();
            if (existingAttributes == null) {
                existingAttributes = new HashMap<>();
            }
            existingAttributes.putAll(attributes);
            
            user.setAttributes(existingAttributes);
            userResource.update(user);
            
            log.debug("Successfully updated attributes for user: {}", userId);
        } catch (Exception e) {
            log.error("Error updating user attributes in Keycloak", e);
            throw new KeycloakException("Failed to update user attributes in Keycloak", e);
        }
    }

    public void updateUser(String userId, IpaasUser user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUser'");
    }

    public boolean verifyUserUpdate(String userId, IpaasUser user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'verifyUserUpdate'");
    }

    public IpaasUser createUser(IpaasUser testUser) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createUser'");
    }
    
}

