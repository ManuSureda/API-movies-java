package com.disneymovie.disneyJava.user.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
@PropertySource("application.properties")
@EnableScheduling
@EnableCaching
@AllArgsConstructor
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    CorsFilter corsFilter;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOriginPatterns("http://localhost:4200/")
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        .exposedHeaders("authorization")
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }

    @Bean
    public FilterRegistrationBean myFilterClient() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(corsFilter);
//        registration.addUrlPatterns("/api/*");

        return registration;
     }

}
