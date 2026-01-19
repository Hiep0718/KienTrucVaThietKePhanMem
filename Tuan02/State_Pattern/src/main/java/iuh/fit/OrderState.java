package iuh.fit;
public interface OrderState {
    // Hành động xử lý chính của đơn hàng
    void handleRequest(Order order);
}