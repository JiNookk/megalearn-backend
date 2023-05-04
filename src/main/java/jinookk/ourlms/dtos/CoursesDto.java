package jinookk.ourlms.dtos;

import java.util.List;
import java.util.Objects;

public class CoursesDto {
    private List<CourseDto> courses;
    private Integer totalPages;

    public CoursesDto() {
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


    @Override
    public boolean equals(Object other) {
        return other != null &&
                other.getClass().equals(CoursesDto.class) &&
                ((CoursesDto) other).courses.equals(this.courses) &&
                ((CoursesDto) other).totalPages.equals(this.totalPages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courses, totalPages);
    }
}
