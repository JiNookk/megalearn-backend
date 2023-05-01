package jinookk.ourlms.applications.note;

import jinookk.ourlms.dtos.LectureTimeDto;
import jinookk.ourlms.dtos.NoteDto;
import jinookk.ourlms.dtos.NoteRequestDto;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Note;
import jinookk.ourlms.models.vos.LectureTime;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.LectureId;
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

class CreateNoteServiceTest {
    CreateNoteService createNoteService;
    NoteRepository noteRepository;
    AccountRepository accountRepository;

    @BeforeEach
    void setup() {
        accountRepository = mock(AccountRepository.class);
        noteRepository = mock(NoteRepository.class);
        createNoteService = new CreateNoteService(noteRepository, accountRepository);

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
        UserName userName = new UserName("userName@email.com");

        NoteDto noteDto = createNoteService.create(noteRequestDto, userName);

        assertThat(noteDto).isNotNull();
        assertThat(noteDto.getLectureTime().getMinute()).isEqualTo(1L);
    }
}
