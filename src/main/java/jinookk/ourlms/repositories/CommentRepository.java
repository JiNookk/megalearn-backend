package jinookk.ourlms.repositories;

import jinookk.ourlms.models.entities.Comment;
import jinookk.ourlms.models.vos.ids.InquiryId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByInquiryId(InquiryId inquiryId);
}
