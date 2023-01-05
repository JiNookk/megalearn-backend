package jinookk.ourlms.controllers;

import jinookk.ourlms.dtos.ProgressDto;
import jinookk.ourlms.dtos.ProgressesDto;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.models.vos.ids.LectureId;
import jinookk.ourlms.services.ProgressService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProgressController {
    private final ProgressService progressService;

    public ProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    @GetMapping("/lectures/{lectureId}/progresses")
    public ProgressDto progress(
            @PathVariable Long lectureId
    ) {
        return progressService.detail(new LectureId(lectureId));
    }

    @GetMapping("/progresses")
    public ProgressesDto list(
            @RequestAttribute Long accountId
    ) {
        return progressService.list(new AccountId(accountId));
    }

    @GetMapping("/courses/{courseId}/progresses")
    public ProgressesDto listByCourseId(
            @PathVariable Long courseId
    ) {
        return progressService.listByCourseId(new CourseId(courseId));
    }

    @PatchMapping("/progresses/{progressId}")
    public ProgressDto complete(
            @PathVariable Long progressId
    ) {
        return progressService.complete(progressId);
    }
}
