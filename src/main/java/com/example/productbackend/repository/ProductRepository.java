package com.example.productbackend.repository;

import com.example.productbackend.model.Product;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByTitleContainingIgnoreCase(String title);


    List<Product> findByBrandContainingIgnoreCase(String brand);


    List<Product> findByCategory(String category);


    List<Product> findByTitleContainingIgnoreCaseAndCategory(
            String title, String category);


    List<Product> findByBrandContainingIgnoreCaseAndCategory(
            String brand, String category);
}
