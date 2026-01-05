package com.example.demo.repository;

import com.example.demo.domain.auth.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findByUserId(String userId);

    // 해당 userId가 존재하는지
    boolean existsByUserId(String userId);

    boolean existsByEmail(String email);
}
