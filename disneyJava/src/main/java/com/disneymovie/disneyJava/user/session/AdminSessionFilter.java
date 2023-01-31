package com.disneymovie.disneyJava.user.session;

import com.disneymovie.disneyJava.user.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class AdminSessionFilter extends OncePerRequestFilter {
    @Autowired
    private SessionManager sessionManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String sessionToken = request.getHeader("Authorization");
//        System.out.println("--------------------");
//        System.out.println("admin?");
//        System.out.println(request.getHeader("Authorization"));
//        System.out.println("--------------------");
        Session session = sessionManager.getSession(sessionToken);

//         && session.getLoggedUser().getUserRole().equals(UserRole.admin)
        if (null != session) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpStatus.FORBIDDEN.value());
        }
    }
}
