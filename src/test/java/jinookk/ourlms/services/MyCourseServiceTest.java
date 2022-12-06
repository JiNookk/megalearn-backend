package jinookk.ourlms.services;

import jinookk.ourlms.dtos.MyCoursesDto;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.repositories.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class MyCourseServiceTest {
    MyCourseService myCourseService;
    CourseRepository courseRepository;

    @BeforeEach
    void setup() {
        courseRepository = mock(CourseRepository.class);
        myCourseService = new MyCourseService(courseRepository);
    }

    @Test
    void list() {
        Course course = Course.fake("test");

        given(courseRepository.findAll())
                .willReturn(List.of(course));

        MyCoursesDto list = myCourseService.list();

        assertThat(list.getMyCourses()).hasSize(1);
    }
}
