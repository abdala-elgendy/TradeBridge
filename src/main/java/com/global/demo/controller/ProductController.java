package com.global.demo.controller;

import com.global.demo.dto.ProductDTO;
import com.global.demo.dto.TopProductDTO;
import com.global.demo.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @PreAuthorize("hasRole('SUPPLIER')")
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        ProductDTO createdProduct = productService.addProduct(productDTO);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        ProductDTO product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPPLIER')")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductDTO productDTO) {
        ProductDTO updatedProduct = productService.updateProduct(id, productDTO);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPPLIER')")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        String message = productService.deleteProduct(id);
        return ResponseEntity.ok(message);
    }

    @PatchMapping("/{id}/stock")
    @PreAuthorize("hasRole('SUPPLIER')")
    public ResponseEntity<Boolean> updateStock(
            @PathVariable Long id,
            @RequestParam int quantity) {
        boolean updated = productService.updateStock(id, quantity);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/add-stock")
    @PreAuthorize("hasRole('SUPPLIER')")
    public ResponseEntity<Boolean> addToStock(
            @PathVariable Long id,
            @RequestParam int quantity) {
        boolean updated = productService.addToStock(id, quantity);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/{id}/purchase")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Boolean> purchaseProduct(
            @PathVariable Long id,
            @RequestParam int quantity) {
        boolean purchased = productService.purchaseProduct(id, quantity);
        return ResponseEntity.ok(purchased);
    }

    @GetMapping("/top-selling")
    public List<TopProductDTO> getTopSellingProducts(
            @RequestParam(defaultValue = "10") int limit) {
        return productService.getTopSellingProducts(limit);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
    }
}
