package jinookk.ourlms.exceptions;

import jinookk.ourlms.models.vos.InquiryId;

public class InquiryNotFound extends RuntimeException {
    public InquiryNotFound(InquiryId inquiryId) {
        super("Inquiry is not found by id: " + inquiryId);
    }
}
