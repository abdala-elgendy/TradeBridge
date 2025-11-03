// src/main/java/com/global/demo/mapper/ProductMapper.java
package com.global.demo.mapper;

import com.global.demo.dto.ProductDTO;
import com.global.demo.dto.TopProductDTO;
import com.global.demo.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "supplier.id", target = "supplierId")
    ProductDTO toProductDTO(Product product);

    @Mapping(source = "supplierId", target = "supplier.id")
    Product toProduct(ProductDTO productDTO);

    List<ProductDTO> toProductDTOList(List<Product> all);

  //  List<TopProductDTO> findTopSellingProducts(int limit);
}
