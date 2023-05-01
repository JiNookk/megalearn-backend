package jinookk.ourlms.applications.rating;

import jinookk.ourlms.dtos.RatingDto;
import jinookk.ourlms.dtos.RatingRequestDto;
import jinookk.ourlms.exceptions.AccountNotFound;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Rating;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.RatingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CreateRatingService {
    private final RatingRepository ratingRepository;
    private final AccountRepository accountRepository;

    public CreateRatingService(RatingRepository ratingRepository,
                               AccountRepository accountRepository) {
        this.ratingRepository = ratingRepository;
        this.accountRepository = accountRepository;
    }

    public RatingDto rate(UserName userName, RatingRequestDto ratingRequestDto) {
        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFound(userName));

        Rating rating = Rating.of(account, ratingRequestDto);

        Rating saved = ratingRepository.save(rating);

        return saved.toDto();
    }
}
