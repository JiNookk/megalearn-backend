package jinookk.ourlms.controllers;

import jinookk.ourlms.dtos.MyCoursesDto;
import jinookk.ourlms.services.MyCourseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account/my-courses")
public class MyCoursesController {
    private final MyCourseService myCourseService;

    public MyCoursesController(MyCourseService myCourseService) {
        this.myCourseService = myCourseService;
    }

    @GetMapping
    public MyCoursesDto myCourses() {
        // TODO : 헤더로 유저 아이디 보내줄 것!!, 인자로 전달
        return myCourseService.list();
    }
}
