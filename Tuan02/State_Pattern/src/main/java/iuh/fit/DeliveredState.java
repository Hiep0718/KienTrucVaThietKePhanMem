package iuh.fit;

public class DeliveredState implements OrderState {
    @Override
    public void handleRequest(Order order) {
        System.out.println("--- Trạng thái: Đã giao ---");
        System.out.println("Hành động: Cập nhật đơn hàng thành công. Hoàn tất!");
        // Đây là trạng thái cuối, không chuyển đi đâu nữa
    }
}