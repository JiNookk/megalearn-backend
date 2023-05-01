package jinookk.ourlms.controllers;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import jinookk.ourlms.applications.like.GetLikeService;
import jinookk.ourlms.dtos.LikeDto;
import jinookk.ourlms.dtos.LikesDto;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.models.vos.ids.LikeId;
import jinookk.ourlms.applications.like.UpdateLikeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LikeController {
    private final GetLikeService getLikeService;
    private final UpdateLikeService updateLikeService;

    public LikeController(GetLikeService getLikeService, UpdateLikeService updateLikeService) {
        this.getLikeService = getLikeService;
        this.updateLikeService = updateLikeService;
    }

    @GetMapping("/likes")
    public LikesDto list() {
        return getLikeService.list();
    }

    @GetMapping("/courses/{courseId}/likes/me")
    @ApiOperation(value = "Fetches like", notes = "fetches my like about given course")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {access_token}", required = true, dataType = "string", paramType = "header")
    })
    public LikeDto like(
            @RequestAttribute UserName userName,
            @PathVariable Long courseId
    ) {
        return getLikeService.detail(userName, new CourseId(courseId));
    }

    @PatchMapping("/likes/{likeId}")
    public LikeDto toggle(
            @PathVariable Long likeId
    ) {
        return updateLikeService.toggle(new LikeId(likeId));
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
