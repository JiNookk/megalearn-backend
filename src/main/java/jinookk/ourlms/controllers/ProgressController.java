package jinookk.ourlms.controllers;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import jinookk.ourlms.dtos.LectureTimeDto;
import jinookk.ourlms.dtos.ProgressDto;
import jinookk.ourlms.dtos.ProgressesDto;
import jinookk.ourlms.models.entities.Progress;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.models.vos.ids.LectureId;
import jinookk.ourlms.models.vos.ids.ProgressId;
import jinookk.ourlms.services.ProgressService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProgressController {
    private final ProgressService progressService;

    public ProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    @GetMapping("/lectures/{lectureId}/progress")
    @ApiOperation(value = "Progress", notes = "fetches my progress with given lecture")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {access_token}", required = true, dataType = "string", paramType = "header")
    })
    public ProgressDto progress(
            @PathVariable Long lectureId,
            @RequestAttribute Name userName
    ) {
        return progressService.detail(new LectureId(lectureId), userName);
    }

    @GetMapping("/progresses")
    @ApiOperation(value = "Progresses", notes = "fetches my progresses")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {access_token}", required = true, dataType = "string", paramType = "header")
    })
    public ProgressesDto list(
            @RequestAttribute Name userName,
            @RequestParam(required = false) String date
    ) {
        return progressService.list(userName, date);
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
        return progressService.complete(new ProgressId(progressId));
    }

    @PatchMapping("/progresses/{progressId}/time")
    public ProgressDto updateTime(
            @PathVariable Long progressId,
            @RequestBody LectureTimeDto lectureTimeDto
            ) {
        return progressService.updateTime(new ProgressId(progressId), lectureTimeDto);
    }
}
