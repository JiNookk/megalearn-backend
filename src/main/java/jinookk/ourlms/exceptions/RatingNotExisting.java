package jinookk.ourlms.exceptions;

import jinookk.ourlms.models.vos.ids.AccountId;

public class RatingNotExisting extends RuntimeException {
    public RatingNotExisting(AccountId accountId) {
        super("rating is not existing with accountId: " + accountId);
    }
}
