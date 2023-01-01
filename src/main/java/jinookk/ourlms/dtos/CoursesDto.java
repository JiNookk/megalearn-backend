package jinookk.ourlms.dtos;

import java.util.List;

public class CoursesDto {
    private List<CourseDto> courses;

    public CoursesDto() {
    }

    public CoursesDto(List<CourseDto> courses) {
        this.courses = courses;
    }

    public List<CourseDto> getCourses() {
        return courses;
    }
}
