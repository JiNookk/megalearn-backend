package jinookk.ourlms.exceptions;

public class KakaoApprovalFail extends RuntimeException {
    public KakaoApprovalFail(Exception exception) {
        super(exception);
    }
}
