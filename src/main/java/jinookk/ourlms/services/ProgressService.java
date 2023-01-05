package jinookk.ourlms.services;

import jinookk.ourlms.dtos.ProgressDto;
import jinookk.ourlms.dtos.ProgressesDto;
import jinookk.ourlms.exceptions.ProgressNotfound;
import jinookk.ourlms.models.entities.Progress;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.models.vos.ids.LectureId;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.ProgressRepository;
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

    public ProgressDto detail(LectureId lectureId) {
        Progress progress = progressRepository.findByLectureId(lectureId)
                .orElseThrow(() -> new ProgressNotfound(lectureId));

        return progress.toDto();
    }

    public ProgressDto complete(Long progressId) {
        Progress progress = progressRepository.findById(progressId)
                .orElseThrow(() -> new ProgressNotfound(progressId));

        progress.complete();

        return progress.toDto();
    }

    public ProgressesDto listByCourseId(CourseId courseId) {
        List<Progress> progresses = progressRepository.findAllByCourseId(courseId);

        List<ProgressDto> progressDtos = progresses.stream().map(Progress::toDto).toList();

        return new ProgressesDto(progressDtos);
    }

    public ProgressesDto list(AccountId accountId) {
        List<Progress> progresses = progressRepository.findAllByAccountId(accountId);

        List<ProgressDto> progressDtos = progresses.stream()
                .map(Progress::toDto)
                .toList();

        return new ProgressesDto(progressDtos);
    }
}
