package com.gamingclub.gamingclub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/members")
@CrossOrigin(origins = "*")
public class MemberController {
    
    @Autowired
    private MemberRepository memberRepository;
    
    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers() {
        try {
            List<Member> members = memberRepository.findAll();
            return ResponseEntity.ok(members);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(List.of());
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable String id) {
        Optional<Member> member = memberRepository.findById(id);
        return member.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
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
