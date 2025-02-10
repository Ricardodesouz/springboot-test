package com.ricardo.estudospringboot.config;

import com.ricardo.estudospringboot.entities.Order;
import com.ricardo.estudospringboot.entities.User;
import com.ricardo.estudospringboot.entities.enums.OrderStatus;
import com.ricardo.estudospringboot.repositories.OrderRespository;
import com.ricardo.estudospringboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Instant;
import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRespository orderRespository;


    @Override
    public void run(String... args) throws Exception {
        User u1 = new User(null,"ricardo","ricardo@gmail.com", "123456");
        User u2 = new User(null,"raquel","raquel@gmail.com", "989089");
        userRepository.saveAll(Arrays.asList(u1,u2));
        Order o1 = new Order(null, Instant.parse("2019-06-20T19:53:07Z"), u1, OrderStatus.PAID);
        Order o2 = new Order(null, Instant.parse("2019-07-21T03:42:10Z"), u2,OrderStatus.PAID);
        Order o3 = new Order(null, Instant.parse("2019-07-22T15:21:22Z"), u1, OrderStatus.CANCELED);
        orderRespository.saveAll(Arrays.asList(o1,o2,o3));



    }
}
