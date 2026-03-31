package com.university.MarketPlace.security.sec;

import com.university.MarketPlace.security.dto.LoginRequest;
import com.university.MarketPlace.security.dto.LoginResponse;
import com.university.MarketPlace.security.dto.RegisterRequest;
import com.university.MarketPlace.security.config.JwtService;
import com.university.MarketPlace.security.user.User;
import com.university.MarketPlace.security.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class SecurityController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login/{role}")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request, @PathVariable String role){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = (User) authentication.getPrincipal();

        assert user != null;
        User user1 = userRepository.findByUsernameAndRoles(user.getUsername(), role)
                .orElseThrow(()->new RuntimeException("User not matched with Role"));

        String accessToken = JwtService.generateToken(user1.getEmail(), role);
        System.out.println(role);

        LoginResponse resp = new LoginResponse();
        resp.setToken(accessToken);
        resp.setExpiryTime("60");

        return ResponseEntity.ok(resp);
    }

    @PostMapping("/register/{role}")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request, @PathVariable String role) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Username already exists"));
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email already registered"));
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Collections.singleton(role));

        userRepository.save(user);


        return ResponseEntity.ok(Map.of("message", "User created"));
    }

}
