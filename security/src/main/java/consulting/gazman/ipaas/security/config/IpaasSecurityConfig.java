package consulting.gazman.ipaas.security.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;

import consulting.gazman.ipaas.security.service.IpaasUserService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class IpaasSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(userInfo -> userInfo
                    .userService(this.oauth2UserService())
                )
            )
            .csrf(csrf -> csrf.disable())
            .build();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        
        return userRequest -> {
            OAuth2User oauth2User = delegate.loadUser(userRequest);
            
            Set<SimpleGrantedAuthority> authorities = new HashSet<>();
            oauth2User.getAuthorities().forEach(authority -> 
                authorities.add(new SimpleGrantedAuthority(authority.getAuthority()))
            );
            
            return new DefaultOAuth2User(
                authorities,
                oauth2User.getAttributes(),
                "preferred_username"
            );
        };
    }

    @Bean
    public IpaasUserService ipaasUserService() {
        return new IpaasUserService();
    }
}
