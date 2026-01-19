package iuh.fit;

public class CancelledState implements OrderState {
    @Override
    public void handleRequest(Order order) {
        System.out.println("--- Trạng thái: Đã hủy ---");
        System.out.println("Hành động: Hủy đơn hàng và hoàn tiền cho khách.");
    }
}
