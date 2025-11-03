package com.global.demo.repository;

import com.global.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmailForAuthentication(@Param("email") String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.customer WHERE u.email = :email")
    Optional<User> findByEmailWithCustomer(@Param("email") String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.supplier WHERE u.email = :email")
    Optional<User> findByEmailWithSupplier(@Param("email") String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.shipper WHERE u.email = :email")
    Optional<User> findByEmailWithShipper(@Param("email") String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.admin WHERE u.email = :email")
    Optional<User> findByEmailWithAdmin(@Param("email") String email);


    boolean existsByEmail(String email);

    boolean existsByNationalId(String nationalId);

//    @Query("SELECT u.email FROM User u WHERE u.name = :username")
//     List<String> findByUsername(@Param("username") String username);
}