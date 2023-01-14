package jinookk.ourlms.controllers;

import jinookk.ourlms.dtos.CourseDto;
import jinookk.ourlms.dtos.CourseFilterDto;
import jinookk.ourlms.dtos.CourseRequestDto;
import jinookk.ourlms.dtos.CourseUpdateRequestDto;
import jinookk.ourlms.dtos.CoursesDto;
import jinookk.ourlms.models.vos.HashTag;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.services.CourseService;
import jinookk.ourlms.services.MyCourseService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseController {
    private final CourseService courseService;
    private final MyCourseService myCourseService;

    public CourseController(CourseService courseService, MyCourseService myCourseService) {
        this.courseService = courseService;
        this.myCourseService = myCourseService;
    }

    @PostMapping("/courses")
    public CourseDto create(
            @Validated @RequestBody CourseRequestDto courseRequestDto,
            @RequestAttribute Long accountId
    ) {
        return courseService.create(courseRequestDto, new AccountId(accountId));
    }

    @GetMapping("/courses/{courseId}")
    public CourseDto course(
            @RequestAttribute Long accountId,
            @PathVariable Long courseId
    ) {
        return courseService.detail(new AccountId(accountId), new CourseId(courseId));
    }

    @GetMapping("/courses/wishes")
    public CoursesDto wishList(
            @RequestAttribute Long accountId
    ) {
        return courseService.wishList(new AccountId(accountId));
    }

    @GetMapping("/courses")
    public CoursesDto list(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false) String level,
            @RequestParam(required = false) String cost,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) String skill
    ) {
        CourseFilterDto courseFilterDto = new CourseFilterDto(level, cost, skill, content);
        return courseService.list(page, courseFilterDto);
    }

    @GetMapping("/account/my-courses")
    public CoursesDto myCourses(
            @RequestAttribute Long accountId
    ) {
        return myCourseService.myCourses(new AccountId(accountId));
    }

    @GetMapping("/instructor/my-courses")
    public CoursesDto uploadedCourses(
            @RequestAttribute Long accountId,
            @RequestParam(required = false, defaultValue = "all") String type
    ) {
        return myCourseService.uploadedList(new AccountId(accountId), type);
    }

    @PatchMapping("/courses/{courseId}")
    public CourseDto update(
            @PathVariable Long courseId,
            @Validated @RequestBody CourseUpdateRequestDto courseUpdateRequestDto
    ) {
        return courseService.update(courseId, courseUpdateRequestDto);
    }

    @DeleteMapping("/courses/{courseId}")
    public CourseDto delete(
            @PathVariable Long courseId
    ) {
        return courseService.delete(courseId);
    }

    @DeleteMapping("/courses/{courseId}/skills/{skill}")
    public CourseDto deleteSkill(
            @PathVariable Long courseId,
            @PathVariable String skill
    ) {
        return courseService.deleteSkill(new CourseId(courseId), new HashTag(skill));
    }
}
