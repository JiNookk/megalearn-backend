package jinookk.ourlms.applications.note;

import jinookk.ourlms.dtos.NoteDto;
import jinookk.ourlms.dtos.NoteUpdateDto;
import jinookk.ourlms.exceptions.NoteNotFound;
import jinookk.ourlms.models.entities.Note;
import jinookk.ourlms.models.vos.ids.NoteId;
import jinookk.ourlms.repositories.NoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UpdateNoteService {
    private final NoteRepository noteRepository;

    public UpdateNoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public NoteDto update(NoteId noteId, NoteUpdateDto noteUpdateDto) {
        Note note = noteRepository.findById(noteId.value())
                .orElseThrow(() -> new NoteNotFound(noteId));

        note.update(noteUpdateDto);

        return note.toNoteDto();
    }
}
