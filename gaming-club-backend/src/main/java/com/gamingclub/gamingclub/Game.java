package com.gamingclub.gamingclub;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Document(collection = "games")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Game {
    
    @Id
    private String id;
    
    @NotBlank(message = "Name is required")
    private String name;
    
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private Double price;
    
    private String description;
    
    // Additional fields for gaming club
    private String category;
    private String difficulty;
    private Integer maxPlayers;
    private Boolean isActive = true;
    private String imageUrl;
}
