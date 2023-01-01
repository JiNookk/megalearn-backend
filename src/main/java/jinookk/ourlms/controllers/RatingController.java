package jinookk.ourlms.controllers;

import jinookk.ourlms.dtos.RatingDto;
import jinookk.ourlms.dtos.RatingsDto;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.services.RatingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RatingController {
    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping("/instructor/my-rating")
    public RatingDto totalRating(
            @RequestAttribute Long accountId
    ) {
        return ratingService.totalRating(new AccountId(accountId));
    }

    @GetMapping("/ratings")
    public RatingsDto list() {
        return ratingService.list();
    }

    @GetMapping("/instructor/ratings")
    public RatingsDto listByInstructorId(
            @RequestAttribute Long accountId,
            @RequestParam(required = false) Long courseId
    ) {
        return ratingService.listWithAccountId(new CourseId(courseId), new AccountId(accountId));
    }
}
