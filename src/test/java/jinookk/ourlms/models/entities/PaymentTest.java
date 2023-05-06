package jinookk.ourlms.models.entities;

import jinookk.ourlms.fixtures.Fixture;
import jinookk.ourlms.exceptions.InvalidPaymentInformation;
import jinookk.ourlms.models.enums.PaymentStatus;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.models.vos.ids.AccountId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PaymentTest extends Fixture {
    private Cart cart;

    @BeforeEach
    void setup() {
        cart = Fixture.cart(new AccountId(1L));

        cart = cart.addItem(1L);
    }

    @Test
    void create() {
        Course course = Fixture.course("fake");
        Account account = Fixture.account("tester");

        Payment payment = Payment.of(account, course, PaymentStatus.SUCCESS);

        assertThat(payment.purchaser()).isEqualTo(new Name("tester", false));
        assertThat(payment.id()).isEqualTo(null);
        assertThat(payment.accountId()).isEqualTo(new AccountId(1L));
    }

    @Test
    void createWithInvalidInformation() {
        assertThrows(InvalidPaymentInformation.class, () -> {
            Payment.of(Fixture.account("tester"), Fixture.course(null), PaymentStatus.SUCCESS);
        });

        assertThrows(InvalidPaymentInformation.class, () -> {
            Payment.of(Fixture.account(null), Fixture.course("fake"), PaymentStatus.SUCCESS);
        });

        assertThrows(InvalidPaymentInformation.class, () -> {
            Payment.of(Fixture.account("tester"), null, PaymentStatus.SUCCESS);
        });

        assertThrows(InvalidPaymentInformation.class, () -> {
            Payment.of(null, Fixture.course("fake"), PaymentStatus.SUCCESS);
        });
    }

    @Test
    void createList() {
        Course course = Fixture.course("fake");
        Account account = Fixture.account("tester");

        List<Course> courses = List.of(course);

        List<Payment> payments = Payment.listOf(courses, account, cart, PaymentStatus.FAILED);

        assertThat(payments).hasSize(1);
    }

    @Test
    void createListWithInvalidValue() {
        Course course = Fixture.course("fake");
        Account account = Fixture.account("tester");

        assertThrows(InvalidPaymentInformation.class, () -> {
            Payment.listOf(List.of(), account, cart, PaymentStatus.FAILED);
        });

        assertThrows(InvalidPaymentInformation.class, () -> {
            Payment.listOf(null, account, cart, PaymentStatus.FAILED);
        });

        assertThrows(InvalidPaymentInformation.class, () -> {
            Payment.listOf(List.of(course), null, cart, PaymentStatus.FAILED);
        });
    }
}
