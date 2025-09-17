package com.gamingclub.gamingclub;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {
    
    List<Transaction> findByMemberId(String memberId);
    
    List<Transaction> findByGameId(String gameId);
    
    List<Transaction> findByDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    List<Transaction> findByTransactionType(String transactionType);
    
    List<Transaction> findByStatus(String status);
    
    @Query("{'memberId': ?0, 'dateTime': {$gte: ?1, $lte: ?2}}")
    List<Transaction> findMemberTransactionsInDateRange(String memberId, LocalDateTime startDate, LocalDateTime endDate);
}
