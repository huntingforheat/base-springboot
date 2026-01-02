package com.example.demo.repository;

import com.example.demo.domain.auth.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findByUserId(String userId);
}
