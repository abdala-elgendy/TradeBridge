// src/main/java/com/global/demo/controller/SupplierController.java
package com.global.demo.controller;

import com.global.demo.entity.Supplier;
import com.global.demo.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @PostMapping("/add")
    public Supplier addSupplier(@RequestBody Supplier supplier) {
        return supplierService.addSupplier(supplier);
    }

    @GetMapping("/allsuppliers")
    public List<Supplier> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }

    @GetMapping("/{id}")
    public Supplier getSupplierById(@PathVariable Long id) {
        return supplierService.getSupplierById(id);
    }

    // @PutMapping("/{id}")
    // public Supplier updateSupplier(@PathVariable Long id, @RequestBody Supplier supplier) {
    // return supplierService.updateSupplier(id, supplier);
    // }

    @DeleteMapping("/{id}")
    public void deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
    }
}