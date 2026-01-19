package iuh.fit;

public class DiscountDecorator extends PaymentDecorator {
    private double discountAmount;

    public DiscountDecorator(Payment payment, double discountAmount) {
        super(payment);
        this.discountAmount = discountAmount;
    }

    @Override
    public void pay(double amount) {
        double total = amount - discountAmount;
        if (total < 0) total = 0; // Đảm bảo không bị âm tiền

        System.out.println(">> Đã áp dụng mã giảm giá: -" + discountAmount + " VND");
        super.pay(total);
    }
}
