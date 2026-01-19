package iuh.fit;

public class ConsumptionTaxStrategy implements TaxStrategy {
    @Override
    public double calculateTax(double price) {
        // Thuế tiêu thụ là 5%
        return price * 0.05;
    }
}
