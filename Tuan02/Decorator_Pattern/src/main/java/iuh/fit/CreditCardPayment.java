package iuh.fit;

public class CreditCardPayment implements Payment {
    @Override
    public void pay(double amount) {
        System.out.println("Đang thực hiện thanh toán: " + amount + " VND qua Thẻ Tín Dụng.");
    }
}
