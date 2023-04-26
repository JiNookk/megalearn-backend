package jinookk.ourlms.exceptions;

import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.AccountId;

public class AccountNotFound extends RuntimeException {
    public AccountNotFound(AccountId accountId) {
        super("Account is not found by id: " + accountId);
    }

    public AccountNotFound(UserName userName) {
        super("Account is not found by userName: " + userName);
    }
}
