package jinookk.ourlms.services;

import jinookk.ourlms.dtos.LikeDto;
import jinookk.ourlms.dtos.LikesDto;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Like;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.models.vos.ids.AccountId;
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

class LikeServiceTest {
    LikeService likeService;
    LikeRepository likeRepository;
    AccountRepository accountRepository;

    @BeforeEach
    void setup() {
        accountRepository = mock(AccountRepository.class);
        likeRepository = mock(LikeRepository.class);
        likeService = new LikeService(likeRepository, accountRepository);

        Like like = Like.fake(false);
        given(likeRepository.findById(any())).willReturn(Optional.of(like));
        given(likeRepository.findByAccountIdAndCourseId(any(), any())).willReturn(Optional.of(like));
        given(likeRepository.findAll()).willReturn(List.of(like));

        Account account = Account.fake("account");
        given(accountRepository.findByUserName(any())).willReturn(Optional.of(account));
    }

    @Test
    void list() {
        LikesDto likesDto = likeService.list();

        assertThat(likesDto.getLikes()).hasSize(1);
    }

    @Test
    void detail() {
        LikeDto likeDto = likeService.detail(new Name("userName"), new CourseId(1L));

        assertThat(likeDto.getClicked()).isFalse();
    }

    @Test
    void toggle() {
        LikeDto likeDto = likeService.toggle(new LikeId(1L));

        assertThat(likeDto.getClicked()).isTrue();
    }
}
