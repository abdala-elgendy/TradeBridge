package com.global.demo.repository;

import com.global.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.customer LEFT JOIN FETCH u.supplier LEFT JOIN FETCH u.shipper LEFT JOIN FETCH u.admin WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    boolean existsByEmail(String email);

    boolean existsByNationalId(String nationalId);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.customer LEFT JOIN FETCH u.supplier LEFT JOIN FETCH u.shipper LEFT JOIN FETCH u.admin WHERE u.verificationToken = :token")
    Optional<User> findByVerificationToken(@Param("token") String token);
}