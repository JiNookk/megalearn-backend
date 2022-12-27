package jinookk.ourlms.dtos;

import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.models.vos.Price;
import jinookk.ourlms.models.vos.Title;
import jinookk.ourlms.models.vos.ids.CourseId;

import java.time.LocalDateTime;

public class PaymentDto {
    private Long id;
    private Long courseId;
    private Integer cost;
    private String purchaser;
    private String courseTitle;
    private LocalDateTime createdAt;

    public PaymentDto() {
    }

    public PaymentDto(Long id, CourseId courseId, Price cost, Name purchaser, Title courseTitle, LocalDateTime createdAt) {
        this.id = id;
        this.courseId = courseId.value();
        this.cost = cost.value();
        this.purchaser = purchaser.value();
        this.courseTitle = courseTitle.value();
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Integer getCost() {
        return cost;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public Long getCourseId() {
        return courseId;
    }

    public String getPurchaser() {
        return purchaser;
    }
}
