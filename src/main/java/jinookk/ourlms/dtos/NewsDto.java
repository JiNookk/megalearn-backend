package jinookk.ourlms.dtos;

import java.time.LocalDateTime;

public class NewsDto {
    private String title;
    private LocalDateTime createdAt;
    private String content;

    public NewsDto() {
    }

    public NewsDto(String title, LocalDateTime createdAt, String content) {
        this.title = title;
        this.createdAt = createdAt;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getContent() {
        return content;
    }
}
