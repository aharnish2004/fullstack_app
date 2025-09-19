package com.gamingclub.gamingclub;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.bson.types.ObjectId;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends MongoRepository<Member, String> {
    
    Optional<Member> findByPhone(String phone);
    
    List<Member> findByNameContainingIgnoreCase(String name);
    
    List<Member> findByIsActive(Boolean isActive);
    
    List<Member> findByMembershipType(String membershipType);
    
    List<Member> findByBalanceGreaterThan(Double balance);
}
