package jinookk.ourlms.dtos;

public class InquiryFilterDto {
    private Long courseId;
    private String type;
    private String order;

    public InquiryFilterDto() {
    }

    public InquiryFilterDto(Long courseId, String type, String order) {
        this.courseId = courseId;
        this.type = type;
        this.order = order;
    }

    public Long getCourseId() {
        return courseId;
    }

    public String getType() {
        return type;
    }

    public String getOrder() {
        return order;
    }
}
