package jinookk.ourlms.services;

import jinookk.ourlms.dtos.MyCourseDto;
import jinookk.ourlms.dtos.MyCoursesDto;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.repositories.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MyCourseService {
    private final CourseRepository courseRepository;

    public MyCourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public MyCoursesDto list() {
        Long userId = 1L;

        // TODO: 나중에 로그인 구현시 유저 아이디로 찾아오는 메서드 구현
//        List<Course> courses = courseRepository.findAllByUserId(userId);
        List<Course> courses = courseRepository.findAll();

        return new MyCoursesDto(
                courses.stream()
                .map(Course::toMyCourseDto)
                .toList()
        );
    }
}
