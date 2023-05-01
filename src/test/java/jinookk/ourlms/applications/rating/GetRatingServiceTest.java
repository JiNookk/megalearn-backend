package jinookk.ourlms.applications.rating;

import jinookk.ourlms.dtos.RatingDto;
import jinookk.ourlms.dtos.RatingsDto;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.models.entities.Rating;
import jinookk.ourlms.models.vos.Content;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.CourseRepository;
import jinookk.ourlms.repositories.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class GetRatingServiceTest {
    GetRatingService getRatingService;
    CourseRepository courseRepository;
    RatingRepository ratingRepository;
    AccountRepository accountRepository;

    @BeforeEach
    void setup() {
        accountRepository = mock(AccountRepository.class);
        ratingRepository = mock(RatingRepository.class);
        courseRepository = mock(CourseRepository.class);
        getRatingService = new GetRatingService(courseRepository, ratingRepository, accountRepository);

        Account account = Account.fake("account");
        given(accountRepository.findByUserName(any())).willReturn(Optional.of(account));

        Course course = Course.fake("course");
        given(courseRepository.findAllByAccountId(any()))
                .willReturn(List.of(course));

        Rating rating1 = new Rating(
                1L, new AccountId(1L), new CourseId(1L), new Name("name1", false), new Content("content1"), 4.0);
        Rating rating2 = new Rating(
                2L, new AccountId(2L), new CourseId(1L), new Name("name2", false), new Content("content2"), 4.3);
        Rating rating3 = new Rating(
                3L, new AccountId(3L), new CourseId(1L), new Name("name3", false), new Content("content3"), 3.7);

        given(ratingRepository.findAll())
                .willReturn(List.of(rating1, rating2, rating3));

        given(ratingRepository.findAllByCourseId(new CourseId(1L)))
                .willReturn(List.of(rating1, rating2, rating3));

        given(ratingRepository.findAllByAccountId(new AccountId(1L)))
                .willReturn(List.of(rating1, rating2, rating3));

        given(ratingRepository.save(any())).willReturn(rating1);
    }

    @Test
    void totalRating() {
        RatingDto ratingDto = getRatingService.totalRating(new UserName("userName@email.com"));

        assertThat(ratingDto.getRating()).isEqualTo(4.0);
    }

    @Test
    void list() {
        RatingsDto ratingsDto = getRatingService.list();

        assertThat(ratingsDto.getRatings()).hasSize(3);
    }

    @Test
    void myReviews() {
        RatingsDto ratingsDto = getRatingService.myReviews(new UserName("userName@email.com"));

        assertThat(ratingsDto.getRatings()).hasSize(3);
    }

    @Test
    void listWithAccountId() {
        RatingsDto ratingsDto = getRatingService.listWithAccountId(new CourseId(1L), new UserName("userName@email.com"));

        assertThat(ratingsDto.getRatings()).hasSize(3);
    }
}
