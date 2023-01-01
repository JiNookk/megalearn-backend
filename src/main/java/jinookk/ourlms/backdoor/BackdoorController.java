package jinookk.ourlms.backdoor;

import jinookk.ourlms.models.vos.HashTag;
import jinookk.ourlms.models.vos.Like;
import jinookk.ourlms.models.vos.Name;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
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
        List<HashTag> hashTags = List.of(new HashTag("운동"), new HashTag("프로틴"));

        jdbcTemplate.execute("DELETE from course_hash_tags");
        jdbcTemplate.execute("DELETE from lecture");
        jdbcTemplate.execute("DELETE from course");

        jdbcTemplate.execute("INSERT INTO " +
                "course(" +
                "id, instructor_id, course_title, image_path, category_name, instructor_name, description, price, status" +
                ") " +
                "VALUES(1, 1, '강의 1', '경로', '개발, 프로그래밍 > 백엔드', '오진성', 'description', 49000, 'approved')");

        jdbcTemplate.execute("INSERT INTO " +
                "course(" +
                "id, instructor_id, course_title, image_path, category_name, instructor_name, description, price, status" +
                ") " +
                "VALUES(2, 1, '강의 2', '경로', '개발, 프로그래밍 > 백엔드', '오진성', 'description', 35000, 'approved')");

        jdbcTemplate.execute("INSERT INTO " +
                "course(" +
                "id, instructor_id, course_title, image_path, category_name, instructor_name, description, price, status" +
                ") " +
                "VALUES(3, 1, '강의 3', '경로', '개발, 프로그래밍 > 백엔드', '오진성', 'description', 24000, 'approved')");

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
                "lecture(id, course_id, section_id, lecture_title, video_url, handout_url, lecture_note, status) " +
                "VALUES(1, 1, 1, '테스트 1강', 'xEbAwSof5M4', 'handout', 'note', 'created')");

        jdbcTemplate.execute("INSERT INTO " +
                "lecture(id, course_id, section_id, lecture_title, video_url, handout_url, lecture_note, status) " +
                "VALUES(2, 1, 1, '테스트 2강', 'KHiONHoiGys', 'handout', 'note', 'created')");

        jdbcTemplate.execute("INSERT INTO " +
                "lecture(id, course_id, section_id, lecture_title, video_url, handout_url, lecture_note, status) " +
                "VALUES(3, 2, 1, '테스트 3강', 'quvgobYR8pA', 'handout', 'note', 'created')");

        return "Ok";
    }

    @GetMapping("/setup-inquiry-db")
    public String resetInquiry() {
        List<HashTag> hashTags = List.of(new HashTag("운동"), new HashTag("프로틴"));

        List<Like> likes1 = List.of(new Like(1L), new Like(2L));
        List<Like> likes2 = List.of(new Like(1L), new Like(2L), new Like(3L));
        List<Like> likes3 = List.of(new Like(1L), new Like(2L), new Like(3L), new Like(4L));

        jdbcTemplate.execute("DELETE from inquiry_hash_tags");
        jdbcTemplate.execute("DELETE from inquiry_likes");
        jdbcTemplate.execute("DELETE from comment");
        jdbcTemplate.execute("DELETE from inquiry");

        jdbcTemplate.update("INSERT INTO " +
                "inquiry(" +
                "id, lecture_id, account_id, course_id, title, content, minutes, seconds, publisher, anonymous, publish_time, " +
                "status, replied, solved, replied_at" +
                ") " +
                "VALUES(" +
                "1, 2, 2, 1, '질문 1', 'content', 3, 24, 'publisher', true, ?, 'created', 'completed', 'processing', ?" +
                ")", LocalDateTime.of(2022, 12, 3, 1, 1), LocalDateTime.of(2022, 12, 26, 1, 1));

        jdbcTemplate.update("INSERT INTO " +
                "inquiry(" +
                "id, lecture_id, account_id, course_id, title, content, minutes, seconds, publisher, anonymous, publish_time, " +
                "status, replied, solved, replied_at" +
                ") " +
                "VALUES(" +
                "2, 1, 2, 1, '질문 2', 'content2', 3, 24, 'publisher', true, ?, 'created', 'processing', 'processing', ?" +
                ")", LocalDateTime.of(2022, 12, 5, 1, 1), LocalDateTime.of(2022, 12, 27, 1, 1));

        jdbcTemplate.update("INSERT INTO " +
                "inquiry(" +
                "id, lecture_id, account_id, course_id, title, content, minutes, seconds, publisher, anonymous, publish_time, " +
                "status, replied, solved, replied_at" +
                ") " +
                "VALUES(" +
                "3, 3, 3, 2, '질문 3', 'content3', 3, 24, 'publisher', true, ?, 'created', 'processing', 'completed', ?" +
                ")", LocalDateTime.of(2022, 12, 21, 1, 1), LocalDateTime.of(2022, 12, 25, 1, 1));

        for (HashTag hashTag : hashTags) {
            jdbcTemplate.update("INSERT INTO " +
                            "inquiry_hash_tags(inquiry_id, tag_name) " +
                            "VALUES(1, ?)"
                    , hashTag.tagName());
        }

        setupLikes(likes1, 1);
        setupLikes(likes2, 2);
        setupLikes(likes3, 3);

        return "Ok";
    }

    private void setupLikes(List<Like> likes1, Integer i) {
        for (Like like : likes1) {
            jdbcTemplate.update("INSERT INTO " +
                            "inquiry_likes(inquiry_id, account_id) " +
                            "VALUES(?, ?)"
                    , i, like.accountId());
        }
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

    @GetMapping("/setup-rating-db")
    public String setupRating() {
        jdbcTemplate.execute("DELETE from rating");

        jdbcTemplate.update("INSERT INTO " +
                "rating(id, account_id, course_id, author, content, created_at, point) " +
                "VALUES(1, 1, 1, 'tester1', '최고의 강의입니다', ?, 4)"
        , LocalDateTime.now());

        jdbcTemplate.update("INSERT INTO " +
                "rating(id, account_id, course_id, author, content, created_at, point) " +
                "VALUES(2, 2, 1, 'tester1', '👍🏻', ?, 5)"
        , LocalDateTime.now());

        jdbcTemplate.update("INSERT INTO " +
                "rating(id, account_id, course_id, author, content, created_at, point) " +
                "VALUES(3, 3, 1, 'tester1', '이것만 들으면 JPA정복 가능', ?, 5)"
        , LocalDateTime.now());

        return "Ok";
    }

    @GetMapping("/setup-payment-db")
    public String setupPayments() {
        jdbcTemplate.execute("DELETE from payment");

        jdbcTemplate.update("INSERT INTO " +
                "payment(id, course_id, account_id, purchaser, price, course_title, created_at) " +
                "VALUES(1, 1, 1, 'tester1', 35000, '테스트 1강', ?)", LocalDateTime.now());

        jdbcTemplate.update("INSERT INTO " +
                "payment(id, course_id, account_id, purchaser, price, course_title, created_at) " +
                "VALUES(2, 1, 2, 'tester2', 35000, '테스트 1강', ?)", LocalDateTime.now());

        jdbcTemplate.update("INSERT INTO " +
                "payment(id, course_id, account_id, purchaser, price, course_title, created_at) " +
                "VALUES(3, 1, 3, 'tester3', 35000, '테스트 1강', ?)", LocalDateTime.now());

        jdbcTemplate.update("INSERT INTO " +
                "payment(id, course_id, account_id, purchaser, price, course_title, created_at) " +
                "VALUES(4, 2, 3, 'tester3', 24000, '테스트 2강', ?)", LocalDateTime.now());

        jdbcTemplate.update("INSERT INTO " +
                "payment(id, course_id, account_id, purchaser, price, course_title, created_at) " +
                "VALUES(5, 3, 3, 'tester3', 49000, '테스트 3강', ?)", LocalDateTime.now());

        return "Ok";
    }

    @GetMapping("/setup-account-db")
    public String setupAccounts() {
        jdbcTemplate.execute("DELETE from account");

        jdbcTemplate.execute("INSERT INTO " +
                "account(id, name) " +
                "VALUES(1, 'tester1')");

        jdbcTemplate.execute("INSERT INTO " +
                "account(id, name) " +
                "VALUES(2, 'tester2')");

        jdbcTemplate.execute("INSERT INTO " +
                "account(id, name) " +
                "VALUES(3, 'tester3')");

        return "Ok";
    }
}
