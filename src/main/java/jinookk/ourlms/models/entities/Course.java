package jinookk.ourlms.models.entities;

import jinookk.ourlms.dtos.CourseDto;
import jinookk.ourlms.dtos.CourseRequestDto;
import jinookk.ourlms.dtos.CourseUpdateRequestDto;
import jinookk.ourlms.dtos.MonthlyPaymentDto;
import jinookk.ourlms.dtos.MyCourseDto;
import jinookk.ourlms.dtos.StatusUpdateDto;
import jinookk.ourlms.models.enums.CourseStatus;
import jinookk.ourlms.models.enums.Level;
import jinookk.ourlms.models.vos.Category;
import jinookk.ourlms.models.vos.Content;
import jinookk.ourlms.models.vos.HashTag;
import jinookk.ourlms.models.vos.ImagePath;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.models.vos.Post;
import jinookk.ourlms.models.vos.Price;
import jinookk.ourlms.models.vos.Title;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.models.vos.status.Status;
import org.springframework.security.access.AccessDeniedException;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
public class Course {
    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "instructor_id"))
    private AccountId accountId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "course_title"))
    private Title title;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "description"))
    private Content description;

    @Column(columnDefinition = "VARCHAR(255) DEFAULT 'PROCESSING'")
    @Enumerated(EnumType.STRING)
    private CourseStatus status;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "image_path"))
    private ImagePath imagePath;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "category_name"))
    private Category category;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "instructor_name"))
    private Name instructor;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "price"))
    private Price price;

    @Enumerated(EnumType.STRING)
    private Level level = Level.TOBEDETERMINED;

    @AttributeOverride(name = "value", column = @Column(name = "goal"))
    @ElementCollection
    private List<Content> goals = new ArrayList<>();

    @ElementCollection
    private List<Post> news = new ArrayList<>();

    @ElementCollection
    private List<HashTag> hashTags = new ArrayList<>();

    @ElementCollection
    private List<HashTag> skillSets = new ArrayList<>();

    private LocalDateTime createdAt;

    public Course() {
    }

    public Course(Long id, Title title, Content description, CourseStatus status, ImagePath imagePath, Category category,
                  Name instructor, AccountId accountId, Price price) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.imagePath = imagePath;
        this.category = category;
        this.instructor = instructor;
        this.accountId = accountId;
        this.price = price;
    }

    public Long id() {
        return id;
    }

    public Title title() {
        return title;
    }

    public ImagePath imagePath() {
        return imagePath;
    }

    public Level level() {
        return level;
    }

    public Price price() {
        return price;
    }

    public CourseStatus status() {
        return status;
    }

    public Course changeLevel(Level level) {
        this.level = level;

        return this;
    }

    public static Course of(CourseRequestDto courseRequestDto, Name instructor, AccountId accountId) {
        return new Course(null, new Title(courseRequestDto.getTitle()), new Content(""), CourseStatus.PROCESSING,
                new ImagePath(""), new Category(""), instructor, accountId, new Price(10000));
    }

    public Double averageRating(List<Rating> ratings) {
        if (ratings.size() == 0) {
            return 0.0;
        }

        return ratings.stream()
                .map(Rating::point)
                .reduce(Double::sum)
                .orElse(0.0) / ratings.size();
    }

    public MonthlyPaymentDto toMonthlyPaymentDto(List<Payment> payments) {
        CourseId courseId = new CourseId(id);

        if (payments.size() == 0) {
            return new MonthlyPaymentDto(courseId, title, 0);
        }

        Integer cost = payments.stream()
                .filter(payment -> ChronoUnit.MONTHS.between(payment.createdAt(), LocalDateTime.now()) < 1)
                .map(payment -> payment.price().value())
                .reduce(Integer::sum)
                .orElse(0);

        return new MonthlyPaymentDto(courseId, title, cost);
    }

    public Course updateStatus(String status) {
        this.status = CourseStatus.value(status);

        return this;
    }

    public void update(CourseUpdateRequestDto courseUpdateRequestDto, AccountId accountId) {
        if (!isInstructor(accountId)) {
            throw new AccessDeniedException("only instructor can update course!");
        }

        String status = courseUpdateRequestDto.getStatus();
        List<String> skills = courseUpdateRequestDto.getSkills();

        if (!status.isBlank()) {
            this.status = CourseStatus.value(status);

            return;
        }

        title = new Title(courseUpdateRequestDto.getTitle());
        category = new Category(courseUpdateRequestDto.getCategory());
        description = new Content(courseUpdateRequestDto.getDescription());
        imagePath = new ImagePath(courseUpdateRequestDto.getImagePath());
        price = new Price(courseUpdateRequestDto.getPrice());
        level = Level.of(courseUpdateRequestDto.getLevel(), level);

        if (skills.size() >= 1) {
            skillSets = skills.stream()
                    .map(HashTag::new)
                    .collect(Collectors.toList());
        }
    }

    public void delete(AccountId accountId) {
        if (!isInstructor(accountId)) {
            throw new AccessDeniedException("only instructor can delete course!");
        }

        status = CourseStatus.DELETED;
    }

    public boolean filterType(String type) {
        return status.filter(type);
    }

    public boolean filterId(CourseId courseId) {
        if (courseId.value() == null || courseId.value() == 0) {
            return true;
        }

        return isCourseId(courseId.value());
    }

    private boolean isCourseId(Long id) {
        return this.id.equals(id);
    }

    public boolean validatePayment(Optional<Payment> payment) {
        return payment.isPresent();
    }

    public boolean isInstructor(AccountId accountId) {
        if (accountId == null || accountId.value() == null) {
            return false;
        }

        return accountId.equals(this.accountId);
    }

    public CourseDto toCourseDto(AccountId accountId) {
        return new CourseDto(id, category, title, price, description, status, instructor, this.accountId,
                imagePath, news, hashTags, skillSets, isInstructor(accountId), true, level, goals, createdAt);
    }

    public CourseDto toCourseDto() {
        boolean isInstructor = false;

        return new CourseDto(id, category, title, price, description, status, instructor, accountId,
                imagePath, news, hashTags, skillSets, isInstructor, false, level, goals, createdAt);
    }

    public MyCourseDto toMyCourseDto() {
        return new MyCourseDto(id, title, imagePath);
    }

    public List<HashTag> skillSets() {
        return skillSets;
    }

    public void deleteSkill(HashTag skill, AccountId accountId) {
        if (!isInstructor(accountId)) {
            throw new AccessDeniedException("only instructor can delete course skill!");
        }

        this.skillSets.remove(skill);
    }

    public boolean isApproved() {
        return this.status.equals(CourseStatus.APPROVED);
    }

    public void validateAuthority(AccountId accountId) {
        if (this.status != CourseStatus.APPROVED && !isInstructor(accountId)) {
            throw new AccessDeniedException("this course is not approved yet!");
        }
    }
}
