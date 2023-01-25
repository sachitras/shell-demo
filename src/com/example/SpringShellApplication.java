package com.example;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.shell.Bootstrap;

@SpringBootApplication
public class SpringShellApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(SpringShellApplication.class, args);
		Bootstrap.main(args);
	}
}
