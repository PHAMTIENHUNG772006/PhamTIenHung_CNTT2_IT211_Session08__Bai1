package com.re.session8.aspect;

import com.re.session8.model.entity.InventoryLog;
import com.re.session8.repository.InventoryLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

@Aspect
@Component
@Order(1)
@RequiredArgsConstructor
public class InventoryAspect {
    private final InventoryLogRepository logRepo;


    @Before("execution(* com.re.session8.service.ProductService.deleteProduct(..)) && args(id)")
    public void checkAdminRole(JoinPoint jp) {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String role = req.getHeader("Role");
        if (!"ADMIN".equals(role)) {
            throw new SecurityException("Chỉ khi tài khoản là Admin mới được xóa");
        }
    }

    @AfterReturning("execution(* com.re.session8.service.ProductService.stockIn(..)) && args(sku, quantity, username)")
    public void logStockIn(String sku, int quantity, String username) {
        InventoryLog log = new InventoryLog();
        log.setTimestamp(LocalDateTime.now());
        log.setUsername(username);
        log.setAction("Nhập kho thành công");
        log.setDetail("Thay đổi số lượng: +" + quantity + " cho sản phẩm có SKU: " + sku);
        logRepo.save(log);
    }

    @AfterReturning("execution(* com.re.session8.service.ProductService.stockOut(..)) && args(sku, quantity, username)")
    public void logStockOut(String sku, int quantity, String username) {
        InventoryLog log = new InventoryLog();
        log.setTimestamp(LocalDateTime.now());
        log.setUsername(username);
        log.setAction("Xuất kho thành công");
        log.setDetail("Thay đổi số lượng: -" + quantity + " cho sản phẩm có SKU: " + sku);
        logRepo.save(log);
    }

    @Around("execution(* com.re.session8.service.ProductService.inspectInventory(..))")
    public Object trackPerformance(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = pjp.proceed();
        long end = System.currentTimeMillis();
        System.out.println("Hàm inspectInventory chạy trong " + (end - start) + " mili-giây");
        return result;
    }

}
