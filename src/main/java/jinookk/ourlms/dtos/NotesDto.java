package jinookk.ourlms.dtos;

import java.util.List;

public class NotesDto {
    private List<NoteDto> notes;

    public NotesDto() {
    }

    public NotesDto(List<NoteDto> notes) {
        this.notes = notes;
    }

    public List<NoteDto> getNotes() {
        return notes;
    }
}
