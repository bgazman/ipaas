package consulting.gazman.ipaas.security.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import consulting.gazman.ipaas.security.model.IpaasUser;
import consulting.gazman.ipaas.security.model.IpaasUserImpl;

@Service
public class IpaasUserService {
    
    public IpaasUser createIpaasUser(OAuth2User oauth2User) {
        return IpaasUserImpl.builder()
            .id(oauth2User.getAttribute("sub"))
            .username(oauth2User.getAttribute("preferred_username"))
            .email(oauth2User.getAttribute("email"))
            .roles(extractRoles(oauth2User))
            .permissions(extractPermissions(oauth2User))
            .tenants(extractTenants(oauth2User))
            .attributes(new HashMap<String,Object>(oauth2User.getAttributes()))
            .build();
    }

    private Set<String> extractRoles(OAuth2User user) {
        return Optional.ofNullable(user.<Map<String, Object>>getAttribute("realm_access"))
            .map(realmAccess -> (List<String>) realmAccess.get("roles"))
            .orElse(Collections.emptyList())
            .stream()
            .collect(Collectors.toSet());
    }

    private Set<String> extractPermissions(OAuth2User user) {
        return Optional.ofNullable(user.<Map<String, Object>>getAttribute("resource_access"))
            .map(resourceAccess -> (Map<String, Object>) resourceAccess.get("your-client-id"))
            .map(clientAccess -> (List<String>) clientAccess.get("roles"))
            .orElse(Collections.emptyList())
            .stream()
            .collect(Collectors.toSet());
    }

    private Set<String> extractTenants(OAuth2User user) {
        return Optional.ofNullable(user.<List<String>>getAttribute("tenants"))
            .orElse(Collections.emptyList())
            .stream()
            .collect(Collectors.toSet());
    }
}
