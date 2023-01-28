package jinookk.ourlms.controllers;

import jinookk.ourlms.dtos.LikeDto;
import jinookk.ourlms.dtos.LikesDto;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.models.vos.ids.LikeId;
import jinookk.ourlms.services.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LikeController {
    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping("/likes")
    public LikesDto list() {
        return likeService.list();
    }

    @GetMapping("/courses/{courseId}/likes/me")
    public LikeDto like(
            @RequestAttribute Name userName,
            @PathVariable Long courseId
    ) {
        return likeService.detail(userName, new CourseId(courseId));
    }

    @PatchMapping("/likes/{likeId}")
    public LikeDto toggle(
            @PathVariable Long likeId
    ) {
        return likeService.toggle(new LikeId(likeId));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String missingProperties() {
        return "Property is Missing";
    }
}
