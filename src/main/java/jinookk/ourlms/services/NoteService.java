package jinookk.ourlms.services;

import jinookk.ourlms.dtos.NoteDeleteDto;
import jinookk.ourlms.dtos.NoteDto;
import jinookk.ourlms.dtos.NoteRequestDto;
import jinookk.ourlms.dtos.NoteUpdateDto;
import jinookk.ourlms.dtos.NotesDto;
import jinookk.ourlms.exceptions.AccountNotFound;
import jinookk.ourlms.exceptions.NoteNotFound;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Note;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.LectureId;
import jinookk.ourlms.models.vos.ids.NoteId;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.NoteRepository;
import jinookk.ourlms.specifications.NoteSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class NoteService {
    private final NoteRepository noteRepository;
    private final AccountRepository accountRepository;

    public NoteService(NoteRepository noteRepository, AccountRepository accountRepository) {
        this.noteRepository = noteRepository;
        this.accountRepository = accountRepository;
    }

    // TODO : 사용자가 만드는 역할을 하기에 적합할 것 같다.
    public NoteDto create(NoteRequestDto noteRequestDto, UserName userName) {
        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFound(userName));

        AccountId accountId = new AccountId(account.id());

        Note note = Note.of(noteRequestDto, accountId);

        Note saved = noteRepository.save(note);

        return saved.toNoteDto();
    }

    public NotesDto list(LectureId lectureId, UserName userName) {
        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFound(userName));

        AccountId accountId = new AccountId(account.id());

        List<Note> notes = noteRepository.findAllByLectureIdAndAccountId(lectureId, accountId);

        List<NoteDto> noteDtos = notes.stream()
                .map(Note::toNoteDto)
                .toList();

        return new NotesDto(noteDtos);
    }

    public NotesDto myNotes(UserName userName, String date) {
        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFound(userName));

        AccountId accountId = new AccountId(account.id());

        Specification<Note> spec = Specification.where(NoteSpecification.equalAccountId(accountId));

        if (date != null) {
            spec = spec.and(NoteSpecification.betweenCurrentWeek(date));
        }

        List<Note> notes = noteRepository.findAll(spec);

        List<NoteDto> noteDtos = notes.stream()
                .map(Note::toNoteDto)
                .toList();

        return new NotesDto(noteDtos);
    }

    public NoteDeleteDto delete(NoteId noteId) {
        Note note = noteRepository.findById(noteId.value())
                .orElseThrow(() -> new NoteNotFound(noteId));

        note.delete();

        return note.toNoteDeleteDto();
    }

    public NoteDto update(NoteId noteId, NoteUpdateDto noteUpdateDto) {
        Note note = noteRepository.findById(noteId.value())
                .orElseThrow(() -> new NoteNotFound(noteId));

        note.update(noteUpdateDto);

        return note.toNoteDto();
    }
}
