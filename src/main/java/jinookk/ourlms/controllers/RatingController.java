package jinookk.ourlms.controllers;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import jinookk.ourlms.applications.rating.CreateRatingService;
import jinookk.ourlms.applications.rating.GetRatingService;
import jinookk.ourlms.dtos.RatingDto;
import jinookk.ourlms.dtos.RatingRequestDto;
import jinookk.ourlms.dtos.RatingsDto;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.CourseId;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RatingController {
    private final GetRatingService getRatingService;
    private final CreateRatingService createRatingService;

    public RatingController(GetRatingService getRatingService,
                            CreateRatingService createRatingService) {
        this.getRatingService = getRatingService;
        this.createRatingService = createRatingService;
    }

    @PostMapping("/ratings")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "create Rating", notes = "create new Rating Entity")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {access_token}", required = true, dataType = "string", paramType = "header")
    })
    public RatingDto rate(
            @Validated @RequestBody RatingRequestDto ratingRequestDto,
            @RequestAttribute UserName userName
    ) {
        return createRatingService.rate(userName, ratingRequestDto);
    }

    @GetMapping("/instructor/my-rating")
    @ApiOperation(value = "My Total Rating", notes = "fetches instructor's total rating about courses")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {access_token}", required = true, dataType = "string", paramType = "header")
    })
    public RatingDto totalRating(
            @RequestAttribute UserName userName
    ) {
        return getRatingService.totalRating(userName);
    }

    @GetMapping("/ratings")
    public RatingsDto list() {
        return getRatingService.list();
    }

    @GetMapping("/ratings/me")
    @ApiOperation(value = "My Reviews", notes = "fetches Rating written by me")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {access_token}", required = true, dataType = "string", paramType = "header")
    })
    public RatingsDto myReviews(
            @RequestAttribute UserName userName
    ) {
        return getRatingService.myReviews(userName);
    }

    @GetMapping("/instructor/ratings")
    @ApiOperation(value = "Rating List", notes = "fetches Ratings with Instructor")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {access_token}", required = true, dataType = "string", paramType = "header")
    })
    public RatingsDto listByInstructorId(
            @RequestAttribute UserName userName,
            @RequestParam(required = false) Long courseId
    ) {
        return getRatingService.listWithAccountId(new CourseId(courseId), userName);
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userNameRequired(ServletRequestBindingException exception) {
        return exception.getMessage();
    }
}
