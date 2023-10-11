package com.realnet.loggingeachuserconfig;

import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.realnet.config.TokenProvider;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class UserAccessLogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Attempt to obtain the username from different sources
        String username = getUsernameFromRequest(request);
        if (username != null) {
            MDC.put("USER", username);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        MDC.clear();
    }

    private String getUsernameFromRequest(HttpServletRequest request) {
        
        String username = null;
        
        
        String authorizationHeader = request.getHeader("Authorization"); // Example: Bearer <token>
        String token=null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
             token = authorizationHeader.substring(7); 
        }
        TokenProvider tokenprovide = new TokenProvider();
        
        username = tokenprovide.getEmailFromToken(token);
        

        return username;
    }
}






