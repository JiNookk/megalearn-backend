package jinookk.ourlms.applications.progress;

import jinookk.ourlms.dtos.LectureTimeDto;
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
import jinookk.ourlms.models.vos.ids.ProgressId;
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
public class UpdateProgressService {
    private final ProgressRepository progressRepository;

    public UpdateProgressService(ProgressRepository progressRepository) {
        this.progressRepository = progressRepository;
    }

    public ProgressDto complete(ProgressId progressId) {
        Progress progress = progressRepository.findById(progressId.value())
                .orElseThrow(() -> new ProgressNotfound(progressId.value()));

        progress.complete();

        return progress.toDto();
    }

    public ProgressDto updateTime(ProgressId progressId, LectureTimeDto lectureTimeDto) {
        Progress progress = progressRepository.findById(progressId.value())
                .orElseThrow(() -> new ProgressNotfound(progressId.value()));

        Progress updated = progress.updateTime(lectureTimeDto);

        return updated.toDto();
    }
}
