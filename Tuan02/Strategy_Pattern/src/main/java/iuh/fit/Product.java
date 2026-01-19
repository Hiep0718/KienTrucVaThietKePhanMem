package iuh.fit;

public class Product {
    private String name;
    private double price;
    private TaxStrategy taxStrategy; // Chứa tham chiếu đến chiến lược tính thuế

    public Product(String name, double price, TaxStrategy taxStrategy) {
        this.name = name;
        this.price = price;
        this.taxStrategy = taxStrategy;
    }

    // Setter này cho phép thay đổi loại thuế ngay khi đang chạy (Runtime)
    public void setTaxStrategy(TaxStrategy taxStrategy) {
        this.taxStrategy = taxStrategy;
    }

    public double getFinalPrice() {
        double tax = taxStrategy.calculateTax(price);
        return price + tax;
    }

    public void printInfo() {
        double tax = taxStrategy.calculateTax(price);
        System.out.println("Sản phẩm: " + name);
        System.out.println(" - Giá gốc: " + price);
        System.out.println(" - Tiền thuế: " + tax);
        System.out.println(" -> TỔNG TIỀN: " + (price + tax));
        System.out.println("------------------------------");
    }
}
