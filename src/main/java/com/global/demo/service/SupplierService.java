// src/main/java/com/global/demo/service/SupplierService.java
package com.global.demo.service;

import com.global.demo.entity.Supplier;
import com.global.demo.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public Supplier addSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public Supplier getSupplierById(Long id) {
        Optional<Supplier> supplier = supplierRepository.findById(id);
        return supplier.orElse(null);
    }

    // public Supplier updateSupplier(Long id, Supplier supplier) {
    // if (supplierRepository.existsById(id)) {
    // supplier.setId(id);
    // return supplierRepository.save(supplier);
    // }
    // return supplier;
    // }

    public void deleteSupplier(Long id) {
        supplierRepository.deleteById(id);
    }
}