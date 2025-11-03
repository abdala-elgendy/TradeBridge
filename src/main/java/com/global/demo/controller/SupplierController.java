// src/main/java/com/global/demo/controller/SupplierController.java
package com.global.demo.controller;

import com.global.demo.entity.Supplier;
import com.global.demo.entity.User;
import com.global.demo.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @PostMapping("/add")
    public Supplier addSupplier(@AuthenticationPrincipal User user, @RequestBody Supplier supplier) {
        return supplierService.addSupplier(supplier);
    }

    @GetMapping("/allsuppliers")
    public List<Supplier> getAllSuppliers(@AuthenticationPrincipal User user) {
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
    public void deleteSupplier(@AuthenticationPrincipal Supplier user, @PathVariable Long id) {
        supplierService.deleteSupplier(id);
    }
}