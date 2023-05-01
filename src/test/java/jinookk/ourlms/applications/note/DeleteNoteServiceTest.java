package jinookk.ourlms.applications.note;

import jinookk.ourlms.dtos.NoteDeleteDto;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Note;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.LectureId;
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

class DeleteNoteServiceTest {
    DeleteNoteService deleteNoteService;
    NoteRepository noteRepository;
    AccountRepository accountRepository;

    @BeforeEach
    void setup() {
        accountRepository = mock(AccountRepository.class);
        noteRepository = mock(NoteRepository.class);
        deleteNoteService = new DeleteNoteService(noteRepository);

        Note note = Note.fake("content");
        given(noteRepository.save(any())).willReturn(note);

        given(noteRepository.findById(any())).willReturn(Optional.of(note));

        given(noteRepository.findAllByLectureIdAndAccountId(new LectureId(1L), new AccountId(1L)))
                .willReturn(List.of(note));

        Account account = Account.fake("account");
        given(accountRepository.findByUserName(any())).willReturn(Optional.of(account));
    }

    @Test
    void delete() {
        NoteDeleteDto noteDeleteDto = deleteNoteService.delete(new NoteId(1L));

        assertThat(noteDeleteDto.getNoteId()).isEqualTo(1L);
    }
}
