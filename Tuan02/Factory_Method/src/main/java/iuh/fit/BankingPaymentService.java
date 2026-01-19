package iuh.fit;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class BankingPaymentService implements PaymentService{
    private String bankName;
    private String accountNumber;

    public BankingPaymentService(String bankName, String accountNumber) {
        this.bankName = bankName;
        this.accountNumber = accountNumber;
    }

    @Override
    public void pay() {
        System.out.println("Processing banking payment through " + bankName + " with account number " + accountNumber);
    }

    @Override
    public String toString() {
        return "BankingPaymentService{" +
                "bankName='" + bankName + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                '}';
    }
}
