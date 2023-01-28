package jinookk.ourlms.models.entities;

import jinookk.ourlms.dtos.RatingDto;
import jinookk.ourlms.dtos.RatingRequestDto;
import jinookk.ourlms.models.vos.Content;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Rating {
    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "account_id"))
    private AccountId accountId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "course_id"))
    private CourseId courseId;

    private Double point;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "author"))
    private Name author;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "content"))
    private Content content;

    @CreationTimestamp
    private LocalDateTime createdAt = LocalDateTime.now();

    public Rating() {
    }

    public Rating(Long id, AccountId accountId, CourseId courseId, Name author, Content content,
                  Double point) {
        this.id = id;
        this.accountId = accountId;
        this.courseId = courseId;
        this.author = author;
        this.content = content;
        this.point = point;
    }

    public static Rating of(Account account, RatingRequestDto ratingRequestDto) {
        Long courseId = ratingRequestDto.getCourseId();
        String content = ratingRequestDto.getContent();
        Integer rating = ratingRequestDto.getRating();
        return new Rating(null, new AccountId(account.id()), new CourseId(courseId), account.name(),
                new Content(content), (double) rating);
    }

    public Long id() {
        return id;
    }

    public AccountId accountId() {
        return accountId;
    }

    public CourseId courseId() {
        return courseId;
    }

    public Double point() {
        return point;
    }

    public Name author() {
        return author;
    }

    public Content content() {
        return content;
    }

    public LocalDateTime createdAt() {
        return createdAt;
    }

    public RatingDto toDto() {
        return new RatingDto(point, courseId, author, content, createdAt);
    }

    @Override
    public String toString() {
        return "id:" + id;
    }
}
