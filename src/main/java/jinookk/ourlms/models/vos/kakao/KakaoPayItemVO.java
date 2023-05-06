package jinookk.ourlms.models.vos.kakao;

import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Cart;
import jinookk.ourlms.models.entities.Course;

import java.util.List;

public class KakaoPayItemVO {
    private Account account;
    private List<Course> courses;
    private Cart cart;

    public KakaoPayItemVO() {
    }

    public KakaoPayItemVO(Account account, List<Course> courses, Cart cart) {
        this.account = account;
        this.courses = courses;
        this.cart = cart;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public Account getAccount() {
        return account;
    }

    public Cart getCart() {
        return cart;
    }
}
