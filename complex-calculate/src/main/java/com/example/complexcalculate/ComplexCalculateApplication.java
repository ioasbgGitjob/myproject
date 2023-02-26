package com.example.complexcalculate;

import com.example.complexcalculate.entity.ExampleCalculate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ComplexCalculateApplication {

//	@Bean
//	ExampleCalculate exampleCalculate() {
//		return new ExampleCalculate();
//	}


	public static void main(String[] args) {
		SpringApplication.run(ComplexCalculateApplication.class, args);
	}

}
