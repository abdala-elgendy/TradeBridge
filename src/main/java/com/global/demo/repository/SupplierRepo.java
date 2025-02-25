package com.global.demo.repository;

import com.global.demo.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepo extends JpaRepository<Supplier,Long> {

    Supplier getSuppliersById(Long id);
}
