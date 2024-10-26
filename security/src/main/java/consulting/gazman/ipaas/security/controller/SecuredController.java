package consulting.gazman.ipaas.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import consulting.gazman.ipaas.security.model.IpaasUser;
import consulting.gazman.ipaas.security.service.SecurityService;

@RestController
@RequestMapping("/api")
public class SecuredController {
    
    private final SecurityService securityService;
    
    public SecuredController(SecurityService securityService) {
        this.securityService = securityService;
    }
    
    @GetMapping("/user")
    public ResponseEntity<IpaasUser> getCurrentUser() {
        IpaasUser user = securityService.getCurrentUser();
        return ResponseEntity.ok(user);
    }
    
    @GetMapping("/secured")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> securedEndpoint() {
        IpaasUser user = securityService.getCurrentUser();
        return ResponseEntity.ok("Hello " + user.getUsername());
    }
}