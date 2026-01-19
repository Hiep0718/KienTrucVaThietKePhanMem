package iuh.fit;

public interface TaxStrategy {
    // Input là giá gốc, Output là tiền thuế tính được
    double calculateTax(double price);
}
