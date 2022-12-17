package jinookk.ourlms.services;

import jinookk.ourlms.dtos.CourseDto;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.repositories.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CourseServiceTest {
    CourseService courseService;
    CourseRepository courseRepository;

    @BeforeEach
    void setup() {
        courseRepository = mock(CourseRepository.class);
        courseService = new CourseService(courseRepository);
    }
    @Test
    void detail() {
        Course course = Course.fake("내 강의");

        given(courseRepository.findById(1L))
                .willReturn(Optional.of(course));

        CourseDto courseDto = courseService.detail(1L);

        assertThat(courseDto).isNotNull();

        verify(courseRepository).findById(1L);
    }
}
