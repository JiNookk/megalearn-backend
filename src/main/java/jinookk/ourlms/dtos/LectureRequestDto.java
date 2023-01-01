package jinookk.ourlms.dtos;

public class LectureRequestDto {
    private Long courseId;
    private Long sectionId;
    private String title;

    public LectureRequestDto() {
    }

    public LectureRequestDto(Long courseId, Long sectionId, String title) {
        this.courseId = courseId;
        this.sectionId = sectionId;
        this.title = title;
    }

    public Long getCourseId() {
        return courseId;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public String getTitle() {
        return title;
    }
}
