package com.gamingclub.gamingclub;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.bson.types.ObjectId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Document(collection = "members")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    
    @Id
    private String id;
    
    @NotBlank(message = "Name is required")
    private String name;
    
    @NotNull(message = "Balance is required")
    private Double balance;
    
    @NotBlank(message = "Phone is required")
    private String phone;
    
    // Additional fields for gaming club
    private String email;
    private String membershipType;
    private Object joinDate;
    private Boolean isActive;
}
