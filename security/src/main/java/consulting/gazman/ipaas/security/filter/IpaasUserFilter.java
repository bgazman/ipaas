package consulting.gazman.ipaas.security.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import consulting.gazman.ipaas.security.model.IpaasUser;
import consulting.gazman.ipaas.security.service.IpaasUserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class IpaasUserFilter extends OncePerRequestFilter {

    private final IpaasUserService ipaasUserService;

    public IpaasUserFilter(IpaasUserService ipaasUserService) {
        this.ipaasUserService = ipaasUserService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                  HttpServletResponse response, 
                                  FilterChain filterChain) throws ServletException, IOException {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            OAuth2User oauth2User = oauthToken.getPrincipal();
            
            IpaasUser ipaasUser = ipaasUserService.createIpaasUser(oauth2User);
            
            // Create a new DefaultOAuth2User that wraps our IpaasUser
            DefaultOAuth2User newOAuth2User = new DefaultOAuth2User(
                authentication.getAuthorities(),
                createAttributes(ipaasUser),
                "preferred_username"
            );
            
            // Create new OAuth2AuthenticationToken with the wrapped user
            OAuth2AuthenticationToken newAuth = new OAuth2AuthenticationToken(
                newOAuth2User,
                authentication.getAuthorities(),
                oauthToken.getAuthorizedClientRegistrationId()
            );
            
            SecurityContextHolder.getContext().setAuthentication(newAuth);
        }
        
        filterChain.doFilter(request, response);
    }

    private Map<String, Object> createAttributes(IpaasUser ipaasUser) {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("sub", ipaasUser.getId());
        attributes.put("preferred_username", ipaasUser.getUsername());
        attributes.put("email", ipaasUser.getEmail());
        attributes.put("roles", ipaasUser.getRoles());
        attributes.put("permissions", ipaasUser.getPermissions());
        attributes.put("tenants", ipaasUser.getTenants());
        
        // Add any custom attributes
        // if (ipaasUser.getAttributes() != null) {
        //     attributes.putAll(convertAttributes(ipaasUser.getAttributes()));
        // }
        
        return attributes;
    }

    private Map<String, Object> convertAttributes(Map<String, List<String>> attributes) {
        return attributes.entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> entry.getValue().size() == 1 ? entry.getValue().get(0) : entry.getValue()
            ));
    }
}

// @Component
// public class IpaasUserFilter extends OncePerRequestFilter {

//     private final IpaasUserService ipaasUserService;

//     public IpaasUserFilter(IpaasUserService ipaasUserService) {
//         this.ipaasUserService = ipaasUserService;
//     }

//     @Override
//     protected void doFilterInternal(HttpServletRequest request,
//             HttpServletResponse response,
//             FilterChain filterChain) throws ServletException, IOException {

//         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

//         if (authentication != null && authentication.getPrincipal() instanceof OAuth2User) {
//             OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
//             IpaasUser ipaasUser = ipaasUserService.createIpaasUser(oauth2User);

//             Authentication newAuth = new OAuth2AuthenticationToken(
//                     ipaasUser,
//                     authentication.getAuthorities(),
//                     ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId());

//             SecurityContextHolder.getContext().setAuthentication(newAuth);
//         }

//         filterChain.doFilter(request, response);
//     }
// }
