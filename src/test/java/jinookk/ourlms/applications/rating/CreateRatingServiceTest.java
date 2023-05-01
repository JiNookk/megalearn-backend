package jinookk.ourlms.applications.rating;

import jinookk.ourlms.dtos.RatingDto;
import jinookk.ourlms.dtos.RatingRequestDto;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Rating;
import jinookk.ourlms.models.vos.Content;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class CreateRatingServiceTest {
    CreateRatingService createRatingService;
    RatingRepository ratingRepository;
    AccountRepository accountRepository;

    @BeforeEach
    void setup() {
        accountRepository = mock(AccountRepository.class);
        ratingRepository = mock(RatingRepository.class);
        createRatingService = new CreateRatingService(ratingRepository, accountRepository);

        Account account = Account.fake("account");
        given(accountRepository.findByUserName(any())).willReturn(Optional.of(account));

        Rating rating = new Rating(
                1L, new AccountId(1L), new CourseId(1L), new Name("name1", false), new Content("content1"), 4.0);

        given(ratingRepository.save(any())).willReturn(rating);
    }

    @Test
    void rate() {
        RatingDto ratingDto = createRatingService.rate(new UserName("userName@email.com"), new RatingRequestDto(1L, 4, "하이입니다"));

        assertThat(ratingDto.getRating()).isEqualTo(4.0);
    }
}
