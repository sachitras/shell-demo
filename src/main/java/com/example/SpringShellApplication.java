package com.example;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.shell.Bootstrap;

@SpringBootApplication
@EnableAutoConfiguration
@Configuration
@PropertySource(value = "classpath:application.properties")
public class SpringShellApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(SpringShellApplication.class, args);
		Bootstrap.main(args);
		// Test comment.

	}
}
