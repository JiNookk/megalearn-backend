package jinookk.ourlms.repositories;

import jinookk.ourlms.models.entities.Note;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.LectureId;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long>, JpaSpecificationExecutor<Note> {
    List<Note> findAllByLectureIdAndAccountId(LectureId lectureId, AccountId accountId);

    List<Note> findAllByAccountId(AccountId accountId);

    List<Note> findAll(Specification<Note> spec);
}
