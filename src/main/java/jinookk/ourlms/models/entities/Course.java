package jinookk.ourlms.models.entities;

import jinookk.ourlms.dtos.CourseDto;
import jinookk.ourlms.dtos.MyCourseDto;
import jinookk.ourlms.models.vos.Category;
import jinookk.ourlms.models.vos.HashTag;
import jinookk.ourlms.models.vos.ImagePath;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.models.vos.LectureId;
import jinookk.ourlms.models.vos.Stars;
import jinookk.ourlms.models.vos.StudentCount;
import jinookk.ourlms.models.vos.Title;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Course {
    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "lecture_id"))
    private LectureId currentLectureId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "course_title"))
    private Title title;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "image_path"))
    private ImagePath imagePath;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "category_name"))
    private Category category;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "student_count"))
    private StudentCount studentCount;

    @Embedded
    private Stars stars;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "instructor_name"))
    private Name instructor;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<HashTag> hashTags = new ArrayList<>();

    public Course() {
    }

    public Course(Title title, ImagePath imagePath, Category category, StudentCount studentCount,
                  Stars stars, Name instructor, Long currentLectureId, List<HashTag> hashTags) {
        this.title = title;
        this.imagePath = imagePath;
        this.category = category;
        this.studentCount = studentCount;
        this.stars = stars;
        this.instructor = instructor;
        this.currentLectureId = new LectureId(currentLectureId);
        this.hashTags = hashTags;
    }

    public static Course fake(String title) {
        return fake(new Title(title));
    }

    private static Course fake(Title title) {
        ImagePath imagePath = new ImagePath("imagePath");
        Category category = new Category("category");
        StudentCount studentCount = new StudentCount(1234L);
        Stars stars = new Stars(4.5D);
        Name instructor = new Name("instructor");
        Long currentLectureId = 1L;
        List<HashTag> hashTags = List.of(new HashTag("category"));

        return new Course(title, imagePath, category, studentCount, stars, instructor, currentLectureId, hashTags);
    }

    public Title title() {
        return title;
    }

    public ImagePath imagePath() {
        return imagePath;
    }

    public MyCourseDto toMyCourseDto() {
        return new MyCourseDto(id, title, imagePath);
    }

    public CourseDto toCourseDto() {
        return new CourseDto(id, category, title, stars, studentCount, instructor, currentLectureId, hashTags);
    }
}

//courseId: productId,
//lectureId: 135,
//category: '개발,프로그래밍 > 백엔드',
//title: '강의 1',
//stars: 5.0,
//studentCount: 1234,
//instructor: '오진성',
//hashTags: ['헛소리 잘하는법', '화나게 하는법'],