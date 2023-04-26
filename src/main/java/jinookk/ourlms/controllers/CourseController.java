package jinookk.ourlms.controllers;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import jinookk.ourlms.dtos.CourseDto;
import jinookk.ourlms.dtos.CourseFilterDto;
import jinookk.ourlms.dtos.CourseRequestDto;
import jinookk.ourlms.dtos.CourseUpdateRequestDto;
import jinookk.ourlms.dtos.CoursesDto;
import jinookk.ourlms.dtos.StatusUpdateDto;
import jinookk.ourlms.models.vos.HashTag;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.services.CourseService;
import jinookk.ourlms.services.MyCourseService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create Course", notes = "create Course")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {access_token}", required = true, dataType = "string", paramType = "header")
    })
    public CourseDto create(
            @Validated @RequestBody CourseRequestDto courseRequestDto,
            @RequestAttribute UserName userName
    ) {
        return courseService.create(courseRequestDto, userName);
    }

    @GetMapping("/courses/{courseId}")
    public CourseDto course(
            @RequestAttribute(required = false) UserName userName,
            @PathVariable Long courseId
    ) {
        return courseService.detail(userName, new CourseId(courseId));
    }

    @GetMapping("/courses/wishes")
    @ApiOperation(value = "WishList", notes = "fetches my course wish list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {access_token}", required = true, dataType = "string", paramType = "header")
    })
    public CoursesDto wishList(
            @RequestAttribute UserName userName
    ) {
        return courseService.wishList(userName);
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

    @GetMapping("/admin/courses")
    public CoursesDto listForAdmin(
            @RequestParam(required = false, defaultValue = "1") Integer page
    ) {
        return courseService.listForAdmin(page);
    }

    @GetMapping("/account/my-courses")
    @ApiOperation(value = "My Courses", notes = "fetches purchased courses")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {access_token}", required = true, dataType = "string", paramType = "header")
    })
    public CoursesDto myCourses(
            @RequestAttribute UserName userName
    ) {
        return myCourseService.myCourses(userName);
    }

    @GetMapping("/instructor/my-courses")
    @ApiOperation(value = "Uploaded Courses", notes = "fetches uploaded courses for instructor")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {access_token}", required = true, dataType = "string", paramType = "header")
    })
    public CoursesDto uploadedCourses(
            @RequestAttribute UserName userName,
            @RequestParam(required = false, defaultValue = "all") String type
    ) {
        return myCourseService.uploadedList(userName, type);
    }

    @PatchMapping("/courses/{courseId}")
    public CourseDto update(
            @PathVariable Long courseId,
            @Validated @RequestBody CourseUpdateRequestDto courseUpdateRequestDto
    ) {
        return courseService.update(courseId, courseUpdateRequestDto);
    }

    @PatchMapping("/courses/{courseId}/status")
    public CourseDto updateStatus(
            @PathVariable Long courseId,
            @Validated @RequestBody StatusUpdateDto statusUpdateDto
    ) {
        return courseService.updateStatus(courseId, statusUpdateDto);
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

    @ExceptionHandler(ServletRequestBindingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userNameRequired(ServletRequestBindingException exception) {
        return exception.getMessage();
    }
}
