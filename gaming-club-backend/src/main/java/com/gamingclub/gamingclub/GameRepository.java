package com.gamingclub.gamingclub;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.bson.types.ObjectId;
import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends MongoRepository<Game, ObjectId> {
    
    List<Game> findByNameContainingIgnoreCase(String name);
    
    List<Game> findByCategory(String category);
    
    List<Game> findByIsActive(Boolean isActive);
    
    List<Game> findByPriceBetween(Double minPrice, Double maxPrice);
    
    List<Game> findByDifficulty(String difficulty);
}
