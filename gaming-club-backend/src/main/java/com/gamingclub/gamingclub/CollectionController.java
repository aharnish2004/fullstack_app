package com.gamingclub.gamingclub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;

@RestController
@RequestMapping("/api/collections")
@CrossOrigin(origins = "*")
public class CollectionController {
    
    @Autowired
    private CollectionRepository collectionRepository;
    
    @GetMapping
    public ResponseEntity<List<Collection>> getAllCollections() {
        List<Collection> collections = collectionRepository.findAll();
        return ResponseEntity.ok(collections);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Collection> getCollectionById(@PathVariable String id) {
        Optional<Collection> collection = collectionRepository.findById(new ObjectId(id));
        return collection.map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Collection> createCollection(@Valid @RequestBody Collection collection) {
        collection.setId(null);
        if (collection.getDate() == null) {
            collection.setDate(LocalDateTime.now());
        }
        Collection savedCollection = collectionRepository.save(collection);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCollection);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Collection> updateCollection(@PathVariable String id, @Valid @RequestBody Collection collection) {
        ObjectId oid = new ObjectId(id);
        if (!collectionRepository.existsById(oid)) {
            return ResponseEntity.notFound().build();
        }
        collection.setId(oid);
        Collection updatedCollection = collectionRepository.save(collection);
        return ResponseEntity.ok(updatedCollection);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollection(@PathVariable String id) {
        ObjectId oid = new ObjectId(id);
        if (!collectionRepository.existsById(oid)) {
            return ResponseEntity.notFound().build();
        }
        collectionRepository.deleteById(oid);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/date-range")
    public ResponseEntity<List<Collection>> getCollectionsByDateRange(
            @RequestParam String startDate, 
            @RequestParam String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
        List<Collection> collections = collectionRepository.findByDateBetween(start, end);
        return ResponseEntity.ok(collections);
    }
    
    @GetMapping("/type/{type}")
    public ResponseEntity<List<Collection>> getCollectionsByType(@PathVariable String type) {
        List<Collection> collections = collectionRepository.findByCollectionType(type);
        return ResponseEntity.ok(collections);
    }
    
    @GetMapping("/collected-by/{collectedBy}")
    public ResponseEntity<List<Collection>> getCollectionsByCollectedBy(@PathVariable String collectedBy) {
        List<Collection> collections = collectionRepository.findByCollectedBy(collectedBy);
        return ResponseEntity.ok(collections);
    }
}
