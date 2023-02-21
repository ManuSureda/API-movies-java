package com.disneymovie.disneyJava.user.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@Service
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class SessionFilter extends OncePerRequestFilter {

    @Autowired
    private SessionManager sessionManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String sessionToken = request.getHeader("authorization");
        Session session = sessionManager.getSession(sessionToken);

        System.out.println("--------------------");
        System.out.println("cliente?");
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                System.out.println("Header: " + request.getHeader(headerNames.nextElement()));
            }
        }
        System.out.println("AAAAAAAAAAAAAAAAAAAA");
        System.out.println(request.getHeader("authorization"));
        System.out.println("AAAAAAAAAAAAAAAAAAAA");
        System.out.println("--------------------");

//        null != session && session.getLoggedUser().getUserRole().equals(UserRole.client)
        if (null != session || request.getRequestURI().equals("/auth/login")) {
            System.out.println("ENTRE");
            System.out.println(sessionToken + " - " + session.getToken());
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpStatus.FORBIDDEN.value());
        }
    }
}