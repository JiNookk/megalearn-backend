package jinookk.ourlms.exceptions;

import jinookk.ourlms.models.vos.ids.AccountId;

public class CartNotFound extends RuntimeException {
    public CartNotFound(AccountId accountId) {
        super("Cart is not found by Id: " + accountId);
    }
}
