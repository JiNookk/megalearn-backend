package jinookk.ourlms.controllers;

import jinookk.ourlms.dtos.LectureDto;
import jinookk.ourlms.dtos.LectureRequestDto;
import jinookk.ourlms.dtos.LectureUpdateRequestDto;
import jinookk.ourlms.dtos.LecturesDto;
import jinookk.ourlms.dtos.SectionDto;
import jinookk.ourlms.dtos.SectionRequestDto;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.services.LectureService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LectureController {
    private final LectureService lectureService;

    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @PostMapping("/lectures")
    public LectureDto create(
            @RequestBody LectureRequestDto lectureRequestDto
    ) {
        return lectureService.create(lectureRequestDto);
    }

    @GetMapping("/courses/{courseId}/lectures/{lectureId}")
    public LectureDto lecture(
            @PathVariable Long courseId,
            @PathVariable Long lectureId
    ) {
        return lectureService.detail(lectureId);
    }

    @GetMapping("/courses/{courseId}/lectures")
    public LecturesDto list(
            @PathVariable Long courseId
    ) {
        return lectureService.list(new CourseId(courseId));
    }

    @GetMapping("/lectures")
    public LecturesDto listByInstructorId(
            @RequestAttribute Long accountId
    ) {
        return lectureService.listByInstructorId(new AccountId(accountId));
    }

    @PatchMapping("/lectures/{lectureId}")
    public LectureDto update(
            @RequestBody LectureUpdateRequestDto lectureUpdateRequestDto,
            @PathVariable Long lectureId
    ) {
        return lectureService.update(lectureId, lectureUpdateRequestDto);
    }

    @DeleteMapping("/lectures/{lectureId}")
    public LectureDto delete(
            @PathVariable Long lectureId
    ) {
        return lectureService.delete(lectureId);
    }
}
