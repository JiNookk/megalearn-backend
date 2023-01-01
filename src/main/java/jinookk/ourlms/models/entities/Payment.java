package jinookk.ourlms.models.entities;

import jinookk.ourlms.dtos.PaymentDto;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.models.vos.Price;
import jinookk.ourlms.models.vos.Title;
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
public class Payment {
    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "course_id"))
    private CourseId courseId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "account_id"))
    private AccountId accountId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "price"))
    private Price price;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "course_title"))
    private Title courseTitle;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "purchaser"))
    private Name purchaser;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Payment() {
    }

    public Payment(Long id, CourseId courseId, AccountId accountId, Price price, Title courseTitle, Name purchaser, LocalDateTime createdAt) {
        this.id = id;
        this.courseId = courseId;
        this.accountId = accountId;
        this.price = price;
        this.courseTitle = courseTitle;
        this.purchaser = purchaser;
        this.createdAt = createdAt;
    }

    public static Payment fake(int price) {
        return fake(new Price(price));
    }

    private static Payment fake(Price price) {
        return new Payment(1L, new CourseId(1L), new AccountId(1L), price, new Title("course title"),
                new Name("purchaser"), LocalDateTime.now());
    }

    public Long id() {
        return id;
    }

    public CourseId courseId() {
        return courseId;
    }

    public AccountId accountId() {
        return accountId;
    }

    public Price price() {
        return price;
    }

    public LocalDateTime createdAt() {
        return createdAt;
    }

    public PaymentDto toDto() {
        return new PaymentDto(id, courseId, price, purchaser, courseTitle, createdAt);
    }
}
