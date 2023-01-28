package jinookk.ourlms.services;

import jinookk.ourlms.dtos.SectionDto;
import jinookk.ourlms.dtos.SectionRequestDto;
import jinookk.ourlms.dtos.SectionUpdateRequestDto;
import jinookk.ourlms.dtos.SectionsDto;
import jinookk.ourlms.models.entities.Progress;
import jinookk.ourlms.models.entities.Section;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.ProgressRepository;
import jinookk.ourlms.repositories.SectionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SectionServiceTest {
    SectionRepository sectionRepository;
    SectionService sectionService;
    ProgressRepository progressRepository;
    AccountRepository accountRepository;

    @BeforeEach
    void setup() {
        accountRepository = mock(AccountRepository.class);
        progressRepository = mock(ProgressRepository.class);
        sectionRepository = mock(SectionRepository.class);
        sectionService = new SectionService(sectionRepository, progressRepository, accountRepository);

        Section section = Section.fake("section");

        given(sectionRepository.findById(1L))
                .willReturn(Optional.of(section));

        given(sectionRepository.findAll())
                .willReturn(List.of(section));

        given(sectionRepository.findAllByCourseId(new CourseId(1L)))
                .willReturn(List.of(section));

        Progress progress = Progress.fake("1강");

//        given(progressRepository.findAllByAccountIdAndSectionId(new AccountId(1L), new SectionId(1L)))
//                .willReturn(List.of(progress));
    }

    @Test
    void list() {
        SectionsDto sectionsDto = sectionService.list();

        assertThat(sectionsDto.getSections()).hasSize(1);
    }

//    @Test
//    void listWithProgress() {
//        SectionsWithProgressDto sectionsWithProgressDto = sectionService.listWithProgress(new CourseId(1L), new AccountId(1L));
//
//        assertThat(sectionsWithProgressDto.getSections()).hasSize(1);
//        assertThat(sectionsWithProgressDto.getSections().get(0).getProgresses()).hasSize(1);
//    }

    @Test
    void create() {
        Section section = Section.fake("title");

        given(sectionRepository.save(any())).willReturn(section);

        SectionDto sectionDto = sectionService.create(new SectionRequestDto(1L, "title", "goal"));

        assertThat(sectionDto.getTitle()).isEqualTo("title");
    }

    @Test
    void update() {
        long sectionId = 1L;

        SectionUpdateRequestDto sectionUpdateRequestDto =
                new SectionUpdateRequestDto("updated", "goal");

        SectionDto sectionDto = sectionService.update(sectionId, sectionUpdateRequestDto);

        assertThat(sectionDto.getTitle()).isEqualTo("updated");
    }

    @Test
    void delete() {
        long sectionId = 1L;

        SectionDto sectionDto = sectionService.delete(sectionId);

        assertThat(sectionDto.getTitle()).isEqualTo(null);
    }
}
