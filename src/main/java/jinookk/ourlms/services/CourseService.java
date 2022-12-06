package jinookk.ourlms.services;

import jinookk.ourlms.dtos.CourseDto;
import jinookk.ourlms.exceptions.CourseNotFound;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.repositories.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public CourseDto detail(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(()-> new CourseNotFound(courseId));

        return course.toCourseDto();
    }
}
