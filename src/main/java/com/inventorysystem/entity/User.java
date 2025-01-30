package com.inventorysystem.entity;

import com.inventorysystem.dto.UserDto;
import com.inventorysystem.enums.UserRole;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users") // Use pluralized table names for convention
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private UserRole userRole;

    // No-argument constructor (required by JPA)
    public User() {
    }

    // All-argument constructor
    public User(String firstName, String lastName, String email, String password, UserRole userRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Implementation of UserDetails methods
    @Override
    public String getUsername() {
        return email; // Use email as the username for authentication
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userRole.name())); // Map roles to authorities
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Account is always non-expired
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Account is always non-locked
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Credentials are always non-expired
    }

    @Override
    public boolean isEnabled() {
        return true; // Account is always enabled
    }

    public UserDto getUserDto(){
        UserDto dto = new UserDto();
        dto.setId(id);
        dto.setFirstName(firstName);
        dto.setLastName(lastName);
        dto.setEmail(email);
        dto.setUserRole(userRole);

        return dto;
    }
}
