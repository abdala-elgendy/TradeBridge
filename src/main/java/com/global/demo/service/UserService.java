package com.global.demo.service;

import com.global.demo.entity.User;
import com.global.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    
    /**
     * Loads a user with all related entities in a single query to avoid N+1 problems
     */
    @Transactional(readOnly = true)
    public Optional<User> findUserWithRoles(String email) {
        return userRepository.findByEmailForAuthentication(email);
    }
    
    /**
     * Loads a user by verification token with all related entities
     */
//    @Transactional(readOnly = true)
//    public Optional<User> findUserByTokenWithRoles(String token) {
//       return userRepository.findByVerificationToken(token);
//    }
}

