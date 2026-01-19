package iuh.fit;

public class VatTaxStrategy implements TaxStrategy {
    @Override
    public double calculateTax(double price) {
        // Thuế VAT là 10% giá trị sản phẩm
        return price * 0.10;
    }
}