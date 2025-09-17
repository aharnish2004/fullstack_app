package com.gamingclub.gamingclub;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Document(collection = "collections")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Collection {
    
    @Id
    private String id;
    
    @NotNull(message = "Date is required")
    private LocalDateTime date;
    
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private Double amount;
    
    // Additional fields for gaming club
    private String collectionType = "DAILY";
    private String description;
    private String collectedBy;
    private String status = "COMPLETED";
}
