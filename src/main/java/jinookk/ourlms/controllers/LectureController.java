package jinookk.ourlms.controllers;

import jinookk.ourlms.dtos.LectureDto;
import jinookk.ourlms.dtos.LecturesDto;
import jinookk.ourlms.services.LectureService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("courses")
public class LectureController {
    private final LectureService lectureService;

    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @GetMapping("{courseId}/unit/{lectureId}")
    public LectureDto lecture(
            @PathVariable Long courseId,
            @PathVariable Long lectureId
    ) {
        return lectureService.detail(lectureId);
    }

    @GetMapping("{courseId}/unit")
    public LecturesDto list(
            @PathVariable Long courseId
    ) {
        return lectureService.list(courseId);
    }
}
