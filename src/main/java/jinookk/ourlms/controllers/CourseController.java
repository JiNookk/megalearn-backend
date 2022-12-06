package jinookk.ourlms.controllers;

import jinookk.ourlms.dtos.CourseDto;
import jinookk.ourlms.services.CourseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("courses")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("{id}")
    public CourseDto course(
            @PathVariable Long id
    ) {
        return courseService.detail(id);
    }
}
