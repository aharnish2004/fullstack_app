package com.gamingclub.gamingclub;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;

@Repository
public interface AdminUserRepository extends MongoRepository<AdminUser, ObjectId> {
    
    Optional<AdminUser> findByUsername(String username);
    
    List<AdminUser> findByRole(String role);
    
    List<AdminUser> findByIsActive(Boolean isActive);
    
    Optional<AdminUser> findByUsernameAndPassword(String username, String password);
}
