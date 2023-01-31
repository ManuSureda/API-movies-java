package com.disneymovie.disneyJava.user.config;

import com.disneymovie.disneyJava.user.session.AdminSessionFilter;
import com.disneymovie.disneyJava.user.session.SessionFilter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@org.springframework.context.annotation.Configuration
@PropertySource("application.properties")
@EnableScheduling
@EnableCaching
@AllArgsConstructor
public class Configuration {

    @Autowired
    SessionFilter sessionFilter;

    @Autowired
    AdminSessionFilter adminSessionFilter;

    @Bean
    public FilterRegistrationBean myFilterClient() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(sessionFilter);
        registration.addUrlPatterns("/movies/*");
        registration.addUrlPatterns("/movies/*/");
        registration.addUrlPatterns("/characters/*");
        registration.addUrlPatterns("/characters/*/");

        return registration;
     }

    @Bean
    public FilterRegistrationBean myFilterAdmin() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(adminSessionFilter);
        System.out.println("hoola");
        registration.addUrlPatterns("/movies/*");
        registration.addUrlPatterns("/movies/*/");
        registration.addUrlPatterns("/characters/*");
        registration.addUrlPatterns("/characters/*/");
        registration.addUrlPatterns("/auth/register");

        return registration;
    }
}
