package jinookk.ourlms.dtos;

import jinookk.ourlms.models.vos.Title;
import jinookk.ourlms.models.vos.ids.CourseId;

public class MonthlyPaymentDto {
    private Long courseId;
    private String courseTitle;
    private Integer cost;

    public MonthlyPaymentDto() {
    }

    public MonthlyPaymentDto(CourseId courseId, Title courseTitle, Integer cost) {
        this.courseId = courseId.value();
        this.courseTitle = courseTitle.value();
        this.cost = cost;
    }

    public Long getCourseId() {
        return courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public Integer getCost() {
        return cost;
    }
}
