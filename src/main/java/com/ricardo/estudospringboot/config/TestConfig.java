package com.ricardo.estudospringboot.config;

import com.ricardo.estudospringboot.entities.User;
import com.ricardo.estudospringboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        User u1 = new User(null,"ricardo","ricardo@gmail.com", "123456");
        User u2 = new User(null,"raquel","raquel@gmail.com", "989089");
        userRepository.saveAll(Arrays.asList(u1,u2));


    }
}
