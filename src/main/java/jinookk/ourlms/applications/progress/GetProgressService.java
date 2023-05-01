package jinookk.ourlms.applications.progress;

import jinookk.ourlms.dtos.ProgressDto;
import jinookk.ourlms.dtos.ProgressesDto;
import jinookk.ourlms.exceptions.AccountNotFound;
import jinookk.ourlms.exceptions.ProgressNotfound;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Progress;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.models.vos.ids.LectureId;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.ProgressRepository;
import jinookk.ourlms.specifications.ProgressSpecification;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class GetProgressService {
    private final ProgressRepository progressRepository;
    private final AccountRepository accountRepository;

    public GetProgressService(ProgressRepository progressRepository, AccountRepository accountRepository) {
        this.progressRepository = progressRepository;
        this.accountRepository = accountRepository;
    }

    public ProgressDto detail(LectureId lectureId, UserName userName) {
        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFound(userName));

        AccountId accountId = new AccountId(account.id());

        Progress progress = progressRepository.findByLectureIdAndAccountId(lectureId, accountId)
                .orElseThrow(() -> new ProgressNotfound(lectureId, accountId));

        return progress.toDto();
    }

    public ProgressesDto listByCourseId(CourseId courseId) {
        List<Progress> progresses = progressRepository.findAllByCourseId(courseId);

        List<ProgressDto> progressDtos = progresses.stream().map(Progress::toDto).toList();

        return new ProgressesDto(progressDtos);
    }

    public ProgressesDto list(UserName userName, String date) {
        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFound(userName));

        AccountId accountId = new AccountId(account.id());

        Sort sort = Sort.by("updatedAt").descending();

        Specification<Progress> spec = Specification.where(ProgressSpecification.equalAccountId(accountId));

        if (date != null) {
            spec = spec.and(ProgressSpecification.betweenCurrentWeek(date));
        }

        List<Progress> progresses = progressRepository.findAll(spec, sort);

        List<ProgressDto> progressDtos = progresses.stream()
                .map(Progress::toDto)
                .toList();

        return new ProgressesDto(progressDtos);
    }
}
