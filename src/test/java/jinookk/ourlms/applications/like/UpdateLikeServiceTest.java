package jinookk.ourlms.applications.like;

import jinookk.ourlms.dtos.LikeDto;
import jinookk.ourlms.fixtures.Fixture;
import jinookk.ourlms.models.entities.Like;
import jinookk.ourlms.models.vos.ids.LikeId;
import jinookk.ourlms.repositories.LikeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class UpdateLikeServiceTest {
    UpdateLikeService likeService;
    LikeRepository likeRepository;

    @BeforeEach
    void setup() {
        likeRepository = mock(LikeRepository.class);
        likeService = new UpdateLikeService(likeRepository);

        Like like = Fixture.like(false);
        given(likeRepository.findById(any())).willReturn(Optional.of(like));
        given(likeRepository.findByAccountIdAndCourseId(any(), any())).willReturn(Optional.of(like));
        given(likeRepository.findAll()).willReturn(List.of(like));

    }

    @Test
    void toggle() {
        LikeDto likeDto = likeService.toggle(new LikeId(1L));

        assertThat(likeDto.getClicked()).isTrue();
    }
}
