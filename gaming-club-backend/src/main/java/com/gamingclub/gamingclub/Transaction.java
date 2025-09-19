package com.gamingclub.gamingclub;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.bson.types.ObjectId;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Document(collection = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    
    @Id
    private ObjectId id;
    
    @NotNull(message = "Member ID is required")
    private ObjectId memberId;
    
    @NotNull(message = "Game ID is required")
    private ObjectId gameId;
    
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private Double amount;
    
    private LocalDateTime dateTime;
    
    // Additional fields for gaming club
    private String transactionType = "GAME_PLAY";
    private String status = "COMPLETED";
    private String notes;
    
    // Reference to member and game (not stored in DB)
    private Member member;
    private Game game;
}
