package jinookk.ourlms.services;

import jinookk.ourlms.dtos.RatingDto;
import jinookk.ourlms.dtos.RatingsDto;
import jinookk.ourlms.exceptions.RatingNotExisting;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.models.entities.Rating;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
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

    public RatingService(CourseRepository courseRepository, RatingRepository ratingRepository) {
        this.courseRepository = courseRepository;
        this.ratingRepository = ratingRepository;
    }

    public RatingDto totalRating(AccountId accountId) {
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

    public RatingsDto listWithAccountId(CourseId courseId, AccountId accountId) {
        List<Course> courses = courseRepository.findAllByAccountId(accountId);

        List<RatingDto> ratingDtos = courses.stream()
                .filter(course -> course.filterId(courseId))
                .map(course -> ratingRepository.findAllByCourseId(new CourseId(course.id())))
                .flatMap(Collection::stream)
                .map(Rating::toDto)
                .toList();

        return new RatingsDto(ratingDtos);
    }
}
