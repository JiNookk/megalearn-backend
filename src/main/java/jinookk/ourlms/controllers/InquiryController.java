package jinookk.ourlms.controllers;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import jinookk.ourlms.applications.inquiry.CreateInquiryService;
import jinookk.ourlms.applications.inquiry.DeleteInquiryService;
import jinookk.ourlms.applications.inquiry.GetInquiryService;
import jinookk.ourlms.applications.inquiry.UpdateInquiryService;
import jinookk.ourlms.dtos.InquiriesDto;
import jinookk.ourlms.dtos.InquiryDeleteDto;
import jinookk.ourlms.dtos.InquiryDto;
import jinookk.ourlms.dtos.InquiryFilterDto;
import jinookk.ourlms.dtos.InquiryRequestDto;
import jinookk.ourlms.dtos.InquiryUpdateDto;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.models.vos.ids.InquiryId;
import jinookk.ourlms.models.vos.ids.LectureId;
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
public class InquiryController {
    private final GetInquiryService getInquiryService;
    private final CreateInquiryService createInquiryService;
    private final UpdateInquiryService updateInquiryService;
    private final DeleteInquiryService deleteInquiryService;

    public InquiryController(GetInquiryService getInquiryService,
                             CreateInquiryService createInquiryService,
                             UpdateInquiryService updateInquiryService,
                             DeleteInquiryService deleteInquiryService) {
        this.getInquiryService = getInquiryService;
        this.createInquiryService = createInquiryService;
        this.updateInquiryService = updateInquiryService;
        this.deleteInquiryService = deleteInquiryService;
    }

    @PostMapping("/inquiries")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create Inquiry", notes = "create inquiry")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {access_token}", required = true, dataType = "string", paramType = "header")
    })
    public InquiryDto post(
            @RequestAttribute UserName userName,
            @Validated @RequestBody InquiryRequestDto inquiryRequestDto
    ) {
        return createInquiryService.create(inquiryRequestDto, userName);
    }

    @GetMapping("/inquiries/{inquiryId}")
    public InquiryDto inquiry(
            @PathVariable Long inquiryId
    ) {
        return getInquiryService.detail(new InquiryId(inquiryId));
    }

    @GetMapping("/inquiries")
    @ApiOperation(value = "Create Inquiry", notes = "create inquiry")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {access_token}", required = false, dataType = "string", paramType = "header")
    })
    public InquiriesDto listWithInstructorId(
            @RequestAttribute(required = false) UserName userName,
            @RequestParam(required = false) Long courseId,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String order
    ) {
        return getInquiryService.list(userName,
                new InquiryFilterDto(courseId, type, order));
    }

    @GetMapping("/inquiries/me")
    public InquiriesDto myInquiries(
            @RequestAttribute UserName userName
    ) {
        return getInquiryService.myInquiries(userName);
    }

    @GetMapping("/lectures/{lectureId}/inquiries")
    public InquiriesDto list(
            @PathVariable Long lectureId,
            @RequestParam(required = false) Long lectureTime,
            @RequestParam(required = false) String content
    ) {
        return getInquiryService.list(new LectureId(lectureId), lectureTime, content);
    }

    @GetMapping("/courses/{courseId}/inquiries")
    public InquiriesDto listByCourseId(
            @PathVariable Long courseId
    ) {
        return getInquiryService.listByCourseId(new CourseId(courseId));
    }

    @PatchMapping("/inquiries/{inquiryId}")
    public InquiryDto update(
            @PathVariable Long inquiryId,
            @RequestBody InquiryUpdateDto inquiryUpdateDto
    ) {
        return updateInquiryService.update(new InquiryId(inquiryId), inquiryUpdateDto);
    }

    @PatchMapping("/inquiries/{inquiryId}/solved")
    public InquiryDto toggle(
            @PathVariable Long inquiryId
    ) {
        return updateInquiryService.toggleSolved(new InquiryId(inquiryId));
    }

    @PatchMapping("/inquiries/{inquiryId}/hits")
    public InquiryDto increaseHits(
            @PathVariable Long inquiryId
    ) {
        return updateInquiryService.increaseHits(new InquiryId(inquiryId));
    }

    @DeleteMapping("/inquiries/{inquiryId}")
    public InquiryDeleteDto delete(
            @PathVariable Long inquiryId
    ) {
        return deleteInquiryService.delete(new InquiryId(inquiryId));
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
