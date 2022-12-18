package jinookk.ourlms.repositories;

import jinookk.ourlms.models.entities.Note;
import jinookk.ourlms.models.vos.AccountId;
import jinookk.ourlms.models.vos.LectureId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findAllByLectureIdAndAccountId(LectureId lectureId, AccountId accountId);
}
