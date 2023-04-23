package jinookk.ourlms.controllers;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import jinookk.ourlms.dtos.CommentDeleteDto;
import jinookk.ourlms.dtos.CommentDto;
import jinookk.ourlms.dtos.CommentRequestDto;
import jinookk.ourlms.dtos.CommentUpdateDto;
import jinookk.ourlms.dtos.CommentsDto;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.InquiryId;
import jinookk.ourlms.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("inquiries/{inquiryId}/comments")
    @ApiOperation(value = "Fetch Comment Lists", notes = "fetches comments in inquiry")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {access_token}", required = true, dataType = "string", paramType = "header")
    })
    public CommentsDto list(
            @PathVariable Long inquiryId,
            @RequestAttribute Name userName
            ) {
        return commentService.list(new InquiryId(inquiryId), userName);
    }

    @PostMapping("comments")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Post Comment", notes = "post comment")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {access_token}", required = true, dataType = "string", paramType = "header")
    })
    public CommentDto post(
            @Validated @RequestBody CommentRequestDto commentRequestDto,
            @RequestAttribute Name userName
            ) {
        return commentService.create(commentRequestDto, userName);
    }

    @PatchMapping("comments/{inquiryId}")
    public CommentDto update(
            @PathVariable Long inquiryId,
            @RequestBody CommentUpdateDto commentUpdateDto
    ) {
        return commentService.update(inquiryId, commentUpdateDto.getContent());
    }

    @DeleteMapping("comments/{inquiryId}")
    public CommentDeleteDto delete(
            @PathVariable Long inquiryId
            ) {
        return commentService.delete(inquiryId);
    }
}
