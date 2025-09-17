package com.gamingclub.gamingclub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
public class WelcomeController {
    
    @Autowired
    private MongoTemplate mongoTemplate;
    
    @GetMapping("/")
    public Map<String, Object> welcome() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Welcome to Gaming Club Management System API");
        response.put("version", "1.0.0");
        response.put("status", "Running");
        response.put("endpoints", Map.of(
            "members", "/api/members",
            "games", "/api/games", 
            "transactions", "/api/transactions",
            "recharges", "/api/recharges",
            "collections", "/api/collections",
            "admin", "/api/admin",
            "health", "/actuator/health"
        ));
        response.put("documentation", "Check POSTMAN_SETUP_GUIDE.md for complete API documentation");
        return response;
    }
    
    @GetMapping("/api")
    public Map<String, Object> apiInfo() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Gaming Club API");
        response.put("baseUrl", "/api");
        response.put("collections", Map.of(
            "Members", "GET /api/members",
            "Games", "GET /api/games",
            "Transactions", "GET /api/transactions", 
            "Recharges", "GET /api/recharges",
            "Collections", "GET /api/collections",
            "Admin Users", "GET /api/admin"
        ));
        return response;
    }
    
    @GetMapping("/test")
    public Map<String, Object> test() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "OK");
        response.put("message", "Server is running without database dependency");
        response.put("timestamp", java.time.LocalDateTime.now());
        return response;
    }
    
    @GetMapping("/db-test")
    public Map<String, Object> testDatabase() {
        Map<String, Object> response = new HashMap<>();
        try {
            // Test MongoDB connection
            String dbName = mongoTemplate.getDb().getName();
            response.put("status", "SUCCESS");
            response.put("message", "MongoDB connection successful");
            response.put("database", dbName);
            response.put("timestamp", java.time.LocalDateTime.now());
        } catch (Exception e) {
            response.put("status", "ERROR");
            response.put("message", "MongoDB connection failed: " + e.getMessage());
            response.put("timestamp", java.time.LocalDateTime.now());
        }
        return response;
    }
    
    @GetMapping("/status")
    public Map<String, Object> status() {
        Map<String, Object> response = new HashMap<>();
        response.put("application", "Gaming Club Management System");
        response.put("status", "RUNNING");
        response.put("version", "1.0.0");
        response.put("timestamp", java.time.LocalDateTime.now());
        return response;
    }
}
