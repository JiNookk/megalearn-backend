package jinookk.ourlms.dtos;

public class NoteUpdateDto {
    private String content;

    public NoteUpdateDto() {
    }

    public NoteUpdateDto(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
