package jinookk.ourlms.dtos;

public class CommentDeleteDto {
    private Long commentId;

    public CommentDeleteDto() {
    }

    public CommentDeleteDto(Long commentId) {
        this.commentId = commentId;
    }

    public Long getCommentId() {
        return commentId;
    }
}
