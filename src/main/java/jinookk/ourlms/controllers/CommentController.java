package jinookk.ourlms.controllers;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import jinookk.ourlms.applications.comment.CreateCommentService;
import jinookk.ourlms.applications.comment.DeleteCommentService;
import jinookk.ourlms.applications.comment.GetCommentService;
import jinookk.ourlms.applications.comment.UpdateCommentService;
import jinookk.ourlms.dtos.CommentDeleteDto;
import jinookk.ourlms.dtos.CommentDto;
import jinookk.ourlms.dtos.CommentRequestDto;
import jinookk.ourlms.dtos.CommentUpdateDto;
import jinookk.ourlms.dtos.CommentsDto;
import jinookk.ourlms.exceptions.AccountNotFound;
import jinookk.ourlms.exceptions.CommentNotFound;
import jinookk.ourlms.exceptions.CourseNotFound;
import jinookk.ourlms.exceptions.InquiryNotFound;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.InquiryId;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {
    private final GetCommentService getCommentService;
    private final CreateCommentService createCommentService;
    private final UpdateCommentService updateCommentService;
    private final DeleteCommentService deleteCommentService;

    public CommentController(GetCommentService getCommentService,
                             CreateCommentService createCommentService,
                             UpdateCommentService updateCommentService,
                             DeleteCommentService deleteCommentService) {
        this.getCommentService = getCommentService;
        this.createCommentService = createCommentService;
        this.updateCommentService = updateCommentService;
        this.deleteCommentService = deleteCommentService;
    }

    @GetMapping("inquiries/{inquiryId}/comments")
    @ApiOperation(value = "Fetch Comment Lists", notes = "fetches comments in inquiry")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {access_token}", required = true, dataType = "string", paramType = "header")
    })
    public CommentsDto list(
            @PathVariable Long inquiryId,
            @RequestAttribute UserName userName
            ) {
        return getCommentService.list(new InquiryId(inquiryId), userName);
    }

    @PostMapping("comments")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Post Comment", notes = "post comment")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {access_token}", required = true, dataType = "string", paramType = "header")
    })
    public CommentDto post(
            @Validated @RequestBody CommentRequestDto commentRequestDto,
            @RequestAttribute UserName userName
            ) {
        return createCommentService.create(commentRequestDto, userName);
    }

    @PatchMapping("comments/{inquiryId}")
    @ApiOperation(value = "Update Comment", notes = "update comment with given id")
    @ApiResponse(code = 403, message = "no authority", response = AccessDeniedException.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {access_token}", required = true, dataType = "string", paramType = "header")
    })
    public CommentDto update(
            @PathVariable Long inquiryId,
            @RequestBody CommentUpdateDto commentUpdateDto,
            @RequestAttribute UserName userName
    ) {
        return updateCommentService.update(inquiryId, commentUpdateDto.getContent(), userName);
    }

    @DeleteMapping("comments/{inquiryId}")
    @ApiOperation(value = "Delete Comment", notes = "delete comment with given id")
    @ApiResponse(code = 403, message = "no authority", response = AccessDeniedException.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {access_token}", required = true, dataType = "string", paramType = "header")
    })
    public CommentDeleteDto delete(
            @PathVariable Long inquiryId,
            @RequestAttribute UserName userName
            ) {
        return deleteCommentService.delete(inquiryId, userName);
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userNameRequired(ServletRequestBindingException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(InquiryNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String inquiryNotFound(InquiryNotFound exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(AccountNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String accountNotFound(AccountNotFound exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(CourseNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String courseNotFound(CourseNotFound exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(CommentNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String commentNotFound(CommentNotFound exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String accessDenied(AccessDeniedException exception) {
        return exception.getMessage();
    }
}
