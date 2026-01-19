package iuh.fit;

public class ProcessingState implements OrderState {
    @Override
    public void handleRequest(Order order) {
        System.out.println("--- Trạng thái: Đang xử lý ---");
        System.out.println("Hành động: Đóng gói và vận chuyển...");

        // Logic chuyển trạng thái: Sau khi giao cho shipper -> Chuyển sang Đã giao
        order.setState(new DeliveredState());
    }
}
