package jinookk.ourlms.models.entities;

import jinookk.ourlms.dtos.SectionDto;
import jinookk.ourlms.dtos.ProgressDto;
import jinookk.ourlms.models.vos.Title;
import jinookk.ourlms.models.vos.ids.CourseId;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Entity
public class Section {
    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "course_id"))
    private CourseId courseId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "title"))
    private Title title;

    public Section() {
    }

    public Section(Long id, CourseId courseId, Title title) {
        this.id = id;
        this.courseId = courseId;
        this.title = title;
    }

    public Long id() {
        return id;
    }

    public CourseId courseId() {
        return courseId;
    }

    public Title title() {
        return title;
    }

    public static Section fake(String title) {
        return fake(new Title(title));
    }

    private static Section fake(Title title) {
        return new Section(1L, new CourseId(1L), title);
    }

    public SectionDto toSectionDto(List<Progress> progresses) {
        if (progresses == null) {
            return new SectionDto(id, title, List.of());
        }

        List<ProgressDto> progressDtos = progresses.stream()
                .map(Progress::toDto)
                .toList();

        return new SectionDto(id, title, progressDtos);
    }
}
