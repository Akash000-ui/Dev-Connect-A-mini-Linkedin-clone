package com.akdev.devconnect.devconnect.config;

import com.akdev.devconnect.devconnect.services.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {


        String path = request.getRequestURI();
        System.out.println("Inside JWT filter for secured path: " + path);
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            return;
        }


        if(path.startsWith("/uploads")){
            filterChain.doFilter(request, response); // skip JWT check
            return;
        }
        // Skip login and register endpoints
        if (path.equals("/api/v1/user/login/google")|| path.equals("/api/v1/user/login")
                || path.equals("/api/v1/user/register/google")
                || path.equals("/api/v1/user/register")||
         path.startsWith("/ws") ) {
            System.out.println("Skipping JWT filter for path: " + path);
            filterChain.doFilter(request, response); // skip JWT check
            return;
        }

        System.out.println("Inside JWT filter for secured path: " + path);
        String authHeader = request.getHeader("Authorization");
        System.out.println("Authorization header: " + authHeader);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            System.out.println("Token found in header");
            String token = authHeader.substring(7);
            try {
                System.out.println("Validating token: " + token);
                Claims claims = jwtService.validateToken(token);
                request.setAttribute("claims", claims);
                System.out.println("Token validated successfully");
            } catch (Exception e) {
                System.out.println("Token validation failed: " + e.getMessage());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                return;
            }
        } else {
            System.out.println("No token found in header");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing token");
            return;
        }

        System.out.println("Continuing with request processing");
        filterChain.doFilter(request, response); // continue with the request
    }
}