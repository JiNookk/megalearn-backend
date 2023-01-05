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

    private Integer minute;

    private Integer second;

    public InquiryRequestDto() {
    }

    public InquiryRequestDto(LectureId lectureId, List<String> hashTags, String title, String content,
                             Boolean anonymous, Integer minute, Integer second, Long courseId) {
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

    public Integer getMinute() {
        return minute;
    }

    public Integer getSecond() {
        return second;
    }

    public Long getCourseId() {
        return courseId;
    }
}
