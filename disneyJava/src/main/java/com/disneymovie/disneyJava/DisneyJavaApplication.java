package com.disneymovie.disneyJava;

import com.disneymovie.disneyJava.webConfig.WebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(WebConfig.class)
public class DisneyJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DisneyJavaApplication.class, args);
	}

}
