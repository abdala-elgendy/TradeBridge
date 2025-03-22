// src/main/java/com/global/demo/service/ProductService.java
package com.global.demo.service;

import com.global.demo.dto.ProductDTO;
import com.global.demo.entity.Product;
import com.global.demo.mapper.ProductMapper;
import com.global.demo.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepository;

    @Autowired
    private ProductMapper productMapper;

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
}