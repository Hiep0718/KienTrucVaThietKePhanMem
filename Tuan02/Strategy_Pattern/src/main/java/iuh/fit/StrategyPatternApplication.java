package iuh.fit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class StrategyPatternApplication {

    public static void main(String[] args) {
        // 1. Sản phẩm bình thường áp dụng VAT (10%)
        Product laptop = new Product("Laptop Dell", 20000000, new VatTaxStrategy());
        laptop.printInfo();

        // 2. Sản phẩm đặc thù áp dụng Thuế Tiêu Thụ (5%)
        Product book = new Product("Sách Giáo Khoa", 50000, new ConsumptionTaxStrategy());
        book.printInfo();

        // 3. Sản phẩm xa xỉ áp dụng Thuế Luxury (20%)
        Product car = new Product("Siêu xe Porsche", 5000000000L, new LuxuryTaxStrategy());
        car.printInfo();

        // --- Demo tính linh hoạt (Thay đổi thuật toán tại Runtime) ---
        System.out.println(">> Cập nhật chính sách thuế mới cho Laptop...");
        // Giả sử Laptop bị đánh thuế xa xỉ thay vì VAT
        laptop.setTaxStrategy(new LuxuryTaxStrategy());
        laptop.printInfo();
    }

}
