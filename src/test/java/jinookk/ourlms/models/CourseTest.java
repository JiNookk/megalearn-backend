package jinookk.ourlms.models;

import jinookk.ourlms.dtos.MyCourseDto;
import jinookk.ourlms.models.entities.Course;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CourseTest {
    @Test
    void convertToMyCourseDto() {
        Course course = Course.fake("Test");

        MyCourseDto myCourseDto = course.toMyCourseDto();

        assertThat(myCourseDto).isNotNull();
    }
}
