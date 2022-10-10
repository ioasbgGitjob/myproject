package com.example.jdkdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Random;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

@SpringBootApplication
public class JdkDemoApplication {

    public static void main(String[] args) {
        var random = new Random(10);
        for (int i = 0; i < 10; i++) {
            System.out.println(random.nextInt());
        }





        SpringApplication.run(JdkDemoApplication.class, args);
    }

}
