package jinookk.ourlms.services;

import jinookk.ourlms.dtos.LectureTimeDto;
import jinookk.ourlms.dtos.NoteDeleteDto;
import jinookk.ourlms.dtos.NoteDto;
import jinookk.ourlms.dtos.NoteRequestDto;
import jinookk.ourlms.dtos.NoteUpdateDto;
import jinookk.ourlms.dtos.NotesDto;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Note;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.LectureId;
import jinookk.ourlms.models.vos.LectureTime;
import jinookk.ourlms.models.vos.ids.NoteId;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class NoteServiceTest {
    NoteService noteService;
    NoteRepository noteRepository;
    AccountRepository accountRepository;

    @BeforeEach
    void setup() {
        accountRepository = mock(AccountRepository.class);
        noteRepository = mock(NoteRepository.class);
        noteService = new NoteService(noteRepository, accountRepository);

        Note note = Note.fake("content");
        given(noteRepository.save(any())).willReturn(note);

        given(noteRepository.findById(any())).willReturn(Optional.of(note));

        given(noteRepository.findAllByLectureIdAndAccountId(new LectureId(1L), new AccountId(1L)))
                .willReturn(List.of(note));

        Account account = Account.fake("account");
        given(accountRepository.findByUserName(any())).willReturn(Optional.of(account));
    }

    @Test
    void create() {
        String content = "content";
        long lectureId = 1L;
        LectureTimeDto lectureTimeDto = new LectureTimeDto(new LectureTime(1, 24));
        NoteRequestDto noteRequestDto = new NoteRequestDto(content, lectureId, lectureTimeDto);
        Name userName = new Name("userName");

        NoteDto noteDto = noteService.create(noteRequestDto, userName);

        assertThat(noteDto).isNotNull();
        assertThat(noteDto.getLectureTime().getMinute()).isEqualTo(1L);
    }

    @Test
    void list() {
        NotesDto notesDto = noteService.list(new LectureId(1L), new Name("userName"));

        assertThat(notesDto.getNotes()).hasSize(1);
    }

    @Test
    void update() {
        NoteId noteId = new NoteId(1L);
        NoteUpdateDto updated = new NoteUpdateDto("updated");

        NoteDto noteDto = noteService.update(noteId, updated);

        assertThat(noteDto).isNotNull();
        assertThat(noteDto.getContent()).isEqualTo("updated");
    }

    @Test
    void delete() {
        NoteDeleteDto noteDeleteDto = noteService.delete(new NoteId(1L));

        assertThat(noteDeleteDto.getNoteId()).isEqualTo(1L);
    }
}
