package jinookk.ourlms.dtos;

import jinookk.ourlms.models.vos.Content;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.models.vos.ids.CourseId;

import java.time.LocalDateTime;

public class RatingDto {
    public Double rating;
    private Long courseId;
    private String author;
    private String content;
    private LocalDateTime createdAt;

    public RatingDto() {
    }

    public RatingDto(Double rating, CourseId courseId, Name author, Content content, LocalDateTime createdAt) {
        this.rating = rating;
        this.courseId = courseId.value();
        this.author = author.value();
        this.content = content.value();
        this.createdAt = createdAt;
    }

    public RatingDto(Double rating) {
        this.rating = rating;
    }

    public Double getRating() {
        return rating;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Long getCourseId() {
        return courseId;
    }
}
