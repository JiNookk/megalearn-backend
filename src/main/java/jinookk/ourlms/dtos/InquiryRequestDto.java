package jinookk.ourlms.dtos;

import jinookk.ourlms.models.vos.ids.LectureId;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class InquiryRequestDto {
    @NotNull
    private LectureId lectureId;

    @NotNull
    private Long courseId;

    private List<String> hashTags;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private Boolean anonymous;

    private Long minute;

    private Long second;

    public InquiryRequestDto() {
    }

    public InquiryRequestDto(LectureId lectureId, List<String> hashTags, String title, String content,
                             Boolean anonymous, Long minute, Long second, Long courseId) {
        this.lectureId = lectureId;
        this.hashTags = hashTags;
        this.title = title;
        this.content = content;
        this.anonymous = anonymous;
        this.minute = minute;
        this.second = second;
        this.courseId = courseId;
    }

    public List<String> getHashTags() {
        return hashTags;
    }

    public String getContent() {
        return content;
    }

    public Boolean getAnonymous() {
        return anonymous;
    }

    public LectureId getLectureId() {
        return lectureId;
    }

    public String getTitle() {
        return title;
    }

    public Long getMinute() {
        return minute;
    }

    public Long getSecond() {
        return second;
    }

    public Long getCourseId() {
        return courseId;
    }
}
