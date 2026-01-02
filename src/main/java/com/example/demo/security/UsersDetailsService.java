package com.example.demo.security;

import com.example.demo.domain.auth.Users;
import com.example.demo.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsersDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        Optional<Users> result = Optional.ofNullable(usersRepository.findByUserId(userId))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Users users = result.get();

        if (users.getRoleSet().isEmpty()) {
            throw new UsernameNotFoundException("User has no roles assigned");
        }

        List<SimpleGrantedAuthority> authorities;

        try {
            authorities = users.getRoleSet().stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                    .toList();
        } catch (Exception e) {
            throw new UsernameNotFoundException("Cannot convert roles to authorities", e);
        }

        return null;
    }
}
