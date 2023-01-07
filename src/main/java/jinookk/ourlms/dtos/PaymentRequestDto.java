package jinookk.ourlms.dtos;

public class PaymentRequestDto {
    private String pgToken;

    public PaymentRequestDto() {
    }

    public PaymentRequestDto(String pgToken) {
        this.pgToken = pgToken;
    }

    public String getPgToken() {
        return pgToken;
    }
}
