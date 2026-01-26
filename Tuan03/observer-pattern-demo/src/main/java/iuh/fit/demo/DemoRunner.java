package iuh.fit.demo;

import iuh.fit.pattern.observer.Investor;
import iuh.fit.pattern.subject.Stock;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DemoRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=".repeat(60));
        System.out.println("🎯 DEMO OBSERVER PATTERN - HỆ THỐNG THÔNG BÁO GIÁ CỔ PHIẾU");
        System.out.println("=".repeat(60));

        // Bước 1: Tạo các cổ phiếu (Subject)
        Stock vietcombank = new Stock("VCB", 85000);
        Stock vingroup = new Stock("VIC", 45000);

        // Bước 2: Tạo các nhà đầu tư (Observer)
        Investor anhNguyen = new Investor("Anh Nguyễn");
        Investor chiTran = new Investor("Chị Trần");
        Investor anhLe = new Investor("Anh Lê");

        System.out.println("\n" + "=".repeat(60));
        System.out.println("📝 ĐĂNG KÝ THEO DÕI");
        System.out.println("=".repeat(60));

        // Bước 3: Đăng ký theo dõi
        vietcombank.registerObserver(anhNguyen);
        vietcombank.registerObserver(chiTran);

        vingroup.registerObserver(chiTran);
        vingroup.registerObserver(anhLe);

        System.out.println("\n" + "=".repeat(60));
        System.out.println("📊 THAY ĐỔI GIÁ CỔ PHIẾU");
        System.out.println("=".repeat(60));

        // Bước 4: Thay đổi giá - tự động thông báo
        vietcombank.setPrice(87000);  // Anh Nguyễn và Chị Trần nhận thông báo

        Thread.sleep(1000);

        vingroup.setPrice(47500);     // Chị Trần và Anh Lê nhận thông báo

        Thread.sleep(1000);

        System.out.println("=".repeat(60));
        System.out.println("🔄 HỦY ĐĂNG KÝ VÀ THAY ĐỔI TIẾP");
        System.out.println("=".repeat(60));

        // Bước 5: Hủy đăng ký
        vietcombank.removeObserver(anhNguyen);

        Thread.sleep(1000);

        // Bước 6: Thay đổi giá lần nữa
        vietcombank.setPrice(88500);  // Chỉ Chị Trần nhận thông báo (Anh Nguyễn đã hủy)

        System.out.println("=".repeat(60));
        System.out.println("✅ DEMO HOÀN TẤT!");
        System.out.println("=".repeat(60));
    }
}