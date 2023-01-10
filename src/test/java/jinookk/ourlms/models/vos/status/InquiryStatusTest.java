package jinookk.ourlms.models.vos.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InquiryStatusTest {
    InquiryStatus inquiryStatus;

    @BeforeEach
    void setup() {
        inquiryStatus = new InquiryStatus();
    }

    @Test
    void reply() {
        assertThat(inquiryStatus.replied()).isEqualTo("processing");

        InquiryStatus replied = inquiryStatus.reply();

        assertThat(replied.replied()).isEqualTo("completed");
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

        InquiryStatus solved = inquiryStatus.toggleSolved();

        assertThat(solved.filter("solved")).isTrue();
    }

    @Test
    void toggleSolved() {
        InquiryStatus inquiryStatus = new InquiryStatus();

        assertThat(inquiryStatus.solved()).isEqualTo(InquiryStatus.PROCESSING);

        InquiryStatus toggled = inquiryStatus.toggleSolved();

        assertThat(toggled.solved()).isEqualTo(InquiryStatus.COMPLETED);

        InquiryStatus doubleToggled = toggled.toggleSolved();

        assertThat(doubleToggled.solved()).isEqualTo(InquiryStatus.PROCESSING);
    }
}
