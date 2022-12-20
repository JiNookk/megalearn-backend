package jinookk.ourlms.dtos;

import jinookk.ourlms.models.vos.Category;
import jinookk.ourlms.models.vos.HashTag;
import jinookk.ourlms.models.vos.ids.LectureId;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.models.vos.Stars;
import jinookk.ourlms.models.vos.StudentCount;
import jinookk.ourlms.models.vos.Title;

import java.util.List;

public class CourseDto {
    private Long id;
    private String category;
    private String title;
    private Double stars;
    private Long studentCount;
    private String instructor;
    private Long currentLectureId;
    private List<String> hashTags;

    public CourseDto() {
    }

    public CourseDto(Long id, Category category, Title title, Stars stars, StudentCount studentCount,
                     Name instructor, LectureId currentLectureId, List<HashTag> hashTags) {
        this.id = id;
        this.category = category.value();
        this.title = title.value();
        this.stars = stars.rating();
        this.studentCount = studentCount.value();
        this.instructor = instructor.value();
        this.currentLectureId = currentLectureId.value();
        this.hashTags = hashTags.stream().map(HashTag::tagName).toList();
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

    public Double getStars() {
        return stars;
    }

    public Long getStudentCount() {
        return studentCount;
    }

    public String getInstructor() {
        return instructor;
    }

    public List<String> getHashTags() {
        return hashTags;
    }

    public Long getCurrentLectureId() {
        return currentLectureId;
    }
}
