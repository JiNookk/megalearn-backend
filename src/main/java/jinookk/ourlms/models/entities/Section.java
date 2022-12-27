package jinookk.ourlms.models.entities;

import jinookk.ourlms.dtos.SectionDto;
import jinookk.ourlms.dtos.SectionRequestDto;
import jinookk.ourlms.dtos.SectionUpdateRequestDto;
import jinookk.ourlms.dtos.SectionWithProgressDto;
import jinookk.ourlms.dtos.ProgressDto;
import jinookk.ourlms.models.vos.Content;
import jinookk.ourlms.models.vos.status.Status;
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
    @AttributeOverride(name = "value", column = @Column(name = "status"))
    private Status status;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "title"))
    private Title title;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "goal"))
    private Content content;

    public Section() {
    }

    public Section(Long id, CourseId courseId, Status status, Title title, Content content) {
        this.id = id;
        this.courseId = courseId;
        this.status = status;
        this.title = title;
        this.content = content;
    }

    public static Section of(SectionRequestDto sectionRequestDto) {
        return new Section(
                null,
                new CourseId(sectionRequestDto.getCourseId()),
                new Status(Status.CREATED),
                new Title(sectionRequestDto.getTitle()),
                new Content(sectionRequestDto.getGoal())
        );
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
        return new Section(1L, new CourseId(1L), new Status(Status.CREATED), title, new Content("goal"));
    }

    public SectionWithProgressDto toSectionWithProgressDto(List<Progress> progresses) {
        if (progresses == null) {
            return new SectionWithProgressDto(id, title, List.of());
        }

        List<ProgressDto> progressDtos = progresses.stream()
                .map(Progress::toDto)
                .toList();

        return new SectionWithProgressDto(id, title, progressDtos);
    }

    public SectionDto toSectionDto() {
        return new SectionDto(id, courseId, title, content);
    }

    public void update(SectionUpdateRequestDto sectionUpdateRequestDto) {
        title.update(sectionUpdateRequestDto.getTitle());
        content.update(sectionUpdateRequestDto.getGoal());
    }

    public void delete() {
        title.delete();
        content.delete();
        status.delete();
        courseId.delete();
    }
}
