package com.inventorysystem.controller.auth;

import com.inventorysystem.dto.AuthenticationRequest;
import com.inventorysystem.dto.AuthenticationResponse;
import com.inventorysystem.dto.SignupRequest;
import com.inventorysystem.dto.UserDto;
import com.inventorysystem.entity.User;
import com.inventorysystem.repository.UserRepository;
import com.inventorysystem.service.customer.jwt.AuthService;
import com.inventorysystem.service.customer.jwt.JwtService;
import com.inventorysystem.service.customer.auth.UserService;
import jakarta.persistence.EntityExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    private final AuthenticationManager authenticationManager;

    private  final UserRepository userRepository;

    private final JwtService jwtService;

    private final UserService userService;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager, UserRepository userRepository, JwtService jwtService, UserService userService) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    public AuthService getAuthService() {
        return authService;
    }

    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public JwtService getJwtService() {
        return jwtService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest){
        try {
            UserDto createdUser = authService.createUser(signupRequest);
            return  new ResponseEntity<>(createdUser, HttpStatus.OK);
        } catch (EntityExistsException entityExistsException){
            return new ResponseEntity<>("User already exists",
                    HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e){
            return new ResponseEntity<>("User not created, come again later",
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e){
            throw new BadCredentialsException("Incorrect username or password");
        }

        final UserDetails userDetails =
                userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
        Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
        final  String jwt = jwtService.generateToken(userDetails);

        AuthenticationResponse authenticationResponse =
                new AuthenticationResponse();
        if (optionalUser.isPresent()){
            authenticationResponse.setJwt(jwt);
            authenticationResponse.setUserRole(optionalUser.get().getUserRole());
            authenticationResponse.setUserId(optionalUser.get().getId());
        }

        return authenticationResponse;

    }
}
