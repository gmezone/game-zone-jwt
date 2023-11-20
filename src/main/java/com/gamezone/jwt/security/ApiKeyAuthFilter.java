package com.gamezone.jwt.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ApiKeyAuthFilter extends OncePerRequestFilter {

    private final ApiKeyAuthExtractor extractor;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(SecurityContextHolder.getContext().getAuthentication() == null) {
           // try {
                System.out.println("----1----------");
                extractor.extract(request)
                        .ifPresent(SecurityContextHolder.getContext()::setAuthentication);
                System.out.println("----2----------");

//            }catch (Exception e){

              //  e.printStackTrace();
             //   System.out.println( e.getClass());
           // }

        }

        filterChain.doFilter(request, response);
    }
}