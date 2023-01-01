package jinookk.ourlms.models.entities;

import jinookk.ourlms.dtos.SectionWithProgressDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SectionTest {
    @Test
    void convertToDto() {
        Section section = Section.fake("section");

        Progress progress = Progress.fake("1강");

        SectionWithProgressDto sectionWithProgressDto = section.toSectionWithProgressDto(List.of(progress));

        assertThat(sectionWithProgressDto.getProgresses()).hasSize(1);
        assertThat(sectionWithProgressDto.getTitle()).isEqualTo("section");
    }

    @Test
    void convertToDtoWithInvalidLectureProducts() {
        Section section = Section.fake("section");

        SectionWithProgressDto sectionWithProgressDto = section.toSectionWithProgressDto(null);

        assertThat(sectionWithProgressDto.getProgresses()).hasSize(0);
        assertThat(sectionWithProgressDto.getTitle()).isEqualTo("section");
    }
}