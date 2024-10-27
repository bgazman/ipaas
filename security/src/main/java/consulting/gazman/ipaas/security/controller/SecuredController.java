package consulting.gazman.ipaas.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import consulting.gazman.ipaas.security.model.IpaasUser;
import consulting.gazman.ipaas.security.service.IpaasSecurityService;

@RestController
@RequestMapping("/api")
public class SecuredController {
    
    private final IpaasSecurityService ipaasSecurityService;
    
    public SecuredController(IpaasSecurityService ipaasSecurityService) {
        this.ipaasSecurityService = ipaasSecurityService;
    }
    
    @GetMapping("/user")
    public ResponseEntity<IpaasUser> getCurrentUser() {
        IpaasUser user = ipaasSecurityService.getCurrentUser();
        return ResponseEntity.ok(user);
    }
    
    @GetMapping("/secured")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> securedEndpoint() {
        IpaasUser user = ipaasSecurityService.getCurrentUser();
        return ResponseEntity.ok("Hello " + user.getUsername());
    }
}