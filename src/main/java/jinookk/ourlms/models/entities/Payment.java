package jinookk.ourlms.models.entities;

import jinookk.ourlms.dtos.PaymentDto;
import jinookk.ourlms.exceptions.InvalidPaymentInformation;
import jinookk.ourlms.models.enums.PaymentStatus;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.models.vos.Price;
import jinookk.ourlms.models.vos.Title;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.access.AccessDeniedException;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

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

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Payment() {
    }

    public Payment(Long id, CourseId courseId, AccountId accountId, Price price, Title courseTitle,
                   Name purchaser, PaymentStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.courseId = courseId;
        this.accountId = accountId;
        this.price = price;
        this.courseTitle = courseTitle;
        this.purchaser = purchaser;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static List<Payment> listOf(List<Course> courses, Account account, Cart cart, PaymentStatus status) {
        if (account == null || courses == null || courses.size() == 0) {
            throw new InvalidPaymentInformation();
        }

        try {
            courses.forEach(course -> {
                if (!course.isApproved()) {
                    throw new AccessDeniedException("cannot purchase not approved course!");
                }
            });

            List<Payment> payments = courses.stream()
                    .map(course -> of(account, course, status))
                    .toList();

            List<CourseId> courseIds = getCourseIds(courses);

            if (status.equals(PaymentStatus.SUCCESS)) {
                cart.removeItems(courseIds);
            }

            return payments;
        } catch (AccessDeniedException exception) {
            List<Payment> payments = courses.stream()
                    .map(course -> of(account, course, PaymentStatus.FAILED))
                    .toList();

            return payments;
        }
    }

    public static Payment of(Account account, Course course, PaymentStatus status) {
        if (hasInvalidInformation(account, course)) {
            throw new InvalidPaymentInformation();
        }

        return new Payment(
                null,
                new CourseId(course.id()),
                new AccountId(account.id()),
                new Price(course.price().value()),
                new Title(course.title().value()),
                new Name(account.name().value(), false),
                status,
                LocalDateTime.now()
        );
    }

    private static List<CourseId> getCourseIds(List<Course> courses) {
        return courses.stream().map(course -> new CourseId(course.id())).toList();
    }

    private static boolean hasInvalidInformation(Account account, Course course) {
        return account == null || course == null || course.id() == null || account.id() == null
                || course.price().value() == null || course.title().value() == null || account.name().value() == null;
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

    public Name purchaser() {
        return purchaser;
    }

    public LocalDateTime createdAt() {
        return createdAt;
    }

    public PaymentDto toDto() {
        return new PaymentDto(id, courseId, price, purchaser, courseTitle, createdAt, status);
    }

    public PaymentStatus status() {
        return status;
    }
}
