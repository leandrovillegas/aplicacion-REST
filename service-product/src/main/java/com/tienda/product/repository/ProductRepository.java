package com.tienda.product.repository;

import com.tienda.product.entity.Category;
import com.tienda.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ProductRepository extends JpaRepository <Product, Long> {

    public List<Product> findByCategory(Category category);
}
