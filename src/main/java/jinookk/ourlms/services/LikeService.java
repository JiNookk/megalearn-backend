package jinookk.ourlms.services;

import jinookk.ourlms.dtos.LikeDto;
import jinookk.ourlms.dtos.LikesDto;
import jinookk.ourlms.exceptions.AccountNotFound;
import jinookk.ourlms.exceptions.LikeNotFound;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Like;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.models.vos.ids.LikeId;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.LikeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LikeService {
    private final LikeRepository likeRepository;
    private final AccountRepository accountRepository;

    public LikeService(LikeRepository likeRepository, AccountRepository accountRepository) {
        this.likeRepository = likeRepository;
        this.accountRepository = accountRepository;
    }

    public LikesDto list() {
        List<Like> likes = likeRepository.findAll();

        List<LikeDto> likeDtos = likes.stream()
                .map(Like::toDto)
                .toList();

        return new LikesDto(likeDtos);
    }

    public LikeDto detail(UserName userName, CourseId courseId) {
        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFound(userName));

        AccountId accountId = new AccountId(account.id());

        Like like = likeRepository.findByAccountIdAndCourseId(accountId, courseId)
                .orElseThrow(() -> new LikeNotFound(accountId, courseId));

        return like.toDto();
    }

    public LikeDto toggle(LikeId likeId) {
        Like like = likeRepository.findById(likeId.value())
                .orElseThrow(() -> new LikeNotFound(likeId));

        Like toggled = like.toggle();

        return toggled.toDto();
    }
}
