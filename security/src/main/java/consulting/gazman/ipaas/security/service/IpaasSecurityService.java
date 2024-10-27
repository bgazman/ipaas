package consulting.gazman.ipaas.security.service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import consulting.gazman.ipaas.security.model.IpaasUser;
import consulting.gazman.ipaas.security.model.IpaasUserImpl;
import lombok.extern.slf4j.Slf4j;



@Service
@Slf4j
public class IpaasSecurityService {

    public IpaasUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication instanceof OAuth2AuthenticationToken) {
            OAuth2User oauth2User = ((OAuth2AuthenticationToken) authentication).getPrincipal();
            Map<String, Object> attributes = oauth2User.getAttributes();
            
            return IpaasUserImpl.builder()
                .id((String) attributes.get("sub"))
                .username((String) attributes.get("preferred_username"))
                .email((String) attributes.get("email"))
                .roles(getAttributeAsSet(attributes, "roles"))
                .permissions(getAttributeAsSet(attributes, "permissions"))
                .tenants(getAttributeAsSet(attributes, "tenants"))
                .attributes(attributes)
                .build();
        }
        
        throw new AccessDeniedException("No authenticated user found");
    }

    private Set<String> getAttributeAsSet(Map<String, Object> attributes, String key) {
        Object value = attributes.get(key);
        if (value instanceof Collection) {
            return ((Collection<?>) value).stream()
                .map(Object::toString)
                .collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }

    public Map<String, Object> extractUserInfo(Jwt jwt) {
        Map<String, Object> userInfo = new HashMap<>();
        
        // Basic user information
        userInfo.put("sub", jwt.getSubject());
        userInfo.put("username", jwt.getClaimAsString("preferred_username"));
        userInfo.put("email", jwt.getClaimAsString("email"));
        userInfo.put("name", jwt.getClaimAsString("name"));
        userInfo.put("given_name", jwt.getClaimAsString("given_name"));
        userInfo.put("family_name", jwt.getClaimAsString("family_name"));

        // Extract roles from realm_access claim
        Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");
        if (realmAccess != null && realmAccess.containsKey("roles")) {
            @SuppressWarnings("unchecked")
            List<String> roles = (List<String>) realmAccess.get("roles");
            userInfo.put("roles", roles);
        }

        // Resource access (client-specific roles)
        Map<String, Object> resourceAccess = jwt.getClaimAsMap("resource_access");
        if (resourceAccess != null) {
            userInfo.put("resource_access", resourceAccess);
        }

        // Scope information
        String scope = jwt.getClaimAsString("scope");
        if (scope != null) {
            List<String> scopes = List.of(scope.split(" "));
            userInfo.put("scopes", scopes);
        }

        // Token metadata
        userInfo.put("issued_at", jwt.getIssuedAt());
        userInfo.put("expiration", jwt.getExpiresAt());
        userInfo.put("issuer", jwt.getIssuer());

        return userInfo;
    }


    public boolean hasPermission(String permission) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hasPermission'");
    }

    public boolean isInTenant(String tenantId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isInTenant'");
    }
    
    public boolean isSameTenant(String tenantId) {
        IpaasUser currentUser = getCurrentUser();
        return currentUser != null && 
               currentUser.getTenantId() != null && 
               currentUser.getTenantId().equals(tenantId);
    }
    public List<SimpleGrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAuthorities'");
    }
}