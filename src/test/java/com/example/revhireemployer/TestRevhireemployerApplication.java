package com.example.revhireemployer;

import org.springframework.boot.SpringApplication;

public class TestRevhireemployerApplication {

	public static void main(String[] args) {
		SpringApplication.from(RevhireemployerApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
