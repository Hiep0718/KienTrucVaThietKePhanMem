package iuh.fit;

public class PayPalPayment implements Payment {
    @Override
    public void pay(double amount) {
        System.out.println("Đang thực hiện thanh toán: " + amount + " VND qua PayPal.");
    }
}
