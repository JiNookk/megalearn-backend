package jinookk.ourlms.applications.note;

import jinookk.ourlms.dtos.NoteDto;
import jinookk.ourlms.dtos.NoteRequestDto;
import jinookk.ourlms.exceptions.AccountNotFound;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Note;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.NoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CreateNoteService {
    private final NoteRepository noteRepository;
    private final AccountRepository accountRepository;

    public CreateNoteService(NoteRepository noteRepository, AccountRepository accountRepository) {
        this.noteRepository = noteRepository;
        this.accountRepository = accountRepository;
    }

    public NoteDto create(NoteRequestDto noteRequestDto, UserName userName) {
        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFound(userName));

        AccountId accountId = new AccountId(account.id());

        Note note = Note.of(noteRequestDto, accountId);

        Note saved = noteRepository.save(note);

        return saved.toNoteDto();
    }
}
