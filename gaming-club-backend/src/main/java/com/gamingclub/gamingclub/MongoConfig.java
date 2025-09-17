package com.gamingclub.gamingclub;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.core.MongoTemplate;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
@EnableMongoRepositories
public class MongoConfig extends AbstractMongoClientConfiguration {
    
    @Override
    protected String getDatabaseName() {
        return "students_db";
    }
    
    @Override
    protected boolean autoIndexCreation() {
        return true;
    }
    
    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create("mongodb+srv://2023csaharnishdubeya_db_user:BWS8uAwuTbCrKADC@cluster0.wmjasfq.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0");
    }
    
    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), getDatabaseName());
    }
}
