package jinookk.ourlms.models.dtos;

import jinookk.ourlms.models.enums.Level;
import jinookk.ourlms.models.vos.Category;
import jinookk.ourlms.models.vos.Content;
import jinookk.ourlms.models.vos.HashTag;
import jinookk.ourlms.models.vos.ImagePath;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.models.vos.Post;
import jinookk.ourlms.models.vos.Price;
import jinookk.ourlms.models.vos.Title;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.status.Status;

import java.time.LocalDateTime;
import java.util.List;

public record GetCourseDto(Long id, Category category, Title title, Price price, Content description, Status status,
                           Name instructor, AccountId accountId, ImagePath imagePath, List<Post> news,
                           List<HashTag> hashTags, List<HashTag> skillSets, boolean isInstructor, Level level,
                           List<Content> goals, LocalDateTime createdAt) {
}
