package jinookk.ourlms.applications.dtos;

import jinookk.ourlms.dtos.CourseDto;

import java.util.List;

public record GetCoursesDto(List<CourseDto> courses) {
}
