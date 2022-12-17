package jinookk.ourlms.exceptions;

public class CommentNotFound extends RuntimeException {
    public CommentNotFound(Long inquiryId) {
        super("Comment is not found by id: " + inquiryId);
    }
}
