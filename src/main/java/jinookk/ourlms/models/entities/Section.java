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
    private Content goal;

    public Section() {
    }

    public Section(Long id, CourseId courseId, Status status, Title title, Content goal) {
        this.id = id;
        this.courseId = courseId;
        this.status = status;
        this.title = title;
        this.goal = goal;
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

    public Content goal() {
        return goal;
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
        return new SectionDto(id, courseId, title, goal, status.value());
    }

    public void update(SectionUpdateRequestDto sectionUpdateRequestDto) {
        title = new Title(sectionUpdateRequestDto.getTitle());
        goal = new Content(sectionUpdateRequestDto.getGoal());
    }

    public void delete() {
        status.delete();
    }
}
