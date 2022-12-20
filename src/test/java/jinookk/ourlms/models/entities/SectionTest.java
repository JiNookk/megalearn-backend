package jinookk.ourlms.models.entities;

import jinookk.ourlms.dtos.SectionDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SectionTest {
    @Test
    void convertToDto() {
        Section section = Section.fake("section");

        Progress progress = Progress.fake("1강");

        SectionDto sectionDto = section.toSectionDto(List.of(progress));

        assertThat(sectionDto.getProgresses()).hasSize(1);
        assertThat(sectionDto.getTitle()).isEqualTo("section");
    }

    @Test
    void convertToDtoWithInvalidLectureProducts() {
        Section section = Section.fake("section");

        SectionDto sectionDto = section.toSectionDto(null);

        assertThat(sectionDto.getProgresses()).hasSize(0);
        assertThat(sectionDto.getTitle()).isEqualTo("section");
    }
}