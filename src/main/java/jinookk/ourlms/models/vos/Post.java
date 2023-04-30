package jinookk.ourlms.models.vos;

import jinookk.ourlms.dtos.NewsDto;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;
import java.util.Objects;

@Embeddable
public class Post {
    private final String title;
    private final LocalDateTime createdAt;
    private final String content;

    protected Post() {
        this.title = null;
        this.createdAt = null;
        this.content = null;
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

    @Override
    public int hashCode() {
        return Objects.hash(title, createdAt, content);
    }

    @Override
    public boolean equals(Object other) {
        return other != null &&
                other.getClass().equals(Post.class) &&
                ((Post) other).title.equals(this.title) &&
                ((Post) other).createdAt.equals(this.createdAt) &&
                ((Post) other).content.equals(this.content);
    }

    @Override
    public String toString() {
        return "Post title: " + title +
                        ", createdAt: " + createdAt +
                        ", content: " + content;
    }
}
