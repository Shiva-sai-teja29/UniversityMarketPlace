package com.university.MarketPlace.security.oauth2;

import com.university.MarketPlace.security.user.User;
import com.university.MarketPlace.security.user.UserRepository;
import com.university.MarketPlace.security.config.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        OAuth2User user = (OAuth2User) authentication.getPrincipal();

        assert user != null;
        String email = user.getAttribute("email");
            String name = user.getAttribute("name");
            String providerId = user.getAttribute("sub");

            // Generate JWT
            String token = JwtService.generateToken(email,"ROLE_USER");

            Optional<User> existingUser = userRepository.findByUsername(name);
            if (existingUser.isEmpty()){
                User user1 = new User();
                user1.setEmail(email);
                user1.setUsername(name);
                user1.setRoles(Collections.singleton("ROLE_USER"));
                userRepository.save(user1);
            }

            // Redirect to React with token
//            response.sendRedirect("http://localhost:5173/home?token=" + token);
        response.sendRedirect("http://localhost:5173/oauth-success?token=" + token);
        System.out.println("http://localhost:5173/home?token=" + token);
    }
}