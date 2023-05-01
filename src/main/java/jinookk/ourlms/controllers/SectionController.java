package jinookk.ourlms.controllers;

import jinookk.ourlms.applications.section.CreateSectionService;
import jinookk.ourlms.applications.section.DeleteSectionService;
import jinookk.ourlms.applications.section.GetSectionService;
import jinookk.ourlms.applications.section.UpdateSectionService;
import jinookk.ourlms.dtos.SectionDto;
import jinookk.ourlms.dtos.SectionRequestDto;
import jinookk.ourlms.dtos.SectionUpdateRequestDto;
import jinookk.ourlms.dtos.SectionsDto;
import jinookk.ourlms.models.vos.ids.CourseId;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SectionController {
    private final GetSectionService getSectionService;
    private final CreateSectionService createSectionService;
    private final UpdateSectionService updateSectionService;
    private final DeleteSectionService deleteSectionService;

    public SectionController(GetSectionService getSectionService,
                             CreateSectionService createSectionService,
                             UpdateSectionService updateSectionService,
                             DeleteSectionService deleteSectionService) {
        this.getSectionService = getSectionService;
        this.createSectionService = createSectionService;
        this.updateSectionService = updateSectionService;
        this.deleteSectionService = deleteSectionService;
    }

    @PostMapping("/sections")
    public SectionDto create(
            @Validated @RequestBody SectionRequestDto sectionRequestDto
    ) {
        return createSectionService.create(sectionRequestDto);
    }

    @GetMapping("/sections")
    public SectionsDto list(
    ) {
        return getSectionService.list();
    }

    @GetMapping("/courses/{courseId}/sections")
    public SectionsDto listByCourseId(
            @PathVariable Long courseId
    ) {
        return getSectionService.listByCourseId(new CourseId(courseId));
    }

//    @GetMapping("/courses/{courseId}/sections")
//    public SectionsWithProgressDto listByCourseId(
//            @RequestAttribute("accountId") Long accountId,
//            @PathVariable Long courseId
//    ) {
//        return getSectionService.listWithProgress(new CourseId(courseId), new AccountId(accountId));
//    }

    @PatchMapping("/sections/{sectionId}")
    public SectionDto update(
            @Validated @RequestBody SectionUpdateRequestDto sectionUpdateRequestDto,
            @PathVariable Long sectionId
    ) {
        return updateSectionService.update(sectionId, sectionUpdateRequestDto);
    }

    @DeleteMapping("/sections/{sectionId}")
    public SectionDto delete(
            @PathVariable Long sectionId
    ) {
        return deleteSectionService.delete(sectionId);
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
