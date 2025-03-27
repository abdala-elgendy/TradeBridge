// src/main/java/com/global/demo/service/ProductService.java
package com.global.demo.service;

import com.global.demo.dto.ProductDTO;
import com.global.demo.entity.Product;
import com.global.demo.mapper.ProductMapper;
import com.global.demo.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo productRepository;
    private final ProductMapper productMapper;

    public ProductDTO addProduct(ProductDTO productDTO) {
        Product product = productMapper.toProduct(productDTO);
        product = productRepository.save(product);
        return productMapper.toProductDTO(product);
    }

    public List<ProductDTO> getAllProducts() {
        List<ProductDTO> productDTOList = productMapper.toProductDTOList(productRepository.findAll());
        return productDTOList;
    }

    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        return productMapper.toProductDTO(product);
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product product = productMapper.toProduct(productDTO);

        product = productRepository.save(product);
        return productMapper.toProductDTO(product);
    }

    public String deleteProduct(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            return "Product not found";
        }

        return "Product deleted successfully";
    }

    @Transactional
    public boolean updateStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        try {
            boolean updated = product.updateStock(quantity);
            if (updated) {
                productRepository.save(product);
                return true;
            }
            return false;
        } catch (OptimisticLockingFailureException e) {
            // Retry logic can be implemented here if needed
            throw new RuntimeException("Concurrent update detected. Please try again.");
        }
    }

    @Transactional
    public boolean addToStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        try {
            boolean updated = product.addToStock(quantity);
            if (updated) {
                productRepository.save(product);
                return true;
            }
            return false;
        } catch (OptimisticLockingFailureException e) {
            // Retry logic can be implemented here if needed
            throw new RuntimeException("Concurrent update detected. Please try again.");
        }
    }

    @Transactional(readOnly = true)
    public ProductDTO getProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return convertToDTO(product);
    }

    private ProductDTO convertToDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .totalSold(product.getTotalSold())
                .category(product.getCategory())
                .brand(product.getBrand())
                .weight(product.getWeight())
                .weightUnit(product.getWeightUnit())
                .supplierId(product.getSupplier().getId())
                .build();
    }
}