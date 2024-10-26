package consulting.gazman.ipaas.security.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import consulting.gazman.ipaas.security.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private final UserService userService;
    

    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @PutMapping("/{userId}/attributes/{key}")
    public ResponseEntity<Void> updateAttribute(
            @PathVariable String userId,
            @PathVariable String key,
            @RequestBody String value) {
        userService.updateUserAttribute(userId, key, value);
        return ResponseEntity.ok().build();
    }
    
    @PutMapping("/{userId}/attributes")
    public ResponseEntity<Void> updateAttributes(
            @PathVariable String userId,
            @RequestBody Map<String, String> attributes) {
        userService.updateMultipleAttributes(userId, attributes);
        return ResponseEntity.ok().build();
    }
}