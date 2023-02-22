package jinookk.ourlms.models.entities;

import jinookk.ourlms.dtos.LikeDto;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Table(name = "like_table")
@Entity
public class Like {
    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "account_id"))
    private AccountId accountId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "course_id"))
    private CourseId courseId;

    private Boolean clicked = false;

    public Like() {
    }

    public Like(AccountId accountId, CourseId courseId, Boolean clicked) {
        this.accountId = accountId;
        this.courseId = courseId;
        this.clicked = clicked;
    }

    public static Like fake(Boolean clicked) {
        return new Like(new AccountId(1L), new CourseId(1L), clicked);
    }

    public static List<Like> listOf(AccountId accountId, List<CourseId> courseIds) {
        return courseIds.stream()
                .map(courseId -> Like.of(accountId, courseId))
                .toList();
    }

    public static Like of(AccountId accountId, CourseId courseId) {
        return new Like(accountId, courseId, false);
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

    public boolean clicked() {
        return clicked;
    }

    public LikeDto toDto() {
        return new LikeDto(id, clicked, courseId);
    }

    public Like toggle() {
        this.clicked = !this.clicked;

        return this;
    }
}
