package jinookk.ourlms.applications.like;

import jinookk.ourlms.dtos.LikeDto;
import jinookk.ourlms.dtos.LikesDto;
import jinookk.ourlms.fixtures.Fixture;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Like;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.models.vos.ids.LikeId;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.LikeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class GetLikeServiceTest extends Fixture {
    GetLikeService getLikeService;
    LikeRepository likeRepository;
    AccountRepository accountRepository;

    @BeforeEach
    void setup() {
        accountRepository = mock(AccountRepository.class);
        likeRepository = mock(LikeRepository.class);
        getLikeService = new GetLikeService(likeRepository, accountRepository);

        Like like = Fixture.like(false);
        given(likeRepository.findById(any())).willReturn(Optional.of(like));
        given(likeRepository.findByAccountIdAndCourseId(any(), any())).willReturn(Optional.of(like));
        given(likeRepository.findAll()).willReturn(List.of(like));

        Account account = Fixture.account("account");
        given(accountRepository.findByUserName(any())).willReturn(Optional.of(account));
    }

    @Test
    void list() {
        LikesDto likesDto = getLikeService.list();

        assertThat(likesDto.getLikes()).hasSize(1);
    }

    @Test
    void detail() {
        LikeDto likeDto = getLikeService.detail(new UserName("userName@email.com"), new CourseId(1L));

        assertThat(likeDto.getClicked()).isFalse();
    }
}
