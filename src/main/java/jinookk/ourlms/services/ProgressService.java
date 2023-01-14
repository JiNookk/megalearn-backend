package jinookk.ourlms.services;

import jinookk.ourlms.dtos.LectureTimeDto;
import jinookk.ourlms.dtos.ProgressDto;
import jinookk.ourlms.dtos.ProgressesDto;
import jinookk.ourlms.exceptions.ProgressNotfound;
import jinookk.ourlms.models.entities.Progress;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.models.vos.ids.LectureId;
import jinookk.ourlms.models.vos.ids.ProgressId;
import jinookk.ourlms.repositories.ProgressRepository;
import jinookk.ourlms.specifications.ProgressSpecification;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class ProgressService {
    private final ProgressRepository progressRepository;

    public ProgressService(ProgressRepository progressRepository) {
        this.progressRepository = progressRepository;
    }

    public ProgressDto detail(LectureId lectureId, AccountId accountId) {
        Progress progress = progressRepository.findByLectureIdAndAccountId(lectureId, accountId)
                .orElseThrow(() -> new ProgressNotfound(lectureId, accountId));

        return progress.toDto();
    }

    public ProgressDto complete(ProgressId progressId) {
        Progress progress = progressRepository.findById(progressId.value())
                .orElseThrow(() -> new ProgressNotfound(progressId.value()));

        progress.complete();

        return progress.toDto();
    }

    public ProgressesDto listByCourseId(CourseId courseId) {
        List<Progress> progresses = progressRepository.findAllByCourseId(courseId);

        List<ProgressDto> progressDtos = progresses.stream().map(Progress::toDto).toList();

        return new ProgressesDto(progressDtos);
    }

    public ProgressesDto list(AccountId accountId, String date) {
        Sort sort = Sort.by("updatedAt").descending();

//        List<Progress> progresses = progressRepository.findAllByAccountId(accountId, sort);

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

    public ProgressDto updateTime(ProgressId progressId, LectureTimeDto lectureTimeDto) {
        Progress progress = progressRepository.findById(progressId.value())
                .orElseThrow(() -> new ProgressNotfound(progressId.value()));

        Progress updated = progress.updateTime(lectureTimeDto);

        return updated.toDto();
    }
}
