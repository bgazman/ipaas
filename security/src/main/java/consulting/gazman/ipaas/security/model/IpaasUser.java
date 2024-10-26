package consulting.gazman.ipaas.security.model;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.List;


import org.springframework.security.core.GrantedAuthority;

public interface IpaasUser {
    String getId();
    String getUsername();
    String getEmail();
    Set<String> getRoles();
    Set<String> getPermissions();
    Map<String, Object> getAttributes();

    Collection<? extends GrantedAuthority> getAuthorities();
    Set<String> getTenants();

    boolean hasPermission(String permission);
    void setId(String id);
    void setUsername(String username);
    void setEmail(String email);
    void setTenantId(String tenantId);
    void setRoles(Set<String> roles);
    void setPermissions(Set<String> permissions);
    String getTenantId();
}

