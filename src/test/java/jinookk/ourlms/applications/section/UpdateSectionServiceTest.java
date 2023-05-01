package jinookk.ourlms.applications.section;

import jinookk.ourlms.dtos.SectionDto;
import jinookk.ourlms.dtos.SectionUpdateRequestDto;
import jinookk.ourlms.models.entities.Section;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.repositories.SectionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class UpdateSectionServiceTest {
    SectionRepository sectionRepository;
    UpdateSectionService updateSectionService;

    @BeforeEach
    void setup() {
        sectionRepository = mock(SectionRepository.class);
        updateSectionService = new UpdateSectionService(sectionRepository);

        Section section = Section.fake("section");

        given(sectionRepository.findById(1L))
                .willReturn(Optional.of(section));

        given(sectionRepository.findAll())
                .willReturn(List.of(section));

        given(sectionRepository.findAllByCourseId(new CourseId(1L)))
                .willReturn(List.of(section));
    }

    @Test
    void update() {
        long sectionId = 1L;

        SectionUpdateRequestDto sectionUpdateRequestDto =
                new SectionUpdateRequestDto("updated", "goal");

        SectionDto sectionDto = updateSectionService.update(sectionId, sectionUpdateRequestDto);

        assertThat(sectionDto.getTitle()).isEqualTo("updated");
    }
}
