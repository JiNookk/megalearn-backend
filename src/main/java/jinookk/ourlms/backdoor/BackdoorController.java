package jinookk.ourlms.backdoor;

import jinookk.ourlms.models.vos.HashTag;
import jinookk.ourlms.models.vos.Title;
import jinookk.ourlms.models.vos.VideoUrl;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.SectionId;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@RestController
@Transactional
@RequestMapping("/backdoor")
public class BackdoorController {
    private final JdbcTemplate jdbcTemplate;

    public BackdoorController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/setup-course-db")
    public String setupCourses() {
        List<HashTag> hashTags = List.of(new HashTag("헛소리 잘하는 법"), new HashTag("화나게 하는 법"));

        jdbcTemplate.execute("DELETE from course_hash_tags");
        jdbcTemplate.execute("DELETE from course");

        jdbcTemplate.execute("INSERT INTO " +
                "course(id, lecture_id, course_title, image_path, category_name, " +
                "instructor_name, student_count, rating) " +
                "VALUES(1, 1, '강의 1', '경로', '개발, 프로그래밍 > 백엔드', '오진성', 1234, 4.5)");

        for (HashTag hashTag : hashTags) {
            jdbcTemplate.update("INSERT INTO " +
                            "course_hash_tags(course_id, tag_name) " +
                            "VALUES(1, ?)"
                    , hashTag.tagName());
        }

        return "Ok";
    }

    @GetMapping("/setup-lecture-db")
    public String setupLectures() {
        jdbcTemplate.execute("DELETE from lecture");

        jdbcTemplate.execute("INSERT INTO " +
                "lecture(id, course_id, lecture_title, video_url) " +
                "VALUES(1, 1, '테스트 1강', 'xEbAwSof5M4')");

        jdbcTemplate.execute("INSERT INTO " +
                "lecture(id, course_id, lecture_title, video_url) " +
                "VALUES(2, 1, '테스트 2강', 'KHiONHoiGys')");

        jdbcTemplate.execute("INSERT INTO " +
                "lecture(id, course_id, lecture_title, video_url) " +
                "VALUES(3, 1, '테스트 3강', 'quvgobYR8pA')");

        return "Ok";
    }

    @GetMapping("/reset-inquiries-db")
    public String resetInquiries() {
        jdbcTemplate.execute("DELETE from inquiry_hash_tags");
        jdbcTemplate.execute("DELETE from comment");
        jdbcTemplate.execute("DELETE from inquiry");

        return "Ok";
    }

    @GetMapping("/setup-section-db")
    public String setupSections() {
        jdbcTemplate.execute("DELETE from progress");
        jdbcTemplate.execute("DELETE from section");


        jdbcTemplate.execute("INSERT INTO " +
                "section(id, course_id, title) " +
                "VALUES(1, 1, '섹션 1')");

        jdbcTemplate.execute("INSERT INTO " +
                "section(id, course_id, title) " +
                "VALUES(2, 1, '섹션 2')");

        jdbcTemplate.execute("INSERT INTO " +
                "progress(id, course_id, section_id, account_id, lecture_id, lecture_title, status, minutes) " +
                "VALUES(1, 1, 1, 1, 1, '테스트 1강', 'unwatched', 67)");

        jdbcTemplate.execute("INSERT INTO " +
                "progress(id, course_id, section_id, account_id, lecture_id, lecture_title, status, minutes) " +
                "VALUES(2, 1, 1, 1, 2, '테스트 2강', 'unwatched', 121)");

        jdbcTemplate.execute("INSERT INTO " +
                "progress(id, course_id, section_id, account_id, lecture_id, lecture_title, status, minutes) " +
                "VALUES(3, 1, 2, 1, 3, '테스트 3강', 'unwatched', 86)");

        return "Ok";
    }
}
