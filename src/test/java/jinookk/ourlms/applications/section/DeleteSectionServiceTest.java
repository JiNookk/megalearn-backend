package jinookk.ourlms.applications.section;

import jinookk.ourlms.dtos.SectionDto;
import jinookk.ourlms.fixtures.Fixture;
import jinookk.ourlms.models.entities.Section;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.models.vos.status.Status;
import jinookk.ourlms.repositories.SectionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class DeleteSectionServiceTest {
    SectionRepository sectionRepository;
    DeleteSectionService deleteSectionService;

    @BeforeEach
    void setup() {
        sectionRepository = mock(SectionRepository.class);
        deleteSectionService = new DeleteSectionService(sectionRepository);

        Section section = Fixture.section("section");

        given(sectionRepository.findById(1L))
                .willReturn(Optional.of(section));

        given(sectionRepository.findAll())
                .willReturn(List.of(section));

        given(sectionRepository.findAllByCourseId(new CourseId(1L)))
                .willReturn(List.of(section));
    }

    @Test
    void delete() {
        long sectionId = 1L;

        SectionDto sectionDto = deleteSectionService.delete(sectionId);

        assertThat(sectionDto.getStatus()).isEqualTo(Status.DELETED);
    }
}
