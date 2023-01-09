package jinookk.ourlms.dtos;

import java.util.List;

public class CoursesDto {
    private List<CourseDto> courses;
    private Integer totalPages;

    public CoursesDto() {
    }

    public CoursesDto(List<CourseDto> courses) {
        this.courses = courses;
    }

    public CoursesDto(List<CourseDto> courses, Integer totalPages) {
        this.courses = courses;
        this.totalPages = totalPages;
    }

    public List<CourseDto> getCourses() {
        return courses;
    }

    public Integer getTotalPages() {
        return totalPages;
    }
}
