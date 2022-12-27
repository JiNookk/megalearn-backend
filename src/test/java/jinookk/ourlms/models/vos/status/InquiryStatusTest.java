package jinookk.ourlms.models.vos.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InquiryStatusTest {
    InquiryStatus inquiryStatus;

    @BeforeEach
    void setup() {
        inquiryStatus = new InquiryStatus(Status.CREATED);
    }

    @Test
    void reply() {
        assertThat(inquiryStatus.replied()).isEqualTo("processing");

        inquiryStatus.reply();

        assertThat(inquiryStatus.replied()).isEqualTo("completed");
    }

    @Test
    void solve() {
        assertThat(inquiryStatus.solved()).isEqualTo("processing");

        inquiryStatus.solve();

        assertThat(inquiryStatus.solved()).isEqualTo("completed");
    }

    @Test
    void filterWithAll() {
        assertThat(inquiryStatus.filter("all")).isTrue();
    }

    @Test
    void filterWithUnReplired() {
        assertThat(inquiryStatus.filter("unreplied")).isTrue();
    }

    @Test
    void filterWithReplied() {
        assertThat(inquiryStatus.filter("replied")).isFalse();

        inquiryStatus.reply();

        assertThat(inquiryStatus.filter("replied")).isTrue();
    }

    @Test
    void filterWithUnsolved() {
        assertThat(inquiryStatus.filter("unsolved")).isTrue();
    }

    @Test
    void filterWithSolved() {
        assertThat(inquiryStatus.filter("solved")).isFalse();

        inquiryStatus.solve();

        assertThat(inquiryStatus.filter("solved")).isTrue();
    }
}
