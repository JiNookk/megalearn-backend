package jinookk.ourlms.services;

import jinookk.ourlms.dtos.CourseDto;
import jinookk.ourlms.dtos.CoursesDto;
import jinookk.ourlms.dtos.MyCoursesDto;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.models.vos.ids.AccountId;
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

    public MyCoursesDto purchasedList() {
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

    public CoursesDto uploadedList(AccountId accountId, String type) {
        List<Course> courses = courseRepository.findAllByAccountId(accountId);

        List<Course> filtered = filtered(courses, type);

        List<CourseDto> courseDtos = filtered.stream()
                .map(Course::toCourseDto)
                .toList();

        return new CoursesDto(courseDtos);
    }

    private List<Course> filtered(List<Course> courses, String type) {
        return courses.stream()
                .filter(course -> course.filterType(type))
                .toList();
    }
}
