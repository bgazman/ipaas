package consulting.gazman.ipaas.api.controller;

import java.util.Map;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import consulting.gazman.ipaas.security.service.SecurityService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ApiController {

    private final SecurityService securityService = new SecurityService();

    @GetMapping("/user-info")
    public ResponseEntity<Map<String, Object>> getUserInfo(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(securityService.extractUserInfo(jwt));
    }
}