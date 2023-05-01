package jinookk.ourlms.controllers;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import jinookk.ourlms.applications.lecture.CreateLectureService;
import jinookk.ourlms.applications.lecture.DeleteLectureService;
import jinookk.ourlms.applications.lecture.GetLectureService;
import jinookk.ourlms.applications.lecture.UpdateLectureService;
import jinookk.ourlms.dtos.LectureDto;
import jinookk.ourlms.dtos.LectureRequestDto;
import jinookk.ourlms.dtos.LectureUpdateRequestDto;
import jinookk.ourlms.dtos.LecturesDto;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.CourseId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LectureController {
    private final GetLectureService getLectureService;
    private final CreateLectureService createLectureService;
    private final UpdateLectureService updateLectureService;
    private final DeleteLectureService deleteLectureService;

    public LectureController(GetLectureService getLectureService,
                             CreateLectureService createLectureService,
                             UpdateLectureService updateLectureService,
                             DeleteLectureService deleteLectureService) {
        this.getLectureService = getLectureService;
        this.createLectureService = createLectureService;
        this.updateLectureService = updateLectureService;
        this.deleteLectureService = deleteLectureService;
    }

    @PostMapping("/lectures")
    public LectureDto create(
            @RequestBody LectureRequestDto lectureRequestDto
    ) {
        return createLectureService.create(lectureRequestDto);
    }

    @GetMapping("/courses/{courseId}/lectures/{lectureId}")
    public LectureDto lecture(
            @PathVariable Long courseId,
            @PathVariable Long lectureId
    ) {
        return getLectureService.detail(lectureId);
    }

    @GetMapping("/lectures")
    public LecturesDto list() {
        return getLectureService.list();
    }

    @GetMapping("/courses/{courseId}/lectures")
    public LecturesDto listByCourseId(
            @PathVariable Long courseId
    ) {
        return getLectureService.listByCourseId(new CourseId(courseId));
    }

    @GetMapping("/lectures/instructor")
    @ApiOperation(value = "Fetches Uploaded Lectures", notes = "fetches uploaded lectures by instructor")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {access_token}", required = true, dataType = "string", paramType = "header")
    })
    public LecturesDto listByInstructorId(
            @RequestAttribute UserName userName
    ) {
        return getLectureService.listByInstructorId(userName);
    }

    @GetMapping("/lectures/me")
    public LecturesDto myLectures(
            @RequestAttribute UserName userName
    ) {
        return getLectureService.myLectures(userName);
    }

    @PatchMapping("/lectures/{lectureId}")
    public LectureDto update(
            @RequestBody LectureUpdateRequestDto lectureUpdateRequestDto,
            @PathVariable Long lectureId
    ) {
        return updateLectureService.update(lectureId, lectureUpdateRequestDto);
    }

    @DeleteMapping("/lectures/{lectureId}")
    public LectureDto delete(
            @PathVariable Long lectureId
    ) {
        return deleteLectureService.delete(lectureId);
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userNameRequired(ServletRequestBindingException exception) {
        return exception.getMessage();
    }
}
