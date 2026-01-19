package iuh.fit;


public class StatePatternDemo {
    public static void main(String[] args) {
        // 1. Tạo một đơn hàng mới
        Order myOrder = new Order();

        // 2. Quy trình bình thường (Happy Case)
        // Đơn hàng đang ở New -> gọi process() sẽ kiểm tra thông tin
        myOrder.process();

        // Lúc này State đã tự chuyển sang Processing bên trong hàm handleRequest cũ
        // Gọi tiếp process() sẽ đóng gói
        myOrder.process();

        // Lúc này State đã chuyển sang Delivered
        // Gọi tiếp process() sẽ hoàn tất
        myOrder.process();

        System.out.println("\n--------------------------\n");

        // 3. Demo trường hợp Hủy (Giả sử đang xử lý mà khách gọi điện hủy)
        Order order2 = new Order();
        order2.process(); // Kiểm tra thông tin -> Sang Processing

        // Cưỡng chế chuyển sang trạng thái Hủy
        System.out.println("-> Khách hàng yêu cầu hủy đơn!");
        order2.setState(new CancelledState());

        order2.process(); // Xử lý hoàn tiền
    }
}
