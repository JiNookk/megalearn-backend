package jinookk.ourlms.dtos;

import java.util.List;

public class MyCoursesDto {
    private List<MyCourseDto> myCourses;

    public MyCoursesDto() {
    }

    public MyCoursesDto(List<MyCourseDto> myCourses) {
        this.myCourses = myCourses;
    }

    public List<MyCourseDto> getMyCourses() {
        return myCourses;
    }
}
