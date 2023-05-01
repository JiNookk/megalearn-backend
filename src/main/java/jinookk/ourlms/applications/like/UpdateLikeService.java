package jinookk.ourlms.applications.like;

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
public class UpdateLikeService {
    private final LikeRepository likeRepository;

    public UpdateLikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    public LikeDto toggle(LikeId likeId) {
        Like like = likeRepository.findById(likeId.value())
                .orElseThrow(() -> new LikeNotFound(likeId));

        Like toggled = like.toggle();

        return toggled.toDto();
    }
}
