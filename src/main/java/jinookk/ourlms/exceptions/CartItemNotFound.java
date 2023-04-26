package jinookk.ourlms.exceptions;

public class CartItemNotFound extends RuntimeException {
    public CartItemNotFound(String courseId) {
        super("cart item is not found by Id: " + courseId);
    }
}
