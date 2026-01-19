package iuh.fit;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
public class CodPaymentService implements PaymentService{
    private String recipientName;
    private String address;

    public CodPaymentService(String recipientName, String address) {
        this.recipientName = recipientName;
        this.address = address;
    }
    @Override
    public void pay() {
        System.out.println("Processing COD payment to " + recipientName + " at " + address);
    }
    @Override
    public String toString() {
        return "CodPaymentService{" +
                "recipientName='" + recipientName + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
