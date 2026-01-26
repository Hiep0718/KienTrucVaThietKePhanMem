package iuh.fit.service;

import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;
import java.util.Random;
import java.time.LocalDateTime;

@Service
public class PaymentService {

    // @Retry: Áp dụng config 'paymentApi' từ file yml
    // fallbackMethod: Nếu thử 3 lần vẫn lỗi thì chạy hàm 'paymentFallback'
    @Retry(name = "paymentApi", fallbackMethod = "paymentFallback")
    public String processPayment(String orderId) {
        System.out.println(LocalDateTime.now() + " --- Đang gọi cổng thanh toán cho đơn: " + orderId + " ---");

        // Giả lập lỗi ngẫu nhiên (50% lỗi) để bạn thấy nó retry
        if (new Random().nextBoolean()) {
            System.err.println("!!! Lỗi kết nối mạng (Simulated Error) !!!");
            throw new RuntimeException("Payment Gateway Timeout");
        }

        return "Thanh toán THÀNH CÔNG cho đơn " + orderId;
    }

    // Hàm dự phòng (Fallback) chạy khi hết 3 lần retry
    public String paymentFallback(String orderId, Throwable t) {
        System.out.println(">>> Kích hoạt Circuit Breaker: Chuyển sang xử lý Offline.");
        return "Hệ thống bận. Đơn hàng " + orderId + " đã được lưu chờ xử lý sau.";
    }
}