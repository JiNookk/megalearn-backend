package jinookk.ourlms.repositories;

import jinookk.ourlms.models.entities.Inquiry;
import jinookk.ourlms.models.vos.ids.LectureId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
    List<Inquiry> findAllByLectureId(LectureId lectureId);

    List<Inquiry> findAllByLectureTime_MinuteAndLectureId(Long lectureTime, LectureId lectureId);

    List<Inquiry> findAllByTitle_ValueContainsOrContent_ValueContainsOrPublisher_ValueContainsAndLectureId(
            String title, String content, String publisher, LectureId lectureId);

    List<Inquiry> findAllByTitle_ValueContainsOrContent_ValueContainsOrPublisher_ValueContainsAndLectureTime_MinuteAndLectureId
            (String title, String content, String publisher, Long lectureTime, LectureId lectureId);
}
