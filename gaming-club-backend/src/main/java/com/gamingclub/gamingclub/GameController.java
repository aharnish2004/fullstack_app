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
@RequestMapping("/api/games")
@CrossOrigin(origins = "*")
public class GameController {
    
    @Autowired
    private GameRepository gameRepository;
    
    @GetMapping
    public ResponseEntity<List<Game>> getAllGames() {
        try {
            List<Game> games = gameRepository.findAll();
            return ResponseEntity.ok(games);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(List.of());
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Game> getGameById(@PathVariable String id) {
        Optional<Game> game = gameRepository.findById(new ObjectId(id));
        return game.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Game> createGame(@Valid @RequestBody Game game) {
        game.setId(null);
        Game savedGame = gameRepository.save(game);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGame);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Game> updateGame(@PathVariable String id, @Valid @RequestBody Game game) {
        ObjectId oid = new ObjectId(id);
        if (!gameRepository.existsById(oid)) {
            return ResponseEntity.notFound().build();
        }
        game.setId(oid);
        Game updatedGame = gameRepository.save(game);
        return ResponseEntity.ok(updatedGame);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable String id) {
        ObjectId oid = new ObjectId(id);
        if (!gameRepository.existsById(oid)) {
            return ResponseEntity.notFound().build();
        }
        gameRepository.deleteById(oid);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Game>> searchGames(@RequestParam(required = false) String name,
                                                 @RequestParam(required = false) String category,
                                                 @RequestParam(required = false) String difficulty,
                                                 @RequestParam(required = false) Boolean isActive) {
        List<Game> games;
        
        if (name != null) {
            games = gameRepository.findByNameContainingIgnoreCase(name);
        } else if (category != null) {
            games = gameRepository.findByCategory(category);
        } else if (difficulty != null) {
            games = gameRepository.findByDifficulty(difficulty);
        } else if (isActive != null) {
            games = gameRepository.findByIsActive(isActive);
        } else {
            games = gameRepository.findAll();
        }
        
        return ResponseEntity.ok(games);
    }
    
    @GetMapping("/price-range")
    public ResponseEntity<List<Game>> getGamesByPriceRange(@RequestParam Double minPrice, 
                                                          @RequestParam Double maxPrice) {
        List<Game> games = gameRepository.findByPriceBetween(minPrice, maxPrice);
        return ResponseEntity.ok(games);
    }
}
