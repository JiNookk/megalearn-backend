package jinookk.ourlms.controllers;

import jinookk.ourlms.dtos.NoteDeleteDto;
import jinookk.ourlms.dtos.NoteDto;
import jinookk.ourlms.dtos.NoteRequestDto;
import jinookk.ourlms.dtos.NoteUpdateDto;
import jinookk.ourlms.dtos.NotesDto;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.LectureId;
import jinookk.ourlms.models.vos.ids.NoteId;
import jinookk.ourlms.services.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/lectures/{lectureId}/notes")
    public NotesDto list(
            @RequestAttribute("accountId") Long accountId,
            @PathVariable Long lectureId
    ) {
        return noteService.list(new LectureId(lectureId), new AccountId(accountId));
    }

    @GetMapping("/notes/me")
    public NotesDto myNotes(
            @RequestAttribute Long accountId,
            @RequestParam(required = false) String date
    ) {
        return noteService.myNotes(new AccountId(accountId), date);
    }

    @PostMapping("/notes")
    @ResponseStatus(HttpStatus.CREATED)
    public NoteDto post(
            @RequestAttribute("accountId") Long accountId,
            @Validated @RequestBody NoteRequestDto noteRequestDto
    ) {
        return noteService.create(noteRequestDto, new AccountId(accountId));
    }

    @PatchMapping("/notes/{noteId}")
    public NoteDto update(
            @PathVariable Long noteId,
            @RequestBody NoteUpdateDto noteUpdateDto
    ) {
        return noteService.update(new NoteId(noteId), noteUpdateDto);
    }


    @DeleteMapping("/notes/{noteId}")
    public NoteDeleteDto delete(
            @PathVariable Long noteId
    ) {
        return noteService.delete(new NoteId(noteId));
    }


//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public String missingProperties() {
//        return "Property is Missing";
//    }
}
