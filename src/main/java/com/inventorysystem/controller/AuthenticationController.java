package com.inventorysystem.controller;

import com.inventorysystem.entity.AuthenticationResponse;
import com.inventorysystem.entity.User;
import com.inventorysystem.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody User request
            ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody User request
    ){
        return ResponseEntity.ok(authService.authenticate(request));
    }


    //logout
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        // Simply return a success message, since JWTs are stateless and don't need server-side storage
        String token = extractToken(request);
        if (token != null) {
            // Invalidate the token on the client-side (client should delete the token)
        }

        // Respond that the logout is successful
        return ResponseEntity.ok("Logged out successfully");
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7); // Extract the token after "Bearer "
        }
        return null;
    }


}
