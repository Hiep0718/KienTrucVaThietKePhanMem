package iuh.fit;

public class NewState implements OrderState {
    @Override
    public void handleRequest(Order order) {
        System.out.println("--- Trạng thái: Mới tạo ---");
        System.out.println("Hành động: Kiểm tra thông tin đơn hàng...");

        // Logic chuyển trạng thái: Sau khi kiểm tra xong -> Chuyển sang Đang xử lý
        order.setState(new ProcessingState());
    }
}