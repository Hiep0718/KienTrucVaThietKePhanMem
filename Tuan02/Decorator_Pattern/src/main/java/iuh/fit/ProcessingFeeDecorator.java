package iuh.fit;

public class ProcessingFeeDecorator extends PaymentDecorator {
    private double fee;

    public ProcessingFeeDecorator(Payment payment, double fee) {
        super(payment);
        this.fee = fee;
    }

    @Override
    public void pay(double amount) {
        double total = amount + fee;
        System.out.println(">> Đã cộng thêm phí xử lý: " + fee + " VND");
        // Gọi phương thức gốc với số tiền mới
        super.pay(total);
    }
}