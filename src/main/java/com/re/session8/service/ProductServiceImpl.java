package com.re.session8.service;

import com.re.session8.model.entity.Product;
import com.re.session8.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public void stockIn(String sku, int quantity, String username) {
        productRepository.stockIn(sku, quantity);
    }

    public void stockOut(String sku, int quantity, String username) {
        int updated = productRepository.stockOut(sku, quantity);
        if (updated == 0) {
            throw new RuntimeException(" Không thấy sp nào được câp nhật");
        }
    }

    public double inspectInventory() {
        return productRepository.findAll()
                .stream()
                .mapToDouble(p -> p.getQuantity() * p.getPrice())
                .sum();
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
