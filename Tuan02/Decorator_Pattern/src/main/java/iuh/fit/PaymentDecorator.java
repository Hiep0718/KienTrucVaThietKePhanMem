package iuh.fit;

public abstract class PaymentDecorator implements Payment {
    protected Payment wrapper; // Đối tượng được bọc bên trong

    public PaymentDecorator(Payment payment) {
        this.wrapper = payment;
    }

    @Override
    public void pay(double amount) {
        // Mặc định là chuyển tiếp yêu cầu cho đối tượng bên trong
        wrapper.pay(amount);
    }
}