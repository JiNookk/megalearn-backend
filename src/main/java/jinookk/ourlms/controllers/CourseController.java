package jinookk.ourlms.controllers;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import jinookk.ourlms.applications.course.CreateCourseService;
import jinookk.ourlms.applications.course.DeleteCourseService;
import jinookk.ourlms.applications.course.GetCourseService;
import jinookk.ourlms.applications.course.UpdateCourseService;
import jinookk.ourlms.dtos.GetCoursesDto;
import jinookk.ourlms.dtos.CourseDto;
import jinookk.ourlms.dtos.CourseFilterDto;
import jinookk.ourlms.dtos.CourseRequestDto;
import jinookk.ourlms.dtos.CourseUpdateRequestDto;
import jinookk.ourlms.dtos.CoursesDto;
import jinookk.ourlms.dtos.StatusUpdateDto;
import jinookk.ourlms.exceptions.CourseNotFound;
import jinookk.ourlms.models.vos.HashTag;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.applications.course.MyCourseService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
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
    private final GetCourseService getCourseService;
    private final CreateCourseService createCourseService;
    private final UpdateCourseService updateCourseService;
    private final DeleteCourseService deleteCourseService;
    private final MyCourseService myCourseService;

    public CourseController(GetCourseService getCourseService,
                            CreateCourseService createCourseService,
                            UpdateCourseService updateCourseService,
                            DeleteCourseService deleteCourseService,
                            MyCourseService myCourseService) {
        this.getCourseService = getCourseService;
        this.createCourseService = createCourseService;
        this.updateCourseService = updateCourseService;
        this.deleteCourseService = deleteCourseService;
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
        return createCourseService.create(courseRequestDto, userName);
    }

    @GetMapping("/courses/{courseId}")
    @ApiOperation(value = "Fetch Course", notes = "fetches course")
    @ApiResponse(code = 404, message = "course not found")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {access_token}", required = false, dataType = "string", paramType = "header")
    })
    public CourseDto course(
            @RequestAttribute(required = false) UserName userName,
            @PathVariable Long courseId
    ) {
        return getCourseService.detail(userName, new CourseId(courseId));
    }

    @GetMapping("/courses/wishes")
    @ApiOperation(value = "WishList", notes = "fetches my course wish list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {access_token}", required = true, dataType = "string", paramType = "header")
    })
    public GetCoursesDto wishList(
            @RequestAttribute UserName userName
    ) {
        return getCourseService.wishList(userName);
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

        return getCourseService.list(page, courseFilterDto);
    }

    @GetMapping("/admin/courses")
    public CoursesDto listForAdmin(
            @RequestParam(required = false, defaultValue = "1") Integer page
    ) {
        return getCourseService.listForAdmin(page);
    }

    @GetMapping("/account/my-courses")
    @ApiOperation(value = "My Courses", notes = "fetches purchased courses")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {access_token}", required = true, dataType = "string", paramType = "header")
    })
    public GetCoursesDto myCourses(
            @RequestAttribute UserName userName
    ) {
        return myCourseService.myCourses(userName);
    }

    @GetMapping("/instructor/my-courses")
    @ApiOperation(value = "Uploaded Courses", notes = "fetches uploaded courses for instructor")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {access_token}", required = true, dataType = "string", paramType = "header")
    })
    public GetCoursesDto uploadedCourses(
            @RequestAttribute UserName userName,
            @RequestParam(required = false, defaultValue = "all") String type
    ) {
        return myCourseService.uploadedList(userName, type);
    }

    @PatchMapping("/courses/{courseId}")
    @ApiOperation(value = "Update Course", notes = "update course with given id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {access_token}", required = true, dataType = "string", paramType = "header")
    })
    public CourseDto update(
            @RequestAttribute UserName userName,
            @PathVariable Long courseId,
            @Validated @RequestBody CourseUpdateRequestDto courseUpdateRequestDto
    ) {
        return updateCourseService.update(courseId, courseUpdateRequestDto, userName);
    }

    @PatchMapping("/courses/{courseId}/status")
    public CourseDto updateStatus(
            @PathVariable Long courseId,
            @Validated @RequestBody StatusUpdateDto statusUpdateDto
    ) {
        return updateCourseService.updateStatus(courseId, statusUpdateDto);
    }

    @DeleteMapping("/courses/{courseId}")
    @ApiOperation(value = "Delete Courses", notes = "delete course with given id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {access_token}", required = true, dataType = "string", paramType = "header")
    })
    public CourseDto delete(
            @PathVariable Long courseId,
            @RequestAttribute UserName userName
    ) {
        return deleteCourseService.delete(courseId, userName);
    }

    @DeleteMapping("/courses/{courseId}/skills/{skill}")
    @ApiOperation(value = "Delete Courses", notes = "delete course with given id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {access_token}", required = true, dataType = "string", paramType = "header")
    })
    public CourseDto deleteSkill(
            @PathVariable Long courseId,
            @PathVariable String skill,
            @RequestAttribute UserName userName
    ) {
        return deleteCourseService.deleteSkill(new CourseId(courseId), new HashTag(skill), userName);
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userNameRequired(ServletRequestBindingException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(CourseNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String courseNotFound(CourseNotFound exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String accessDeniedException(AccessDeniedException exception) {
        return exception.getMessage();
    }
}
