package jinookk.ourlms.dtos;

public class RecentPaymentsDto {
    private String courseTitle;
    private Long price;

    public RecentPaymentsDto() {
    }

    public RecentPaymentsDto(String courseTitle, Long price) {
        this.courseTitle = courseTitle;
        this.price = price;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public Long getPrice() {
        return price;
    }
}
