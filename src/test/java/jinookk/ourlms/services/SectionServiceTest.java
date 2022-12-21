package jinookk.ourlms.services;

import jinookk.ourlms.dtos.SectionsDto;
import jinookk.ourlms.models.entities.Progress;
import jinookk.ourlms.models.entities.Section;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.models.vos.ids.SectionId;
import jinookk.ourlms.repositories.ProgressRepository;
import jinookk.ourlms.repositories.SectionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SectionServiceTest {
    SectionRepository sectionRepository;
    SectionService sectionService;
    ProgressRepository progressRepository;

    @BeforeEach
    void setup() {
        progressRepository = mock(ProgressRepository.class);
        sectionRepository = mock(SectionRepository.class);
        sectionService = new SectionService(sectionRepository, progressRepository);

        Section section = Section.fake("section");

        given(sectionRepository.findAllByCourseId(new CourseId(1L)))
                .willReturn(List.of(section));

        Progress progress = Progress.fake("1강");

        given(progressRepository.findAllByAccountIdAndSectionId(new AccountId(1L), new SectionId(1L)))
                .willReturn(List.of(progress));
    }

    @Test
    void list() {
        SectionsDto sectionsDto = sectionService.list(new CourseId(1L), new AccountId(1L));

        assertThat(sectionsDto.getSections()).hasSize(1);
        assertThat(sectionsDto.getSections().get(0).getProgresses()).hasSize(1);
    }
}
