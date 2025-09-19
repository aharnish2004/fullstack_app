package com.gamingclub.gamingclub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;

@RestController
@RequestMapping("/api/members")
@CrossOrigin(origins = "*")
public class MemberController {
    
    @Autowired
    private MemberRepository memberRepository;
    
    @Autowired
    private MongoTemplate mongoTemplate;
    
    private static final Logger log = LoggerFactory.getLogger(MemberController.class);
    
    @GetMapping
    public ResponseEntity<?> getAllMembers() {
        try {
            List<Document> docs = mongoTemplate.find(new Query(), Document.class, "members");
            java.util.List<String> json = new java.util.ArrayList<>(docs.size());
            for (Document d : docs) {
                json.add(d.toJson());
            }
            return ResponseEntity.ok(json);
        } catch (Exception ex) {
            log.error("Failed to fetch members (raw)", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(java.util.Map.of("error", ex.getClass().getSimpleName(), "message", String.valueOf(ex.getMessage())));
        }
    }

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("OK");
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable String id) {
        Optional<Member> member = memberRepository.findById(id);
        return member.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/raw")
    public ResponseEntity<List<?>> getAllMembersRaw() {
        List<Document> docs = mongoTemplate.find(new Query(), Document.class, "members");
        return ResponseEntity.ok(docs);
    }
    
    @GetMapping("/_debug")
    public ResponseEntity<?> debugMembers() {
        try {
            List<Member> members = memberRepository.findAll();
            return ResponseEntity.ok(java.util.Map.of("count", members.size()));
        } catch (Exception ex) {
            log.error("Debug fetch members failed", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(java.util.Map.of(
                        "error", ex.getClass().getName(),
                        "message", String.valueOf(ex.getMessage())
                    ));
        }
    }
    
    @GetMapping("/count")
    public ResponseEntity<?> countMembers() {
        long count = mongoTemplate.getCollection("members").countDocuments();
        return ResponseEntity.ok(java.util.Map.of("count", count));
    }
    
    @PostMapping
    public ResponseEntity<Member> createMember(@Valid @RequestBody Member member) {
        member.setId(null);
        Member savedMember = memberRepository.save(member);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMember);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable String id, @Valid @RequestBody Member member) {
        if (!memberRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        member.setId(id);
        Member updatedMember = memberRepository.save(member);
        return ResponseEntity.ok(updatedMember);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable String id) {
        if (!memberRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        memberRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Member>> searchMembers(@RequestParam(required = false) String name,
                                                     @RequestParam(required = false) String phone,
                                                     @RequestParam(required = false) Boolean isActive) {
        List<Member> members;
        
        if (name != null) {
            members = memberRepository.findByNameContainingIgnoreCase(name);
        } else if (phone != null) {
            Optional<Member> member = memberRepository.findByPhone(phone);
            members = member.map(List::of).orElse(List.of());
        } else if (isActive != null) {
            members = memberRepository.findByIsActive(isActive);
        } else {
            members = memberRepository.findAll();
        }
        
        return ResponseEntity.ok(members);
    }
    
    @GetMapping("/phone/{phone}")
    public ResponseEntity<Member> getMemberByPhone(@PathVariable String phone) {
        Optional<Member> member = memberRepository.findByPhone(phone);
        return member.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }
}
