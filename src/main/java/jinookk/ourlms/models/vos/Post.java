package jinookk.ourlms.models.vos;

// 가져야 하는것 -> 제목, 저자, 작성일자, 내용, 좋아요

import jinookk.ourlms.dtos.NewsDto;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class Post {
    private String title;
    private LocalDateTime createdAt;
    private String content;

//    좋아요는 어떻게 표시하면 좋을까? -> Post가 엔티티가 되어야 할까? 값객체로는 표시할수가 없나?
//    private String

    public Post() {
    }

    public Post(String title, LocalDateTime createdAt, String content) {
        this.title = title;
        this.createdAt = createdAt;
        this.content = content;
    }

    public String title() {
        return title;
    }

    public LocalDateTime createdAt() {
        return createdAt;
    }

    public String content() {
        return content;
    }

    public NewsDto toDto() {
        return new NewsDto(title, createdAt, content);
    }
}
