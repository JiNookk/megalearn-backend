package jinookk.ourlms.applications.section;

import jinookk.ourlms.dtos.SectionDto;
import jinookk.ourlms.dtos.SectionRequestDto;
import jinookk.ourlms.models.entities.Section;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.repositories.SectionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class CreateSectionServiceTest {
    SectionRepository sectionRepository;
    CreateSectionService createSectionService;

    @BeforeEach
    void setup() {
        sectionRepository = mock(SectionRepository.class);
        createSectionService = new CreateSectionService(sectionRepository);

        Section section = Section.fake("section");

        given(sectionRepository.findById(1L))
                .willReturn(Optional.of(section));

        given(sectionRepository.findAll())
                .willReturn(List.of(section));

        given(sectionRepository.findAllByCourseId(new CourseId(1L)))
                .willReturn(List.of(section));
    }

    @Test
    void create() {
        Section section = Section.fake("title");

        given(sectionRepository.save(any())).willReturn(section);

        SectionDto sectionDto = createSectionService.create(new SectionRequestDto(1L, "title", "goal"));

        assertThat(sectionDto.getTitle()).isEqualTo("title");
    }
}
