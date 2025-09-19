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
@RequestMapping("/api/recharges")
@CrossOrigin(origins = "*")
public class RechargeController {
    
    @Autowired
    private RechargeRepository rechargeRepository;
    
    @Autowired
    private MemberRepository memberRepository;
    
    @GetMapping
    public ResponseEntity<List<Recharge>> getAllRecharges() {
        try {
            List<Recharge> recharges = rechargeRepository.findAll();
            return ResponseEntity.ok(recharges);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(List.of());
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Recharge> getRechargeById(@PathVariable String id) {
        Optional<Recharge> recharge = rechargeRepository.findById(new ObjectId(id));
        return recharge.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Recharge> createRecharge(@Valid @RequestBody Recharge recharge) {
        recharge.setId(null);
        recharge.setDateTime(LocalDateTime.now());
        
        // Update member balance
        Optional<Member> memberOpt = memberRepository.findById(recharge.getMemberId());
        if (memberOpt.isPresent()) {
            Member member = memberOpt.get();
            member.setBalance(member.getBalance() + recharge.getAmount());
            memberRepository.save(member);
        }
        
        Recharge savedRecharge = rechargeRepository.save(recharge);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRecharge);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Recharge> updateRecharge(@PathVariable String id, @Valid @RequestBody Recharge recharge) {
        ObjectId oid = new ObjectId(id);
        if (!rechargeRepository.existsById(oid)) {
            return ResponseEntity.notFound().build();
        }
        recharge.setId(oid);
        Recharge updatedRecharge = rechargeRepository.save(recharge);
        return ResponseEntity.ok(updatedRecharge);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecharge(@PathVariable String id) {
        ObjectId oid = new ObjectId(id);
        if (!rechargeRepository.existsById(oid)) {
            return ResponseEntity.notFound().build();
        }
        rechargeRepository.deleteById(oid);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<Recharge>> getRechargesByMember(@PathVariable String memberId) {
        List<Recharge> recharges = rechargeRepository.findByMemberId(new ObjectId(memberId));
        return ResponseEntity.ok(recharges);
    }
    
    @GetMapping("/date-range")
    public ResponseEntity<List<Recharge>> getRechargesByDateRange(
            @RequestParam String startDate, 
            @RequestParam String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
        List<Recharge> recharges = rechargeRepository.findByDateTimeBetween(start, end);
        return ResponseEntity.ok(recharges);
    }
    
    @GetMapping("/member/{memberId}/date-range")
    public ResponseEntity<List<Recharge>> getMemberRechargesByDateRange(
            @PathVariable String memberId,
            @RequestParam String startDate, 
            @RequestParam String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
        List<Recharge> recharges = rechargeRepository.findMemberRechargesInDateRange(new ObjectId(memberId), start, end);
        return ResponseEntity.ok(recharges);
    }
}
