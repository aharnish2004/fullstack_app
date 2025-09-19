package com.gamingclub.gamingclub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminUserController {
    
    @Autowired
    private AdminUserRepository adminUserRepository;
    
    @GetMapping
    public ResponseEntity<List<AdminUser>> getAllAdminUsers() {
        List<AdminUser> adminUsers = adminUserRepository.findAll();
        return ResponseEntity.ok(adminUsers);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AdminUser> getAdminUserById(@PathVariable String id) {
        Optional<AdminUser> adminUser = adminUserRepository.findById(new ObjectId(id));
        return adminUser.map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<AdminUser> createAdminUser(@Valid @RequestBody AdminUser adminUser) {
        adminUser.setId(null);
        AdminUser savedAdminUser = adminUserRepository.save(adminUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAdminUser);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<AdminUser> updateAdminUser(@PathVariable String id, @Valid @RequestBody AdminUser adminUser) {
        ObjectId oid = new ObjectId(id);
        if (!adminUserRepository.existsById(oid)) {
            return ResponseEntity.notFound().build();
        }
        adminUser.setId(oid);
        AdminUser updatedAdminUser = adminUserRepository.save(adminUser);
        return ResponseEntity.ok(updatedAdminUser);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdminUser(@PathVariable String id) {
        ObjectId oid = new ObjectId(id);
        if (!adminUserRepository.existsById(oid)) {
            return ResponseEntity.notFound().build();
        }
        adminUserRepository.deleteById(oid);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/username/{username}")
    public ResponseEntity<AdminUser> getAdminUserByUsername(@PathVariable String username) {
        Optional<AdminUser> adminUser = adminUserRepository.findByUsername(username);
        return adminUser.map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/role/{role}")
    public ResponseEntity<List<AdminUser>> getAdminUsersByRole(@PathVariable String role) {
        List<AdminUser> adminUsers = adminUserRepository.findByRole(role);
        return ResponseEntity.ok(adminUsers);
    }
    
    @PostMapping("/login")
    public ResponseEntity<AdminUser> login(@RequestBody LoginRequest loginRequest) {
        Optional<AdminUser> adminUser = adminUserRepository.findByUsernameAndPassword(
            loginRequest.getUsername(), 
            loginRequest.getPassword()
        );
        
        if (adminUser.isPresent() && adminUser.get().getIsActive()) {
            return ResponseEntity.ok(adminUser.get());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    
    // Inner class for login request
    public static class LoginRequest {
        private String username;
        private String password;
        
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}
