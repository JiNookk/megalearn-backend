package jinookk.ourlms.backdoor;

import jinookk.ourlms.models.vos.HashTag;
import jinookk.ourlms.models.vos.LectureTime;
import jinookk.ourlms.models.vos.Like;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.models.vos.Post;
import jinookk.ourlms.models.vos.Title;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.LectureId;
import jinookk.ourlms.models.vos.ids.SectionId;
import jinookk.ourlms.models.vos.status.Status;
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

        List<Post> news = List.of(
                new Post("News1", LocalDateTime.of(2022, 12, 26, 1, 1), "content1"),
                new Post("News2", LocalDateTime.of(2022, 12, 31, 1, 1), "content2")
        );

        List<List<String>> goalList = List.of(
                List.of(
                        "자바의 기초 문법 완벽 마스터",
                        "객체 지향 프로그래밍에 대한 이해",
                        "장마다 제공되는 실력 다지기 퀴즈 및 해설",
                        "IntelliJ 의 주요 단축키 및 강력한 기능 사용법"
                ),
                List.of(
                        "하루 동안 섭취한 음식에 맞는 우리몸에 꼭 맞는 운동법을 영상을 통해 따라해 볼 수 있다.",
                        "집에서 간단하게 맨몸 스트레칭 운동을 통해 건강한 하루로 마무리하자"
                ),
                List.of(
                        "디자이너와 협업 시 빈번하게 발생하는 소통 문제 예방/해결할 수 있는 방법",
                        "피그마에서 컴포넌트 세로정렬 값 확인하는 방법 ex. v-align: top / middle",
                        "피그마에서 컴포넌트 다운로드 안될 때 5초 해결 방법",
                        "피그마에서 인터랙션(애니메이션) 속성 확인하기 ex. 화면이 우에서 좌로 slide in"
                ));

        jdbcTemplate.execute("DELETE from course_hash_tags");
        jdbcTemplate.execute("DELETE from course_news");
        jdbcTemplate.execute("DELETE from course_goals");
        jdbcTemplate.execute("DELETE from lecture");
        jdbcTemplate.execute("DELETE from course");

        jdbcTemplate.execute("INSERT INTO " +
                "course(" +
                "id, instructor_id, course_title, image_path, category_name, instructor_name, description, price, " +
                "status, level" +
                ") " +
                "VALUES(1, 1, '강의 1', '경로', '개발 • 프로그래밍', '오진성', 'description', 49000, 'approved', 'BEGINNER')");

        jdbcTemplate.execute("INSERT INTO " +
                "course(" +
                "id, instructor_id, course_title, image_path, category_name, instructor_name, description, price, " +
                "status, level" +
                ") " +
                "VALUES(2, 1, '강의 2', '경로', '교양', '오진성', 'description', 35000, 'approved', 'INTERMEDIATE')");

        jdbcTemplate.execute("INSERT INTO " +
                "course(" +
                "id, instructor_id, course_title, image_path, category_name, instructor_name, description, price, " +
                "status, level" +
                ") " +
                "VALUES(3, 1, '강의 3', '경로', '크리에이티브', '오진성', 'description', 24000, 'approved', 'EXPERT')");

        for (HashTag hashTag : hashTags) {
            jdbcTemplate.update("INSERT INTO " +
                            "course_hash_tags(course_id, tag_name) " +
                            "VALUES(1, ?)"
                    , hashTag.tagName());
        }

        for (Post post : news) {
            jdbcTemplate.update("INSERT INTO " +
                            "course_news(course_id, title, created_at, content) " +
                            "VALUES(1, ?, ?, ?)"
                    , post.title(), post.createdAt(), post.content());
        }

        for (int i = 0; i < goalList.size(); i += 1) {
            List<String> goals = goalList.get(i);

            for (String goal : goals) {
                jdbcTemplate.update("INSERT INTO " +
                                "course_goals(course_id, goal) " +
                                "VALUES(?, ?)"
                        , i + 1, goal);
            }
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
                "section(id, course_id, goal, status, title) " +
                "VALUES(1, 1, 'goal', 'created', '섹션 1')");

        jdbcTemplate.execute("INSERT INTO " +
                "section(id, course_id, goal, status, title) " +
                "VALUES(2, 1, 'goal', 'created', '섹션 2')");

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

    @GetMapping("/setup-progress-db")
    public String setupProgress() {
        jdbcTemplate.execute("DELETE from progress");

        jdbcTemplate.execute("INSERT INTO " +
                "progress(id, course_id, section_id, account_id, lecture_id, lecture_title, status, minutes, seconds) " +
                "VALUES(1, 1, 1, 1, 1, '테스트 1강', 'completed', 1, 24)");

        jdbcTemplate.execute("INSERT INTO " +
                "progress(id, course_id, section_id, account_id, lecture_id, lecture_title, status, minutes, seconds) " +
                "VALUES(2, 1, 1, 1, 2, '테스트 2강', 'completed', 3, 24)");

        jdbcTemplate.execute("INSERT INTO " +
                "progress(id, course_id, section_id, account_id, lecture_id, lecture_title, status, minutes, seconds) " +
                "VALUES(3, 1, 2, 1, 3, '테스트 1강', 'processing', 5, 24)");

        jdbcTemplate.execute("INSERT INTO " +
                "progress(id, course_id, section_id, account_id, lecture_id, lecture_title, status, minutes, seconds) " +
                "VALUES(4, 1, 1, 2, 1, '테스트 1강', 'completed', 63, 26)");

        jdbcTemplate.execute("INSERT INTO " +
                "progress(id, course_id, section_id, account_id, lecture_id, lecture_title, status, minutes, seconds) " +
                "VALUES(5, 1, 1, 2, 2, '테스트 2강', 'processing', 3, 24)");

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
