package com.gamingclub.gamingclub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.bson.types.ObjectId;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@SpringBootApplication
public class GamingClubApplication {
    public static void main(String[] args) {
        SpringApplication.run(GamingClubApplication.class, args);
    }

    @Bean
    public Module objectIdModule() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(ObjectId.class, new ToStringSerializer());
        return module;
    }
}
