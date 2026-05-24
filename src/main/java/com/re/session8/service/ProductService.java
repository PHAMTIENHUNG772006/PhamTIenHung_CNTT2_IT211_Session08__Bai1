package com.re.session8.service;

import com.re.session8.model.entity.Product;

import java.util.List;

public interface ProductService {
    Product saveProduct(Product product);
    Product getProductById(Long id);
    void stockIn(String sku, int quantity, String username);
    void stockOut(String sku, int quantity, String username);
    double inspectInventory();
    void deleteProduct(Long id);
    List<Product> getAllProducts();
}
