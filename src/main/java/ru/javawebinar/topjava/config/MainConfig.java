package ru.javawebinar.topjava.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.repository.inMemory.InMemoryUserRepository;

@Configuration
public class MainConfig {
    @Bean
    public UserRepository inMemoryUserRepository(){
        return new InMemoryUserRepository();
    }
}
