package com.gamingclub.gamingclub;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import org.bson.types.ObjectId;

@Repository
public interface RechargeRepository extends MongoRepository<Recharge, ObjectId> {
    
    List<Recharge> findByMemberId(ObjectId memberId);
    
    List<Recharge> findByDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    List<Recharge> findByPaymentMethod(String paymentMethod);
    
    List<Recharge> findByStatus(String status);
    
    @Query("{'memberId': ?0, 'dateTime': {$gte: ?1, $lte: ?2}}")
    List<Recharge> findMemberRechargesInDateRange(ObjectId memberId, LocalDateTime startDate, LocalDateTime endDate);
}
