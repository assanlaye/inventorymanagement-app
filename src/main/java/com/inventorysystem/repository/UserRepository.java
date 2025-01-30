package com.inventorysystem.repository;

import com.inventorysystem.enums.UserRole;
import com.inventorysystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserRole(UserRole userRole);

    Optional<User> findFirstByEmail(String email);

}
