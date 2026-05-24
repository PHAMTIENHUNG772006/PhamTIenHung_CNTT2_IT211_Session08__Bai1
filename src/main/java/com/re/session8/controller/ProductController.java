package com.re.session8.controller;

import com.re.session8.aspect.InventoryAspect;
import com.re.session8.model.dto.request.StockRequest;
import com.re.session8.model.entity.InventoryLog;
import com.re.session8.model.entity.Product;
import com.re.session8.repository.InventoryLogRepository;
import com.re.session8.service.ProductService;
import com.re.session8.service.ProductServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final InventoryLogRepository logRepo;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/log")
    public ResponseEntity<List<InventoryLog>> getAllProductsLog() {
        return ResponseEntity.ok(logRepo.findAll());
    }

    @PostMapping
    public ResponseEntity<Product> saveProduct(@Valid @RequestBody Product product) {
        return ResponseEntity.ok(productService.saveProduct(product));
    }


    @PostMapping("/stock-in")
    public ResponseEntity<String> stockIn(
            @RequestHeader("User") String username,
            @RequestHeader("Role") String role,
            @Valid @RequestBody StockRequest request) {
        productService.stockIn(request.getSku(), request.getQuantity(), username);
        return ResponseEntity.ok("Nhập kho thành công");
    }

    @PostMapping("/stock-out")
    public ResponseEntity<String> stockOut(
            @RequestHeader("User") String username,
            @RequestHeader("Role") String role,
            @Valid @RequestBody StockRequest request) {
        productService.stockOut(request.getSku(), request.getQuantity(), username);
        return ResponseEntity.ok("Xuất kho thành công");
    }

    @GetMapping("/inspect")
    public ResponseEntity<Double> inspectInventory(
            @RequestHeader("User") String username,
            @RequestHeader("Role") String role) {
        double totalValue = productService.inspectInventory();
        return ResponseEntity.ok(totalValue);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(
            @RequestHeader("User") String username,
            @RequestHeader("Role") String role,
            @PathVariable Long id) {

        Product product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        productService.deleteProduct(id);
        return ResponseEntity.ok("Xóa sản phẩm thành công");
    }
}

