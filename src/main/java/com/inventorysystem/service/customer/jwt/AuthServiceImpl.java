package com.inventorysystem.service.customer.jwt;

import com.inventorysystem.dto.SignupRequest;
import com.inventorysystem.dto.UserDto;
import com.inventorysystem.entity.User;
import com.inventorysystem.enums.UserRole;
import com.inventorysystem.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityExistsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private  final UserRepository userRepository;


    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void createAnAdminAccount(){
        Optional<User> adminAccount = userRepository.findByUserRole(UserRole.ADMIN);
        if (adminAccount.isEmpty()){
            User user = new User();
            user.setEmail("admin@test.com");
            user.setFirstName("Admin");
            user.setLastName("Admin");
            user.setUserRole(UserRole.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(user);
            System.out.println("Admin account created successfully");
        } else {
            System.out.println("Admin account already exist");
        }
    }


    public UserDto createUser(SignupRequest signupRequest){
        if (userRepository.findFirstByEmail(signupRequest.getEmail()).isPresent()){
            throw new EntityExistsException("User Already Present with email " + signupRequest.getEmail());
        }

        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setFirstName(signupRequest.getFirstName());
        user.setLastName(signupRequest.getLastName());
        user.setUserRole(UserRole.CUSTOMER);
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        User createdUser = userRepository.save(user);
        return  createdUser.getUserDto();

    }
}
