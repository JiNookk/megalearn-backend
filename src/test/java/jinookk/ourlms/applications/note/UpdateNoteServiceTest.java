package jinookk.ourlms.applications.note;

import jinookk.ourlms.dtos.NoteDto;
import jinookk.ourlms.dtos.NoteUpdateDto;
import jinookk.ourlms.fixtures.Fixture;
import jinookk.ourlms.models.entities.Note;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.LectureId;
import jinookk.ourlms.models.vos.ids.NoteId;
import jinookk.ourlms.repositories.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class UpdateNoteServiceTest {
    UpdateNoteService updateNoteService;
    NoteRepository noteRepository;

    @BeforeEach
    void setup() {
        noteRepository = mock(NoteRepository.class);
        updateNoteService = new UpdateNoteService(noteRepository);

        Note note = Fixture.note("content");
        given(noteRepository.save(any())).willReturn(note);

        given(noteRepository.findById(any())).willReturn(Optional.of(note));

        given(noteRepository.findAllByLectureIdAndAccountId(new LectureId(1L), new AccountId(1L)))
                .willReturn(List.of(note));
    }

    @Test
    void update() {
        NoteId noteId = new NoteId(1L);
        NoteUpdateDto updated = new NoteUpdateDto("updated");

        NoteDto noteDto = updateNoteService.update(noteId, updated);

        assertThat(noteDto).isNotNull();
        assertThat(noteDto.getContent()).isEqualTo("updated");
    }
}
