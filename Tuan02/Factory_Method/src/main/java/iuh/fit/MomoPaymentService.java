package iuh.fit;

import lombok.*;

@Getter
@Setter

@NoArgsConstructor
public class MomoPaymentService implements PaymentService{
    private String phoneNumber;
    private String momoId;
    public MomoPaymentService(String phoneNumber, String momoId) {
        this.phoneNumber = phoneNumber;
        this.momoId = momoId;
    }
    @Override
    public void pay() {
        System.out.println("Processing MoMo payment for phone number " + phoneNumber + " with MoMo ID " + momoId);
    }

    @Override
    public String toString() {
        return "MomoPaymentService{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", momoId='" + momoId + '\'' +
                '}';
    }
}
