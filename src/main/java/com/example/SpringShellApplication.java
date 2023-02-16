package com.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.shell.Bootstrap;
import org.springframework.util.StringUtils;

@SpringBootApplication
@EnableAutoConfiguration
@Configuration
@PropertySource(value = "classpath:application.properties")
public class SpringShellApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(SpringShellApplication.class, args);
//
//		String[] disabledCommands = {
//				"--spring.shell.command.help.enabled=false"};
//		String[] fullArgs = StringUtils.concatenateStringArrays(args, disabledCommands);
//		Bootstrap.main(fullArgs);
////		Bootstrap.main(args);
//		// Test comment.

//		String[] disabledCommands = {"--spring.shell.command.help.enabled=false"};
//		String[] fullArgs = StringUtils.concatenateStringArrays(args, disabledCommands);
//		SpringApplication.run(SpringShellApplication.class, fullArgs);
		Bootstrap.main(args);
		

	}
}
