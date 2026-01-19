package iuh.fit;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentFactory paymentFactory;

    // Constructor Injection (Thay vì @Autowired)
    public PaymentController(PaymentFactory paymentFactory) {
        this.paymentFactory = paymentFactory;
    }

    @GetMapping
    public String processPayment(@RequestParam(defaultValue = "COD") String type) {
        // 1. Gọi Factory để lấy đối tượng xử lý tương ứng
        PaymentService service = paymentFactory.getPaymentMethod(type);

        // 2. Thực thi hành động thanh toán
        service.pay(); // Dòng này sẽ in log ra Console của IntelliJ

        // 3. Trả về kết quả cho trình duyệt
        return "Đã xử lý thanh toán bằng phương thức: " + type.toUpperCase() + ". <br>"
                + "Chi tiết đối tượng: " + service.toString();
    }
}