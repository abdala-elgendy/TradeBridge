package com.global.demo.repository;

import com.global.demo.dto.ProductDTO;
import com.global.demo.dto.TopProductDTO;
import com.global.demo.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Long> {


    /**
     * Finds the top-selling products based on the total sold quantity.
     *
     * @param limit the maximum number of products to return
     * @return a list of top-selling products
     */
    @Query("SELECT new " +
            "com.global.demo.dto.TopProductDTO(p.price, p.stockQuantity, p.rate, p.name, " +
            "p.description) " +
           "FROM Product p ORDER BY p.totalSold DESC")
    List<TopProductDTO> findTopSellingProducts(int limit);

    @Query("""
    SELECT new com.global.demo.dto.ProductDTO(
      p.price,p.stockQuantity,p.name,p.description) 
    FROM Product p
    WHERE p.id IN (
        SELECT f.product.id FROM Favorite f
        WHERE f.customer.id = (
            SELECT c.id FROM Customer c WHERE c.user.email ="alice@example.com"
        )
    )
""")
    List<ProductDTO> getFavorites(@Param("email") String email);

    @Query("    SELECT new com.global.demo.dto.ProductDTO(\n" +
            "      p.price,p.stockQuantity,p.name,p.description) \n" +
            "    FROM Product p ORDER BY p.rate DESC limit :limit")
    List<ProductDTO> findTopRatedProducts(int limit);
}
