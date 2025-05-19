package com.global.demo.graphql;

import com.global.demo.dto.ProductDTO;
import com.global.demo.entity.Product;
import com.global.demo.service.ProductService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductResolver implements GraphQLQueryResolver {

    private final ProductService productService;

    public List<ProductDTO> products() {
        return productService.getAllProducts();
    }

    public ProductDTO product(Long id) {
        return productService.getProductById(id);
    }
} 