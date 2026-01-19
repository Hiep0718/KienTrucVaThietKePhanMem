package iuh.fit;

public class Order {
    private OrderState currentState;

    public Order() {
        // Mặc định khi khởi tạo, đơn hàng ở trạng thái Mới tạo
        this.currentState = new NewState();
    }

    public void setState(OrderState state) {
        this.currentState = state;
    }

    public OrderState getState() {
        return currentState;
    }

    // Khi người dùng yêu cầu xử lý, Đơn hàng ủy quyền cho State hiện tại làm việc
    public void process() {
        currentState.handleRequest(this);
    }
}