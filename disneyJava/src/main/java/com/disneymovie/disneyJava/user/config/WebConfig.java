package com.disneymovie.disneyJava.user.config;

import com.disneymovie.disneyJava.user.session.SessionFilter;
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
    SessionFilter sessionFilter;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            //            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/api/**")
//                        .allowedOrigins("http://localhost:4200")
//                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                        .allowedHeaders("*")
//                        .exposedHeaders("Authorization")
//                        .maxAge(3600);// le quite el allowcredentials
//            }
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedOrigins("*")
//                        .allowedMethods("*")
//                        .allowedMethods("OPTIONS")
//                        .allowedHeaders("*")
//                        .exposedHeaders("*");
////                        .allowedOriginPatterns("*")
////                        .allowedOriginPatterns("http://localhost:4200/")
////                        .allowedOriginPatterns("http://localhost:4200/characters")
////                        .allowedOriginPatterns("http://localhost:4200/movies")
////                        .allowedOriginPatterns("http://localhost:8080/api/movies");
//            }

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOriginPatterns("http://*.localhost:*")
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
        registration.setFilter(sessionFilter);
//        registration.addUrlPatterns("/api/movies*");
//        registration.addUrlPatterns("/api/movies/*");
//        registration.addUrlPatterns("/api/movies/*/");
////        registration.addUrlPatterns("/api/characters*");
//        registration.addUrlPatterns("/api/characters/*");
//        registration.addUrlPatterns("/api/characters/*/");
        registration.addUrlPatterns("/api/*");

//        registration.addUrlPatterns("/movies/*");
//        registration.addUrlPatterns("/movies/*/");
//        registration.addUrlPatterns("/characters/*");
//        registration.addUrlPatterns("/characters/*/");

        return registration;
     }

}
