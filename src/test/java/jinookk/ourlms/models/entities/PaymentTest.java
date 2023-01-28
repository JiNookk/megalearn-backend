package jinookk.ourlms.models.entities;

import jinookk.ourlms.models.exceptions.InvalidPaymentInformation;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PaymentTest {
    private Cart cart;

    @BeforeEach
    void setup() {
        cart = Cart.fake(new AccountId(1L));

        cart = cart.addItem(1L);
    }

    @Test
    void create() {
        Course course = Course.fake("fake");
        Account account = Account.fake("tester");

        Payment payment = Payment.of(account, course);

        assertThat(payment.purchaser()).isEqualTo(new Name("tester"));
    }

    @Test
    void createWithInvalidInformation() {
        assertThrows(InvalidPaymentInformation.class, () -> {
            Payment.of(Account.fake("tester"), Course.fake(null));
        });

        assertThrows(InvalidPaymentInformation.class, () -> {
            Payment.of(Account.fake(null), Course.fake("fake"));
        });

        assertThrows(InvalidPaymentInformation.class, () -> {
            Payment.of(Account.fake("tester"), null);
        });

        assertThrows(InvalidPaymentInformation.class, () -> {
            Payment.of(null, Course.fake("fake"));
        });
    }

    @Test
    void createList() {
        Course course = Course.fake("fake");
        Account account = Account.fake("tester");

        List<Course> courses = List.of(course);

        List<Payment> payments = Payment.listOf(courses, account, cart);

        assertThat(payments).hasSize(1);
    }

    @Test
    void createListWithInvalidValue() {
        Course course = Course.fake("fake");
        Account account = Account.fake("tester");

        assertThrows(InvalidPaymentInformation.class, () -> {
            Payment.listOf(List.of(), account, cart);
        });

        assertThrows(InvalidPaymentInformation.class, () -> {
            Payment.listOf(null, account, cart);
        });

        assertThrows(InvalidPaymentInformation.class, () -> {
            Payment.listOf(List.of(course), null, cart);
        });
    }
}
