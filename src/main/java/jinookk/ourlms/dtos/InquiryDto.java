package jinookk.ourlms.dtos;

import jinookk.ourlms.models.vos.Content;
import jinookk.ourlms.models.vos.HashTag;
import jinookk.ourlms.models.vos.LectureTime;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.models.vos.Title;

import java.time.LocalDateTime;
import java.util.List;

public class InquiryDto {
    private Long id;
    private List<String> hashTag;
    private String title;
    private String content;
    private String publisher;
    private LocalDateTime publishTime;
    private LectureTimeDto lectureTime;

    public InquiryDto() {
    }

    public InquiryDto(Long id, List<HashTag> hashTag, Title title, Content content, Name publisher,
                      LocalDateTime publishTime, LectureTime lectureTime) {
        this.id = id;
        this.hashTag = hashTag.stream().map(HashTag::tagName).toList();
        this.title = title.value();
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
}
