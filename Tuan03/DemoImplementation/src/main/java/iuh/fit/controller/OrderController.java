package iuh.fit.controller;

import iuh.fit.service.PaymentService;
import iuh.fit.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final PaymentService paymentService;

    @PostMapping("/checkout") // Dùng GET cho dễ test trên trình duyệt cũng được, nhưng đúng chuẩn là POST
    public String checkout(@RequestParam String orderId, @RequestParam String creditCard) {

        // 1. DEMO SECURITY: Mã hóa
        String encryptedCard = SecurityUtils.encrypt(creditCard);
        System.out.println("\n[SECURITY] Dữ liệu gốc: " + creditCard);
        System.out.println("[SECURITY] Dữ liệu đã mã hóa lưu DB: " + encryptedCard);

        // 2. DEMO FAULT TOLERANCE
        return paymentService.processPayment(orderId);
    }
}