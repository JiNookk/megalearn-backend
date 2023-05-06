package jinookk.ourlms.daos;

import jinookk.ourlms.dtos.CourseFilterDto;
import jinookk.ourlms.models.entities.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseDao {
    Page<Course> findCoursesByFilter(CourseFilterDto courseFilterDto, Pageable pageable);

    Page<Course> findCoursesForAdmin(Pageable pageable);
}
