package jinookk.ourlms.exceptions;

import jinookk.ourlms.models.vos.ids.NoteId;

public class NoteNotFound extends RuntimeException {
    public NoteNotFound(NoteId noteId) {
        super("note is not found by id: " + noteId);
    }
}
