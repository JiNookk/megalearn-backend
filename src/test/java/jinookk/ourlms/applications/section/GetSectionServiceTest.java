package jinookk.ourlms.applications.section;

import jinookk.ourlms.dtos.SectionsDto;
import jinookk.ourlms.fixtures.Fixture;
import jinookk.ourlms.models.entities.Section;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.SectionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class GetSectionServiceTest {
    SectionRepository sectionRepository;
    GetSectionService getSectionService;

    @BeforeEach
    void setup() {
        sectionRepository = mock(SectionRepository.class);
        getSectionService = new GetSectionService(sectionRepository);

        Section section = Fixture.section("section");

        given(sectionRepository.findById(1L))
                .willReturn(Optional.of(section));

        given(sectionRepository.findAll())
                .willReturn(List.of(section));

        given(sectionRepository.findAllByCourseId(new CourseId(1L)))
                .willReturn(List.of(section));
    }

    @Test
    void list() {
        SectionsDto sectionsDto = getSectionService.list();

        assertThat(sectionsDto.getSections()).hasSize(1);
    }

//    @Test
//    void listWithProgress() {
//        SectionsWithProgressDto sectionsWithProgressDto = getSectionService.listWithProgress(new CourseId(1L), new AccountId(1L));
//
//        assertThat(sectionsWithProgressDto.getSections()).hasSize(1);
//        assertThat(sectionsWithProgressDto.getSections().get(0).getProgresses()).hasSize(1);
//    }
}
