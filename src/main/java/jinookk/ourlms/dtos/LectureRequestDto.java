package jinookk.ourlms.dtos;

public class LectureRequestDto {
    private Long courseId;
    private Long sectionId;
    private String title;
    private Integer minute;
    private Integer second;

    public LectureRequestDto() {
    }

    public LectureRequestDto(Long courseId, Long sectionId, String title, Integer minute, Integer second) {
        this.courseId = courseId;
        this.sectionId = sectionId;
        this.title = title;
        this.minute = minute;
        this.second = second;
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

    public Integer getMinute() {
        return minute;
    }

    public Integer getSecond() {
        return second;
    }
}
