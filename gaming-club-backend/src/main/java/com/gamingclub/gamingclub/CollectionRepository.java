package com.gamingclub.gamingclub;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import org.bson.types.ObjectId;

@Repository
public interface CollectionRepository extends MongoRepository<Collection, ObjectId> {
    
    List<Collection> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    List<Collection> findByCollectionType(String collectionType);
    
    List<Collection> findByStatus(String status);
    
    List<Collection> findByCollectedBy(String collectedBy);
    
    @Query("{'date': {$gte: ?0, $lte: ?1}}")
    List<Collection> findCollectionsInDateRange(LocalDateTime startDate, LocalDateTime endDate);
}
