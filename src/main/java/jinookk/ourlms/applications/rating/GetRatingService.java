package jinookk.ourlms.applications.rating;

import jinookk.ourlms.dtos.RatingDto;
import jinookk.ourlms.dtos.RatingsDto;
import jinookk.ourlms.exceptions.AccountNotFound;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.models.entities.Rating;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.CourseRepository;
import jinookk.ourlms.repositories.RatingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Transactional
@Service
public class GetRatingService {
    private final CourseRepository courseRepository;
    private final RatingRepository ratingRepository;
    private final AccountRepository accountRepository;

    public GetRatingService(CourseRepository courseRepository, RatingRepository ratingRepository,
                            AccountRepository accountRepository) {
        this.courseRepository = courseRepository;
        this.ratingRepository = ratingRepository;
        this.accountRepository = accountRepository;
    }

    public RatingDto totalRating(UserName userName) {
        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFound(userName));

        AccountId accountId = new AccountId(account.id());

        List<CourseId> courseIds = courseRepository.findAllByAccountId(accountId).stream()
                .map(course -> new CourseId(course.id()))
                .toList();

        List<Rating> ratings = ratingRepository.findAll();

        Double totalRating = Rating.averageOf(courseIds, ratings);

        return new RatingDto(totalRating);
    }

    public RatingsDto list() {
        List<Rating> ratings = ratingRepository.findAll();

        List<RatingDto> ratingDtos = ratings.stream()
                .map(Rating::toDto)
                .toList();

        return new RatingsDto(ratingDtos);
    }

    public RatingsDto listWithAccountId(CourseId courseId, UserName userName) {
        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFound(userName));

        AccountId accountId = new AccountId(account.id());

        List<Course> courses = courseRepository.findAllByAccountId(accountId);

        List<RatingDto> ratingDtos = courses.stream()
                .filter(course -> course.filterId(courseId))
                .map(course -> ratingRepository.findAllByCourseId(new CourseId(course.id())))
                .flatMap(Collection::stream)
                .map(Rating::toDto)
                .toList();

        return new RatingsDto(ratingDtos);
    }

    public RatingsDto myReviews(UserName userName) {
        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFound(userName));

        AccountId accountId = new AccountId(account.id());

        List<Rating> ratings = ratingRepository.findAllByAccountId(accountId);

        List<RatingDto> ratingDtos = ratings.stream()
                .map(Rating::toDto)
                .toList();

        return new RatingsDto(ratingDtos);
    }
}
