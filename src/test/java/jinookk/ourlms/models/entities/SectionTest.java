package jinookk.ourlms.models.entities;

import jinookk.ourlms.dtos.SectionRequestDto;
import jinookk.ourlms.dtos.SectionWithProgressDto;
import jinookk.ourlms.models.vos.Content;
import jinookk.ourlms.models.vos.Title;
import jinookk.ourlms.models.vos.ids.CourseId;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SectionTest {
    @Test
    void createdFromDto() {
        SectionRequestDto sectionRequestDto = new SectionRequestDto(1L, "title", "goal");

        Section section = Section.of(sectionRequestDto);

        assertThat(section.courseId()).isEqualTo(new CourseId(1L));
        assertThat(section.title()).isEqualTo(new Title("title"));
        assertThat(section.goal()).isEqualTo(new Content("goal"));
    }

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