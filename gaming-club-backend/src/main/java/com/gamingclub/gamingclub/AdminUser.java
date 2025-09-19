package com.gamingclub.gamingclub;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.bson.types.ObjectId;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Document(collection = "admin_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUser {
    
    @Id
    private ObjectId id;
    
    @NotBlank(message = "Username is required")
    private String username;
    
    @NotBlank(message = "Password is required")
    private String password;
    
    // Additional fields for gaming club
    private String role = "ADMIN";
    private String email;
    private Boolean isActive = true;
    private String lastLogin;
    private String createdBy;
}
