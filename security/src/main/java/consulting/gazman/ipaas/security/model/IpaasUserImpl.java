package consulting.gazman.ipaas.security.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IpaasUserImpl implements IpaasUser {
    private String id;
    private String username;
    private String email;
    private Set<String> roles;
    private Set<String> permissions;
    private Map<String,Object> attributes;
    private Set<String> tenants;
    private String tenantId;

    // Other fields, constructors, and methods...



    public IpaasUserImpl(String id, String username,
     String email, Set<String> roles, Set<String> permissions,
    Map<String,Object> attributes, Set<String> tenants, String tenantId) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.permissions = permissions;
        this.attributes = attributes;
        this.tenants = tenants;
    }



    public IpaasUserImpl() {
        //TODO Auto-generated constructor stub
    }



    public IpaasUser createIpaasUser(OAuth2User oauth2User) {
        return IpaasUserImpl.builder()
            .id(oauth2User.getAttribute("sub"))
            .username(oauth2User.getAttribute("preferred_username"))
            .email(oauth2User.getAttribute("email"))
            .roles(extractRoles(oauth2User))
            .permissions(extractPermissions(oauth2User))
            .tenants(extractTenants(oauth2User))
            .attributes(new HashMap<>(oauth2User.getAttributes()))
            .build();
    }
    

    private static Map<String, Object> convertAttributes(Map<String, Object> attributes) {
        // Actual conversion logic
        return attributes; // Replace with actual conversion logic
    }

    private static Set<String> extractRoles(OAuth2User user) {
        // Extract roles from Keycloak token
        return Optional.ofNullable(user.<Map<String, Object>>getAttribute("realm_access"))
                .map(realmAccess -> (List<String>) realmAccess.get("roles"))
                .orElse(Collections.emptyList())
                .stream()
                .collect(Collectors.toSet());
    }

    private static Set<String> extractPermissions(OAuth2User user) {
        // Extract permissions from Keycloak token resource_access
        return Optional.ofNullable(user.<Map<String, Object>>getAttribute("resource_access"))
                .map(resourceAccess -> (Map<String, Object>) resourceAccess.get("your-client-id"))
                .map(clientAccess -> (List<String>) clientAccess.get("roles"))
                .orElse(Collections.emptyList())
                .stream()
                .collect(Collectors.toSet());
    }

    private static Set<String> extractTenants(OAuth2User user) {
        // Actual extraction logic for tenants
        return Collections.emptySet(); // Replace with actual extraction logic
    }

    private static Set<String> extractAttributes(OAuth2User user) {
        // Actual extraction logic for tenants
        return Collections.emptySet(); // Replace with actual extraction logic
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role)));
        permissions.forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission)));
        return authorities;
    }

    @Override
    public boolean hasPermission(String permission) {
        return permissions.contains(permission);
    }
    @Override
    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
    @Override
    public Set<String> getTenants() {
        return tenants;
    }

    @Override
    public Map<String, Object> getAttributes() {
        // TODO Auto-generated method stub
        return attributes;
    }

    @Override
    public String getEmail() {
        // TODO Auto-generated method stub
        return email;
    }

    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return id;
    }

    @Override
    public Set<String> getPermissions() {
        // TODO Auto-generated method stub
        return permissions;
    }

    @Override
    public Set<String> getRoles() {
        // TODO Auto-generated method stub
        return roles;
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
