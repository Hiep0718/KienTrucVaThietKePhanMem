package iuh.fit;

public class LuxuryTaxStrategy implements TaxStrategy {
    @Override
    public double calculateTax(double price) {
        // Thuế đặc biệt cho hàng xa xỉ là 20%
        return price * 0.20;
    }
}
