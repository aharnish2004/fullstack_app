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
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "*")
public class TransactionController {
    
    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired
    private MemberRepository memberRepository;
    
    @Autowired
    private GameRepository gameRepository;
    
    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        try {
            List<Transaction> transactions = transactionRepository.findAll();
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(List.of());
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable String id) {
        Optional<Transaction> transaction = transactionRepository.findById(new ObjectId(id));
        return transaction.map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@Valid @RequestBody Transaction transaction) {
        transaction.setId(null);
        transaction.setDateTime(LocalDateTime.now());
        Transaction savedTransaction = transactionRepository.save(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTransaction);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable String id, @Valid @RequestBody Transaction transaction) {
        ObjectId oid = new ObjectId(id);
        if (!transactionRepository.existsById(oid)) {
            return ResponseEntity.notFound().build();
        }
        transaction.setId(oid);
        Transaction updatedTransaction = transactionRepository.save(transaction);
        return ResponseEntity.ok(updatedTransaction);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable String id) {
        ObjectId oid = new ObjectId(id);
        if (!transactionRepository.existsById(oid)) {
            return ResponseEntity.notFound().build();
        }
        transactionRepository.deleteById(oid);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<Transaction>> getTransactionsByMember(@PathVariable String memberId) {
        List<Transaction> transactions = transactionRepository.findByMemberId(new ObjectId(memberId));
        return ResponseEntity.ok(transactions);
    }
    
    @GetMapping("/game/{gameId}")
    public ResponseEntity<List<Transaction>> getTransactionsByGame(@PathVariable String gameId) {
        List<Transaction> transactions = transactionRepository.findByGameId(new ObjectId(gameId));
        return ResponseEntity.ok(transactions);
    }
    
    @GetMapping("/date-range")
    public ResponseEntity<List<Transaction>> getTransactionsByDateRange(
            @RequestParam String startDate, 
            @RequestParam String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
        List<Transaction> transactions = transactionRepository.findByDateTimeBetween(start, end);
        return ResponseEntity.ok(transactions);
    }
    
    @GetMapping("/member/{memberId}/date-range")
    public ResponseEntity<List<Transaction>> getMemberTransactionsByDateRange(
            @PathVariable String memberId,
            @RequestParam String startDate, 
            @RequestParam String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
        List<Transaction> transactions = transactionRepository.findMemberTransactionsInDateRange(new ObjectId(memberId), start, end);
        return ResponseEntity.ok(transactions);
    }
}
