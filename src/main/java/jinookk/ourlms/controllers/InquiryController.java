package jinookk.ourlms.controllers;

import jinookk.ourlms.dtos.InquiriesDto;
import jinookk.ourlms.dtos.InquiryDeleteDto;
import jinookk.ourlms.dtos.InquiryDto;
import jinookk.ourlms.dtos.InquiryFilterDto;
import jinookk.ourlms.dtos.InquiryRequestDto;
import jinookk.ourlms.dtos.InquiryUpdateDto;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.models.vos.ids.InquiryId;
import jinookk.ourlms.models.vos.ids.LectureId;
import jinookk.ourlms.services.InquiryService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
public class InquiryController {
    private final InquiryService inquiryService;

    public InquiryController(InquiryService inquiryService) {
        this.inquiryService = inquiryService;
    }

    @PostMapping("/inquiries")
    @ResponseStatus(HttpStatus.CREATED)
    public InquiryDto post(
            @RequestAttribute Name userName,
            @Validated @RequestBody InquiryRequestDto inquiryRequestDto
    ) {
        return inquiryService.create(inquiryRequestDto, userName);
    }

    @GetMapping("/inquiries/{inquiryId}")
    public InquiryDto inquiry(
            @PathVariable Long inquiryId
    ) {
        return inquiryService.detail(new InquiryId(inquiryId));
    }

    @GetMapping("/inquiries")
    public InquiriesDto listWithInstructorId(
            @RequestAttribute(required = false) Name userName,
            @RequestParam(required = false) Long courseId,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String order
    ) {
        return inquiryService.list(userName,
                new InquiryFilterDto(courseId, type, order));
    }

    @GetMapping("/inquiries/me")
    public InquiriesDto myInquiries(
            @RequestAttribute Name userName
    ) {
        return inquiryService.myInquiries(userName);
    }

    @GetMapping("/lectures/{lectureId}/inquiries")
    public InquiriesDto list(
            @PathVariable Long lectureId,
            @RequestParam(required = false) Long lectureTime,
            @RequestParam(required = false) String content
    ) {
        return inquiryService.list(new LectureId(lectureId), lectureTime, content);
    }

    @GetMapping("/courses/{courseId}/inquiries")
    public InquiriesDto listByCourseId(
            @PathVariable Long courseId
    ) {
        return inquiryService.listByCourseId(new CourseId(courseId));
    }

    @PatchMapping("/inquiries/{inquiryId}")
    public InquiryDto update(
            @PathVariable Long inquiryId,
            @RequestBody InquiryUpdateDto inquiryUpdateDto
    ) {
        return inquiryService.update(new InquiryId(inquiryId), inquiryUpdateDto);
    }

    @PatchMapping("/inquiries/{inquiryId}/solved")
    public InquiryDto toggle(
            @PathVariable Long inquiryId
    ) {
        return inquiryService.toggleSolved(new InquiryId(inquiryId));
    }

    @PatchMapping("/inquiries/{inquiryId}/hits")
    public InquiryDto increaseHits(
            @PathVariable Long inquiryId
    ) {
        return inquiryService.increaseHits(new InquiryId(inquiryId));
    }

    @DeleteMapping("/inquiries/{inquiryId}")
    public InquiryDeleteDto delete(
            @PathVariable Long inquiryId
    ) {
        return inquiryService.delete(new InquiryId(inquiryId));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String missingProperties() {
        return "Property is Missing";
    }
}
