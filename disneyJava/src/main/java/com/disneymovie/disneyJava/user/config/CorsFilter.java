package com.disneymovie.disneyJava.user.config;

import com.disneymovie.disneyJava.user.session.Session;
import com.disneymovie.disneyJava.user.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

@Component
public class CorsFilter implements Filter {

    @Autowired
    private SessionManager sessionManager;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("---ENTRE EN doFilter (CorsFilter)---");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if ( httpRequest.getRequestURI().equals("/auth/login") ) {
            System.out.println("entre al primer if");
            System.out.println(httpRequest.getHeader("authorization"));
            chain.doFilter(httpRequest, httpResponse);
        } else {
            System.out.println("entre al else");
            System.out.println(httpRequest.getRequestURI());
            System.out.println("request headers: ");

            String sessionToken = httpRequest.getHeader("authorization");
            System.out.println(sessionToken);
            Session session = sessionManager.getSession(sessionToken);

            httpResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
            httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
            httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, authorization, X-Requested-With");
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpResponse.setHeader("Access-Control-Max-Age", "3600");

            if (null != session) {
                System.out.println("ENTRE");
                System.out.println(sessionToken + " - " + session.getToken());
                chain.doFilter(httpRequest, httpResponse);
            } else {
                httpResponse.setStatus(HttpStatus.FORBIDDEN.value());
            }
        }


    }

}
