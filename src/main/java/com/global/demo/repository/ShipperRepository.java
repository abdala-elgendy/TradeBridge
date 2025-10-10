package com.global.demo.repository;

import com.global.demo.entity.Shipper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShipperRepository extends JpaRepository<Shipper, Long> {
    
    @Query("SELECT s FROM Shipper s JOIN FETCH s.user WHERE s.user.id = :userId")
    Optional<Shipper> findByUserId(@Param("userId") Long userId);
    
    @Query("SELECT s FROM Shipper s JOIN FETCH s.user WHERE s.user.email = :email")
    Optional<Shipper> findByUserEmail(@Param("email") String email);
}