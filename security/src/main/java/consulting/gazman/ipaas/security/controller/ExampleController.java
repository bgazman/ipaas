package consulting.gazman.ipaas.security.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import consulting.gazman.ipaas.security.annotations.RequiresPermission;
import consulting.gazman.ipaas.security.annotations.RequiresTenant;

@RestController
@RequestMapping("/api/v1")
public class ExampleController {
    
    // @RequiresPermission(value = "service1", action = "read", resource = "users")
    // @RequiresTenant
    // @GetMapping("/users")
    // public ResponseEntity<List<User>> getUsers(@RequestHeader("X-Tenant-ID") String tenantId) {
    //     // Implementation
    //     return ResponseEntity.ok(Collections.emptyList());
    // }
}