package com.inventorysystem.service.customer.jwt;

import com.inventorysystem.dto.SignupRequest;
import com.inventorysystem.dto.UserDto;

public interface AuthService {

    UserDto createUser(SignupRequest signupRequest);
}
