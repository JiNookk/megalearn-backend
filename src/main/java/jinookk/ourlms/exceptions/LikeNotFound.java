package jinookk.ourlms.exceptions;

import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.models.vos.ids.LikeId;

public class LikeNotFound extends RuntimeException {
    public LikeNotFound(LikeId likeId) {
        super("Like is not found by: " + likeId);
    }

    public LikeNotFound(AccountId accountId, CourseId courseId) {
        super("My Like is not found by accountId: " + accountId + " and courseId: " + courseId);
    }
}
