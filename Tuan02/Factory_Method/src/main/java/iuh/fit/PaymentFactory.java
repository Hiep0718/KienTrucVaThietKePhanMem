package iuh.fit;

import org.springframework.stereotype.Service;

@Service
public class PaymentFactory {
    public PaymentService getPaymentMethod(String type) {
        if ("MOMO".equals(type)) {
            // Giả lập dữ liệu
            return new MomoPaymentService("0909123456", "MM_9999");
        } else if ("BANK".equals(type)) {
            return new BankingPaymentService("Vietcombank", "9988776655");
        }
        // Mặc định là COD
        return new CodPaymentService("Nguyen Van A", "TP.HCM");
    }
}