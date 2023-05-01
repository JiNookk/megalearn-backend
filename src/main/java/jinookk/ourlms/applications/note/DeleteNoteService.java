package jinookk.ourlms.applications.note;

import jinookk.ourlms.dtos.NoteDeleteDto;
import jinookk.ourlms.exceptions.NoteNotFound;
import jinookk.ourlms.models.entities.Note;
import jinookk.ourlms.models.vos.ids.NoteId;
import jinookk.ourlms.repositories.NoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class DeleteNoteService {
    private final NoteRepository noteRepository;

    public DeleteNoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public NoteDeleteDto delete(NoteId noteId) {
        Note note = noteRepository.findById(noteId.value())
                .orElseThrow(() -> new NoteNotFound(noteId));

        note.delete();

        return note.toNoteDeleteDto();
    }
}
