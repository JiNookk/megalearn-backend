package jinookk.ourlms.controllers;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import jinookk.ourlms.applications.progress.GetProgressService;
import jinookk.ourlms.applications.progress.UpdateProgressService;
import jinookk.ourlms.dtos.LectureTimeDto;
import jinookk.ourlms.dtos.ProgressDto;
import jinookk.ourlms.dtos.ProgressesDto;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.models.vos.ids.LectureId;
import jinookk.ourlms.models.vos.ids.ProgressId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProgressController {
    private final GetProgressService getProgressService;
    private final UpdateProgressService updateProgressService;

    public ProgressController(GetProgressService getProgressService,
                              UpdateProgressService updateProgressService) {
        this.getProgressService = getProgressService;
        this.updateProgressService = updateProgressService;
    }

    @GetMapping("/lectures/{lectureId}/progress")
    @ApiOperation(value = "Progress", notes = "fetches my progress with given lecture")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {access_token}", required = true, dataType = "string", paramType = "header")
    })
    public ProgressDto progress(
            @PathVariable Long lectureId,
            @RequestAttribute UserName userName
    ) {
        return getProgressService.detail(new LectureId(lectureId), userName);
    }

    @GetMapping("/progresses")
    @ApiOperation(value = "Progresses", notes = "fetches my progresses")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {access_token}", required = true, dataType = "string", paramType = "header")
    })
    public ProgressesDto list(
            @RequestAttribute UserName userName,
            @RequestParam(required = false) String date
    ) {
        return getProgressService.list(userName, date);
    }

    @GetMapping("/courses/{courseId}/progresses")
    public ProgressesDto listByCourseId(
            @PathVariable Long courseId
    ) {
        return getProgressService.listByCourseId(new CourseId(courseId));
    }

    @PatchMapping("/progresses/{progressId}")
    public ProgressDto complete(
            @PathVariable Long progressId
    ) {
        return updateProgressService.complete(new ProgressId(progressId));
    }

    @PatchMapping("/progresses/{progressId}/time")
    public ProgressDto updateTime(
            @PathVariable Long progressId,
            @RequestBody LectureTimeDto lectureTimeDto
            ) {
        return updateProgressService.updateTime(new ProgressId(progressId), lectureTimeDto);
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userNameRequired(ServletRequestBindingException exception) {
        return exception.getMessage();
    }
}
