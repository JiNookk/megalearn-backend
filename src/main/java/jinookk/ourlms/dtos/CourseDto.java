package jinookk.ourlms.dtos;

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
import jinookk.ourlms.models.vos.status.Status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class CourseDto {
    private Long id;
    private String category;
    private String title;
    private Integer price;
    private String description;
    private String status;
    private String instructor;
    private Long instructorId;
    private String coverImage;
    private List<NewsDto> news;
    private List<String> hashTags;
    private List<String> skillSets;
    private Boolean isInstructor;
    private Boolean isPurchased;
    private Level level;
    private List<String> goals;
    private LocalDateTime createdAt;

    public CourseDto() {
    }

    public CourseDto(Long id, Category category, Title title, Price price, Content description,
                     Status status, Name instructor, AccountId instructorId, ImagePath coverImage,
                     List<Post> news, List<HashTag> hashTags, List<HashTag> skillSets, Boolean isInstructor,
                     Boolean isPurchased, Level level, List<Content> goals, LocalDateTime createdAt) {
        this.id = id;
        this.category = category.value();
        this.title = title.value();
        this.price = price.value();
        this.description = description.value();
        this.status = status.value();
        this.instructor = instructor.value();
        this.instructorId = instructorId.value();
        this.coverImage = coverImage.value();
        this.news = news.stream().map(Post::toDto).toList();
        this.hashTags = hashTags.stream().map(HashTag::tagName).toList();
        this.skillSets = skillSets.stream().map(HashTag::tagName).toList();
        this.isInstructor = isInstructor;
        this.isPurchased = isPurchased;
        this.level = level;
        this.goals = goals.stream().map(Content::value).toList();;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public Integer getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getInstructor() {
        return instructor;
    }

    public Boolean getPurchased() {
        return isPurchased;
    }

    public Level getLevel() {
        return level;
    }

    public List<String> getGoals() {
        return goals;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Long getInstructorId() {
        return instructorId;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public List<NewsDto> getNews() {
        return news;
    }

    public List<String> getHashTags() {
        return hashTags;
    }

    public List<String> getSkillSets() {
        return skillSets;
    }

    @Override
    public boolean equals(Object other) {
        return other != null &&
                other.getClass().equals(CourseDto.class) &&
                ((CourseDto) other).id.equals(this.id) &&
                ((CourseDto) other).category.equals(this.category) &&
                ((CourseDto) other).title.equals(this.title) &&
                ((CourseDto) other).price.equals(this.price) &&
                ((CourseDto) other).description.equals(this.description) &&
                ((CourseDto) other).status.equals(this.status) &&
                ((CourseDto) other).instructor.equals(this.instructor) &&
                ((CourseDto) other).instructorId.equals(this.instructorId) &&
                ((CourseDto) other).coverImage.equals(this.coverImage) &&
                ((CourseDto) other).news.equals(this.news) &&
                ((CourseDto) other).hashTags.equals(this.hashTags) &&
                ((CourseDto) other).skillSets.equals(this.skillSets) &&
                ((CourseDto) other).isInstructor.equals(this.isInstructor) &&
                ((CourseDto) other).isPurchased.equals(this.isPurchased) &&
                ((CourseDto) other).level.equals(this.level) &&
                ((CourseDto) other).goals.equals(this.goals) &&
                ((CourseDto) other).createdAt.equals(this.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                category,
                title,
                price,
                description,
                status,
                instructor,
                instructorId,
                coverImage,
                news,
                hashTags,
                skillSets,
                isInstructor,
                isPurchased,
                level,
                goals,
                createdAt
        );
    }
}
