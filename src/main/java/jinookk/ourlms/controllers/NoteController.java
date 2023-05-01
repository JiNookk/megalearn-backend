package jinookk.ourlms.controllers;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import jinookk.ourlms.applications.note.CreateNoteService;
import jinookk.ourlms.applications.note.DeleteNoteService;
import jinookk.ourlms.applications.note.GetNoteService;
import jinookk.ourlms.applications.note.UpdateNoteService;
import jinookk.ourlms.dtos.NoteDeleteDto;
import jinookk.ourlms.dtos.NoteDto;
import jinookk.ourlms.dtos.NoteRequestDto;
import jinookk.ourlms.dtos.NoteUpdateDto;
import jinookk.ourlms.dtos.NotesDto;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.LectureId;
import jinookk.ourlms.models.vos.ids.NoteId;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoteController {
    private final GetNoteService getNoteService;
    private final CreateNoteService createNoteService;
    private final UpdateNoteService updateNoteService;
    private final DeleteNoteService deleteNoteService;

    public NoteController(GetNoteService getNoteService,
                          CreateNoteService createNoteService,
                          UpdateNoteService updateNoteService,
                          DeleteNoteService deleteNoteService) {
        this.getNoteService = getNoteService;
        this.createNoteService = createNoteService;
        this.updateNoteService = updateNoteService;
        this.deleteNoteService = deleteNoteService;
    }

    @GetMapping("/lectures/{lectureId}/notes")
    @ApiOperation(value = "Fetches My Notes", notes = "fetches my notes with given lecture")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {access_token}", required = true, dataType = "string", paramType = "header")
    })
    public NotesDto list(
            @RequestAttribute UserName userName,
            @PathVariable Long lectureId
    ) {
        return getNoteService.list(new LectureId(lectureId), userName);
    }

    @GetMapping("/notes/me")
    @ApiOperation(value = "Fetches My Notes", notes = "fetches my entire notes")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {access_token}", required = true, dataType = "string", paramType = "header")
    })
    public NotesDto myNotes(
            @RequestAttribute UserName userName,
            @RequestParam(required = false) String date
    ) {
        return getNoteService.myNotes(userName, date);
    }

    @PostMapping("/notes")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Creates Notes", notes = "creates a note")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {access_token}", required = true, dataType = "string", paramType = "header")
    })
    public NoteDto post(
            @RequestAttribute UserName userName,
            @Validated @RequestBody NoteRequestDto noteRequestDto
    ) {
        return createNoteService.create(noteRequestDto, userName);
    }

    @PatchMapping("/notes/{noteId}")
    public NoteDto update(
            @PathVariable Long noteId,
            @RequestBody NoteUpdateDto noteUpdateDto
    ) {
        return updateNoteService.update(new NoteId(noteId), noteUpdateDto);
    }


    @DeleteMapping("/notes/{noteId}")
    public NoteDeleteDto delete(
            @PathVariable Long noteId
    ) {
        return deleteNoteService.delete(new NoteId(noteId));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String missingProperties() {
        return "Property is Missing";
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userNameRequired(ServletRequestBindingException exception) {
        return exception.getMessage();
    }
}
