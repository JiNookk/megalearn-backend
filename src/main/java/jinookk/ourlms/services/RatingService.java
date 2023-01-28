package jinookk.ourlms.services;

import jinookk.ourlms.dtos.RatingDto;
import jinookk.ourlms.dtos.RatingRequestDto;
import jinookk.ourlms.dtos.RatingsDto;
import jinookk.ourlms.exceptions.AccountNotFound;
import jinookk.ourlms.exceptions.RatingNotExisting;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.models.entities.Rating;
import jinookk.ourlms.models.vos.Name;
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
public class RatingService {
    private final CourseRepository courseRepository;
    private final RatingRepository ratingRepository;
    private final AccountRepository accountRepository;

    public RatingService(CourseRepository courseRepository, RatingRepository ratingRepository,
                         AccountRepository accountRepository) {
        this.courseRepository = courseRepository;
        this.ratingRepository = ratingRepository;
        this.accountRepository = accountRepository;
    }

    public RatingDto totalRating(Name userName) {
        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFound(userName));

        AccountId accountId = new AccountId(account.id());

        List<Course> courses = courseRepository.findAllByAccountId(accountId);

        Double totalRating = courses.stream()
                .map(course -> course.averageRating(
                            ratingRepository.findAllByCourseId(new CourseId(course.id()))))
                .reduce(Double::sum)
                .orElseThrow(() -> new RatingNotExisting(accountId));

        return new RatingDto(totalRating);
    }

    public RatingsDto list() {
        List<Rating> ratings = ratingRepository.findAll();

        List<RatingDto> ratingDtos = ratings.stream()
                .map(Rating::toDto)
                .toList();

        return new RatingsDto(ratingDtos);
    }

    public RatingsDto listWithAccountId(CourseId courseId, Name userName) {
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

    public RatingDto rate(Name userName, RatingRequestDto ratingRequestDto) {
        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFound(userName));

        Rating rating = Rating.of(account, ratingRequestDto);

        Rating saved = ratingRepository.save(rating);

        return saved.toDto();
    }

    public RatingsDto myReviews(Name userName) {
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
