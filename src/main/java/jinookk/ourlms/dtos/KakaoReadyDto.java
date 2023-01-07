package jinookk.ourlms.dtos;

public class KakaoReadyDto {
    private String paymentUrl;

    public KakaoReadyDto() {
    }

    public KakaoReadyDto(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }

    public String getPaymentUrl() {
        return paymentUrl;
    }
}
