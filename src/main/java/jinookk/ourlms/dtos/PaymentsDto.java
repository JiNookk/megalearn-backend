package jinookk.ourlms.dtos;

import java.util.List;

public class PaymentsDto {
    private List<PaymentDto> payments;

    public PaymentsDto() {
    }

    public PaymentsDto(List<PaymentDto> payments) {
        this.payments = payments;
    }

    public List<PaymentDto> getPayments() {
        return payments;
    }
}
