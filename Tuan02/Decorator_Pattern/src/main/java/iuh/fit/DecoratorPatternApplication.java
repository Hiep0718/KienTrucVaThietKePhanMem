package iuh.fit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DecoratorPatternApplication {

    public static void main(String[] args) {
        System.out.println("--- TRƯỜNG HỢP 1: Thanh toán bình thường ---");
        Payment simplePayment = new CreditCardPayment();
        simplePayment.pay(100000);

        System.out.println("\n--- TRƯỜNG HỢP 2: PayPal có tính phí xử lý ---");
        // Bọc PayPal bên trong lớp Phí xử lý
        Payment paypalWithFee = new ProcessingFeeDecorator(
                new PayPalPayment(),
                5000 // Phí 5k
        );
        paypalWithFee.pay(100000);

        System.out.println("\n--- TRƯỜNG HỢP 3: Combo phức tạp (Thẻ + Phí + Giảm giá) ---");
        // Chúng ta có thể bọc nhiều lớp lồng nhau như con búp bê Nga
        Payment complexPayment = new DiscountDecorator(
                new ProcessingFeeDecorator(
                        new CreditCardPayment(),
                        2000 // Phí 2k
                ),
                10000 // Giảm giá 10k
        );

        // Luồng chạy: Trừ giảm giá -> Cộng phí -> Thanh toán gốc
        complexPayment.pay(100000);
    }
}

