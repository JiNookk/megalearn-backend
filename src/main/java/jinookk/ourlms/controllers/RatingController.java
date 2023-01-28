package jinookk.ourlms.controllers;

import jinookk.ourlms.dtos.RatingDto;
import jinookk.ourlms.dtos.RatingRequestDto;
import jinookk.ourlms.dtos.RatingsDto;
import jinookk.ourlms.models.entities.Rating;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.services.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RatingController {
    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/ratings")
    @ResponseStatus(HttpStatus.CREATED)
    public RatingDto rate(
            @Validated @RequestBody RatingRequestDto ratingRequestDto,
            @RequestAttribute Name userName
    ) {
        return ratingService.rate(userName, ratingRequestDto);
    }

    @GetMapping("/instructor/my-rating")
    public RatingDto totalRating(
            @RequestAttribute Name userName
    ) {
        return ratingService.totalRating(userName);
    }

    @GetMapping("/ratings")
    public RatingsDto list() {
        return ratingService.list();
    }

    @GetMapping("/ratings/me")
    public RatingsDto myReviews(
            @RequestAttribute Name userName
    ) {
        return ratingService.myReviews(userName);
    }

    @GetMapping("/instructor/ratings")
    public RatingsDto listByInstructorId(
            @RequestAttribute Name userName,
            @RequestParam(required = false) Long courseId
    ) {
        return ratingService.listWithAccountId(new CourseId(courseId), userName);
    }
}
