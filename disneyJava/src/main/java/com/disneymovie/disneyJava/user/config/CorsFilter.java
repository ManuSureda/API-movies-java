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
import java.util.Map;

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
            chain.doFilter(request, response);
        } else {

            //  ESTO TENGO QUE BORRARLO, ES PARA PODER SEGUIR AVANZANDO CON EL FRONT-END

                chain.doFilter(request, response);


//            System.out.println("entre al else \n uri: ");
//            System.out.println(httpRequest.getRequestURI());
//            System.out.println("request headers: ");
//            System.out.println(httpRequest.getHeader("authorization"));
//            System.out.println(httpRequest.getHeader("access-control-request-headers"));
//
//            System.out.println("sessions: ");
//            Map<String, Session> sessions = sessionManager.getSessionMap();
//            if (sessions.isEmpty()) {
//                System.out.println("mama mia");
//            }
//            for (Map.Entry<String, Session> entry : sessions.entrySet()) {
//                String key = entry.getKey();
//                Session value = entry.getValue();
//                System.out.println("key: " + key + " - values: " + value);
//            }
//
//            String sessionToken = httpRequest.getHeader("authorization");
//            System.out.println(sessionToken);
//            Session session = sessionManager.getSession(sessionToken);
//
//            httpResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
//            httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
//            httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, authorization, X-Requested-With");
//            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
//            httpResponse.setHeader("Access-Control-Max-Age", "3600");
//
//            if (null != session) {
//                System.out.println("ENTRE");
//                System.out.println(sessionToken + " - " + session.getToken());
//                chain.doFilter(httpRequest, httpResponse);
//            } else {
//                httpResponse.setStatus(HttpStatus.FORBIDDEN.value());
//            }
        }


    }

}
