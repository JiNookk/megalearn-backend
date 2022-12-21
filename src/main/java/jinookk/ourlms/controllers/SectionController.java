package jinookk.ourlms.controllers;

import jinookk.ourlms.dtos.SectionsDto;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.services.SectionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SectionController {
    private final SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @GetMapping("/courses/{courseId}/sections")
    public SectionsDto list(
            @RequestAttribute("accountId") Long accountId,
            @PathVariable Long courseId
    ) {
        return sectionService.list(new CourseId(courseId), new AccountId(accountId));
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public String missingProperties() {
//        return "Property is Missing";
//    }
}
