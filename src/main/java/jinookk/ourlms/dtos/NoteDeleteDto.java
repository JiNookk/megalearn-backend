package jinookk.ourlms.dtos;

public class NoteDeleteDto {
    private Long noteId;

    public NoteDeleteDto() {
    }

    public NoteDeleteDto(Long noteId) {
        this.noteId = noteId;
    }

    public Long getNoteId() {
        return noteId;
    }
}
