package com.disneymovie.disneyJava;

import com.disneymovie.disneyJava.user.config.CorsFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.disneymovie.disneyJava")
public class DisneyJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DisneyJavaApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilterRegistration() {
		FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new CorsFilter());
		registrationBean.addUrlPatterns("/*");
		registrationBean.setName("corsFilter");
		registrationBean.setOrder(1);
		return registrationBean;
	}

}
