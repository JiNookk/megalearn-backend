package jinookk.ourlms.dtos;

import jinookk.ourlms.models.vos.Content;
import jinookk.ourlms.models.vos.Name;

import java.time.LocalDateTime;

public class CommentDto {
    private Boolean myComment;
    private Long id;
    private String author;
    private String content;
    private LocalDateTime publishTime;

    public CommentDto() {
    }

    public CommentDto(Long id, Name author, Content content, LocalDateTime publishTime) {
        this.id = id;
        this.author = author.value();
        this.content = content.value();
        this.publishTime = publishTime;
    }

    public CommentDto(Long id, Name author, Content content, LocalDateTime publishTime, Boolean myComment) {
        this.id = id;
        this.author = author.value();
        this.content = content.value();
        this.publishTime = publishTime;
        this.myComment = myComment;
    }

    public Long getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getPublishTime() {
        return publishTime;
    }

    public Boolean getMyComment() {
        return myComment;
    }
}
