package jinookk.ourlms.dtos;

import jinookk.ourlms.models.vos.Content;
import jinookk.ourlms.models.vos.HashTag;
import jinookk.ourlms.models.vos.LectureTime;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.models.vos.Title;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.models.vos.ids.LectureId;
import jinookk.ourlms.models.vos.status.InquiryStatus;

import java.time.LocalDateTime;
import java.util.List;

public class InquiryDto {
    private Long id;
    private Long lectureId;
    private Long courseId;
    private List<String> hashTag;
    private InquiryStatusDto status;
    private String title;
    private Integer hits;
    private String content;
    private String publisher;
    private LocalDateTime publishTime;
    private LectureTimeDto lectureTime;

    public InquiryDto() {
    }

    public InquiryDto(Long id, LectureId lectureId, CourseId courseId, List<HashTag> hashTag, InquiryStatus status, Title title,
                      Integer hits, Content content, Name publisher, LocalDateTime publishTime, LectureTime lectureTime) {
        this.id = id;
        this.lectureId = lectureId.value();
        this.courseId = courseId.value();
        this.hashTag = hashTag.stream().map(HashTag::tagName).toList();
        this.status = new InquiryStatusDto(status.value(), status.replied(), status.solved());
        this.title = title.value();
        this.hits = hits;
        this.content = content.value();
        this.publisher = publisher.value();
        this.publishTime = publishTime;
        this.lectureTime = new LectureTimeDto(lectureTime);
    }

    public Long getId() {
        return id;
    }

    public List<String> getHashTag() {
        return hashTag;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getPublishTime() {
        return publishTime;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getTitle() {
        return title;
    }

    public LectureTimeDto getLectureTime() {
        return lectureTime;
    }

    public InquiryStatusDto getStatus() {
        return status;
    }

    public Long getLectureId() {
        return lectureId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public Integer getHits() {
        return hits;
    }
}
