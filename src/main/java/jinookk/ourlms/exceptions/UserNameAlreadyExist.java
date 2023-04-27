package jinookk.ourlms.exceptions;

import jinookk.ourlms.exceptions.RegisterFailed;

public class UserNameAlreadyExist extends RegisterFailed {
    public UserNameAlreadyExist(String userName) {
        super("userName(" + userName + ") already exists!");
    }
}
