package jinookk.ourlms.dtos;

public class CourseRequestDto {
    private String title;

    public CourseRequestDto() {
    }

    public CourseRequestDto(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
