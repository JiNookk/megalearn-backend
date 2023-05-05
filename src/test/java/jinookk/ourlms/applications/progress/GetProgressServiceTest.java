package jinookk.ourlms.applications.progress;

import jinookk.ourlms.dtos.ProgressDto;
import jinookk.ourlms.dtos.ProgressesDto;
import jinookk.ourlms.fixtures.Fixture;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Progress;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.models.vos.ids.LectureId;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.ProgressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class GetProgressServiceTest extends Fixture {
    ProgressRepository progressRepository;
    GetProgressService getProgressService;
    AccountRepository accountRepository;

    @BeforeEach
    void setup() {
        accountRepository = mock(AccountRepository.class);
        progressRepository = mock(ProgressRepository.class);
        getProgressService = new GetProgressService(progressRepository, accountRepository);

        Progress progress = Fixture.progress("1강");

        given(progressRepository.findById(1L))
                .willReturn(Optional.of(progress));

        given(progressRepository.findByLectureIdAndAccountId(new LectureId(1L), new AccountId(1L)))
                .willReturn(Optional.of(progress));

        given(progressRepository.findAllByCourseId(new CourseId(1L)))
                .willReturn(List.of(progress));

        given(progressRepository.findAllByAccountId(any(), any()))
                .willReturn(List.of(progress));

        given(progressRepository.findAll((Specification<Progress>) any(), (Sort) any()))
                .willReturn(List.of(progress));

        Account account = Fixture.account("userName@email.com");
        given(accountRepository.findByUserName(any())).willReturn(Optional.of(account));
    }

    @Test
    void detail() {
        ProgressDto progressDto = getProgressService.detail(new LectureId(1L), new UserName("userName@email.com"));

        assertThat(progressDto).isNotNull();
    }

    @Test
    void list() {
        ProgressesDto progressesDto = getProgressService.list(new UserName("userName@email.com"), LocalDateTime.now().toString());

        assertThat(progressesDto.getProgresses()).hasSize(1);
    }

    @Test
    void listByCourseId() {
        ProgressesDto progressesDto = getProgressService.listByCourseId(new CourseId(1L));

        assertThat(progressesDto.getProgresses()).hasSize(1);
    }
}
