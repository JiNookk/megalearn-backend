package jinookk.ourlms.dtos;

public class CommentUpdateDto {
    private String content;

    public CommentUpdateDto() {
    }

    public CommentUpdateDto(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
