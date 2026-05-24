package com.re.session8.repository;

import com.re.session8.model.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.quantity = p.quantity + :quantity WHERE p.sku = :sku")
    void stockIn(@Param("sku") String sku, @Param("quantity") int quantity);

    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.quantity = p.quantity - :quantity WHERE p.sku = :sku AND p.quantity >= :quantity")
    int stockOut(@Param("sku") String sku, @Param("quantity") int quantity);
}
