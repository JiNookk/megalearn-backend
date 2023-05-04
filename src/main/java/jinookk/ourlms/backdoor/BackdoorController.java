package jinookk.ourlms.backdoor;

import jinookk.ourlms.models.vos.HashTag;
import jinookk.ourlms.models.entities.Like;
import jinookk.ourlms.models.vos.Post;
import jinookk.ourlms.models.vos.ids.CourseId;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String setupCourses(
            @RequestParam(required = false) Integer count
    ) {
        jdbcTemplate.execute("DELETE from course_hash_tags");
        jdbcTemplate.execute("DELETE from course_news");
        jdbcTemplate.execute("DELETE from course_skill_sets");
        jdbcTemplate.execute("DELETE from course_goals");
        jdbcTemplate.execute("DELETE from course");

        for (int i = 0; i < 30; i += 3) {
            jdbcTemplate.update("INSERT INTO " +
                            "course(" +
                            "id, instructor_id, course_title, image_path, category_name, instructor_name, description, price, " +
                            "status, level, created_at" +
                            ") " +
                            "VALUES(?, 1, '테스트', '', '크리에이티브', '메가런', 'description', 49000, 'approved', 'BEGINNER', ?)"
                    , i + 3000, LocalDateTime.now().minusDays(10));

            jdbcTemplate.update("INSERT INTO " +
                            "course(" +
                            "id, instructor_id, course_title, image_path, category_name, instructor_name, description, price, " +
                            "status, level, created_at" +
                            ") " +
                            "VALUES(?, 1, '테스트', '', '개발 프로그래밍', '메가런', 'description', 49000, 'approved', 'BEGINNER', ?)"
                    , i + 3001, LocalDateTime.now().minusDays(10));


            jdbcTemplate.update("INSERT INTO " +
                            "course(" +
                            "id, instructor_id, course_title, image_path, category_name, instructor_name, description, price, " +
                            "status, level, created_at" +
                            ") " +
                            "VALUES(?, 1, '테스트', '', '커리어', '메가런', 'description', 49000, 'approved', 'BEGINNER', ?)"
                    , i + 3002, LocalDateTime.now().minusDays(10));

            setCollections(i);
            setCollections(i + 1);
            setCollections(i + 2);
        }

        for (int i = 0; i < 30; i += 3) {
            jdbcTemplate.update("INSERT INTO " +
                            "course(" +
                            "id, instructor_id, course_title, image_path, category_name, instructor_name, description, price, " +
                            "status, level, created_at" +
                            ") " +
                            "VALUES(?, 1, '테스트', '', '크리에이티브', '메가런', 'description', 49000, 'submitted', 'BEGINNER', ?)"
                    , i + 6000, LocalDateTime.now().minusDays(10));

            jdbcTemplate.update("INSERT INTO " +
                            "course(" +
                            "id, instructor_id, course_title, image_path, category_name, instructor_name, description, price, " +
                            "status, level, created_at" +
                            ") " +
                            "VALUES(?, 1, '테스트', '', '개발 프로그래밍', '메가런', 'description', 49000, 'processing', 'BEGINNER', ?)"
                    , i + 6001, LocalDateTime.now().minusDays(10));

            jdbcTemplate.update("INSERT INTO " +
                            "course(" +
                            "id, instructor_id, course_title, image_path, category_name, instructor_name, description, price, " +
                            "status, level, created_at" +
                            ") " +
                            "VALUES(?, 1, '테스트', '', '커리어', '메가런', 'description', 49000, 'submitted', 'BEGINNER', ?)"
                    , i + 6002, LocalDateTime.now().minusDays(10));

            setCollections(i);
            setCollections(i + 1);
            setCollections(i + 2);
        }

        return "Ok";
    }

    private void setCollections(int i) {
        for (int j = 0; j < 5; j += 1) {
            jdbcTemplate.update("INSERT INTO " +
                            "course_goals(" +
                            "course_id, goal" +
                            ") " +
                            "VALUES(?, '목표')"
                    , i + 3000);

            jdbcTemplate.update("INSERT INTO " +
                            "course_hash_tags(" +
                            "course_id, tag_name" +
                            ") " +
                            "VALUES(?, '해쉬태그')"
                    , i + 3000);

            jdbcTemplate.update("INSERT INTO " +
                            "course_news(" +
                            "course_id, content, created_at, title" +
                            ") " +
                            "VALUES(?, '내용', ?, '제목')"
                    , i + 3000, LocalDateTime.now().minusDays(10));

            jdbcTemplate.update("INSERT INTO " +
                            "course_skill_sets(" +
                            "course_id, tag_name" +
                            ") " +
                            "VALUES(?, '스킬목록')"
                    , i + 3000);
        }
    }

    @GetMapping("/setup-lecture-db")
    public String setupLectures() {
        jdbcTemplate.execute("DELETE from lecture");

        jdbcTemplate.execute("INSERT INTO " +
                "lecture(" +
                "id, course_id, section_id, minutes, seconds, lecture_title, video_url, handout_url, lecture_note, status" +
                ") " +
                "VALUES(1, 1, 1, 10, 24, '테스트 1강', 'xEbAwSof5M4', 'handout', 'note', 'created')");

        jdbcTemplate.execute("INSERT INTO " +
                "lecture(" +
                "id, course_id, section_id, minutes, seconds, lecture_title, video_url, handout_url, lecture_note, status" +
                ") " +
                "VALUES(2, 1, 2, 20, 24, '테스트 2강', 'KHiONHoiGys', 'handout', 'note', 'created')");

        jdbcTemplate.execute("INSERT INTO " +
                "lecture(" +
                "id, course_id, section_id, minutes, seconds, lecture_title, video_url, handout_url, lecture_note, status" +
                ") " +
                "VALUES(3, 1, 2, 30, 24, '테스트 3강', 'quvgobYR8pA', 'handout', 'note', 'created')");

        jdbcTemplate.execute("INSERT INTO " +
                "lecture(" +
                "id, course_id, section_id, minutes, seconds, lecture_title, video_url, handout_url, lecture_note, status" +
                ") " +
                "VALUES(4, 2, 3, 20, 53, '나동빈', 'oFV00xfrQ9Y', 'handout', 'note', 'created')");

        jdbcTemplate.execute("INSERT INTO " +
                "lecture(" +
                "id, course_id, section_id, minutes, seconds, lecture_title, video_url, handout_url, lecture_note, status" +
                ") " +
                "VALUES(5, 2, 3, 8, 32, '코딩 알려주는 누나', 'HFEurBNmMwM', 'handout', 'note', 'created')");

        jdbcTemplate.execute("INSERT INTO " +
                "lecture(" +
                "id, course_id, section_id, minutes, seconds, lecture_title, video_url, handout_url, lecture_note, status" +
                ") " +
                "VALUES(6, 2, 4, 11, 51, '워키토키', 'fv5pIa_l7ns', 'handout', 'note', 'created')");

        jdbcTemplate.execute("INSERT INTO " +
                "lecture(" +
                "id, course_id, section_id, minutes, seconds, lecture_title, video_url, handout_url, lecture_note, status" +
                ") " +
                "VALUES(7, 2, 4, 12, 50, '워키토키 x 샌드버드', 'i1HPaGDnocM', 'handout', 'note', 'created')");

        return "Ok";
    }

    @GetMapping("/setup-inquiry-db")
    public String resetInquiry() {
        List<HashTag> hashTags = List.of(new HashTag("운동"), new HashTag("프로틴"));

//        List<Like> likes1 = List.of(new Like(1L, 1L, new CourseId(1L), clicked), new Like(1L, 2L, new CourseId(1L), clicked));
//        List<Like> likes2 = List.of(new Like(1L, 1L, new CourseId(1L), clicked), new Like(1L, 2L, new CourseId(1L), clicked), new Like(1L, 3L, new CourseId(1L), clicked));
//        List<Like> likes3 = List.of(new Like(1L, 1L, new CourseId(1L), clicked), new Like(1L, 2L, new CourseId(1L), clicked), new Like(1L, 3L, new CourseId(1L), clicked), new Like(1L, 4L, new CourseId(1L), clicked));

        jdbcTemplate.execute("DELETE from inquiry_hash_tags");
        jdbcTemplate.execute("DELETE from comment");
        jdbcTemplate.execute("DELETE from inquiry");

        jdbcTemplate.update("INSERT INTO " +
                "inquiry(" +
                "id, lecture_id, account_id, course_id, title, content, minutes, seconds, publisher, hits, " +
                "anonymous, publish_time, status, replied, solved, replied_at" +
                ") " +
                "VALUES(" +
                "1, 2, 1, 1, '질문 1', 'content', 3, 24, 'publisher', 0, true, ?, 'created', 'completed', 'processing', ?" +
                ")", LocalDateTime.of(2022, 12, 3, 1, 1), LocalDateTime.of(2022, 12, 26, 1, 1));

        jdbcTemplate.update("INSERT INTO " +
                "inquiry(" +
                "id, lecture_id, account_id, course_id, title, content, minutes, seconds, publisher, hits, " +
                "anonymous, publish_time, status, replied, solved, replied_at" +
                ") " +
                "VALUES(" +
                "2, 1, 1, 1, '질문 2', 'content2', 3, 24, 'publisher', 0, true, ?, 'created', 'processing', 'processing', ?" +
                ")", LocalDateTime.of(2022, 12, 5, 1, 1), LocalDateTime.of(2022, 12, 27, 1, 1));

        jdbcTemplate.update("INSERT INTO " +
                "inquiry(" +
                "id, lecture_id, account_id, course_id, title, content, minutes, seconds, publisher, hits, " +
                "anonymous, publish_time, status, replied, solved, replied_at" +
                ") " +
                "VALUES(" +
                "4, 1, 2, 1, '질문 4', 'content2', 3, 24, 'publisher', 0, true, ?, 'created', 'processing', 'processing', ?" +
                ")", LocalDateTime.of(2022, 12, 5, 1, 1), LocalDateTime.of(2022, 12, 27, 1, 1));

        jdbcTemplate.update("INSERT INTO " +
                "inquiry(" +
                "id, lecture_id, account_id, course_id, title, content, minutes, seconds, publisher, hits, " +
                "anonymous, publish_time, status, replied, solved, replied_at" +
                ") " +
                "VALUES(" +
                "3, 3, 3, 2, '질문 3', 'content3', 3, 24, 'publisher', 0, true, ?, 'created', 'processing', 'completed', ?" +
                ")", LocalDateTime.of(2022, 12, 21, 1, 1), LocalDateTime.of(2022, 12, 25, 1, 1));

        for (HashTag hashTag : hashTags) {
            jdbcTemplate.update("INSERT INTO " +
                            "inquiry_hash_tags(inquiry_id, tag_name) " +
                            "VALUES(1, ?)"
                    , hashTag.tagName());
        }

//        setupLikes(likes1, 1);
//        setupLikes(likes2, 2);
//        setupLikes(likes3, 3);

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

        jdbcTemplate.execute("INSERT INTO " +
                "section(id, course_id, goal, status, title) " +
                "VALUES(3, 2, 'goal', 'created', '면접 특강')");

        jdbcTemplate.execute("INSERT INTO " +
                "section(id, course_id, goal, status, title) " +
                "VALUES(4, 2, 'goal', 'created', '이력서 특강')");

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

        jdbcTemplate.update("INSERT INTO " +
                        "progress(" +
                        "id, course_id, section_id, account_id, lecture_id, lecture_title, status, minutes, seconds, updated_at" +
                        ") " +
                        "VALUES(1, 1, 1, 1, 1, '테스트 1강', 'completed', 1, 24, ?)"
                , LocalDateTime.now());

        jdbcTemplate.update("INSERT INTO " +
                        "progress(" +
                        "id, course_id, section_id, account_id, lecture_id, lecture_title, status, minutes, seconds, updated_at" +
                        ") " +
                        "VALUES(2, 1, 1, 1, 2, '테스트 2강', 'completed', 3, 24, ?)"
                , LocalDateTime.now());

        jdbcTemplate.update("INSERT INTO " +
                        "progress(" +
                        "id, course_id, section_id, account_id, lecture_id, lecture_title, status, minutes, seconds, updated_at" +
                        ") " +
                        "VALUES(3, 1, 2, 1, 3, '테스트 1강', 'processing', 5, 24, ?)"
                , LocalDateTime.now());

        jdbcTemplate.update("INSERT INTO " +
                        "progress(" +
                        "id, course_id, section_id, account_id, lecture_id, lecture_title, status, minutes, seconds, updated_at" +
                        ") " +
                        "VALUES(4, 1, 1, 2, 1, '테스트 1강', 'completed', 63, 26, ?)"
                , LocalDateTime.now());

        jdbcTemplate.update("INSERT INTO " +
                        "progress(" +
                        "id, course_id, section_id, account_id, lecture_id, lecture_title, status, minutes, seconds, updated_at" +
                        ") " +
                        "VALUES(5, 1, 1, 2, 2, '테스트 2강', 'processing', 3, 24, ?)"
                , LocalDateTime.now());

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

    @GetMapping("/setup-cart-db")
    public String setupCarts() {
        jdbcTemplate.execute("DELETE from cart_item_ids");
        jdbcTemplate.execute("DELETE from cart");

        jdbcTemplate.execute("INSERT INTO " +
                "cart(id, account_id) " +
                "VALUES(1, 1)");

        jdbcTemplate.execute("INSERT INTO " +
                "cart(id, account_id) " +
                "VALUES(2, 2)");

        jdbcTemplate.execute("INSERT INTO " +
                "cart(id, account_id) " +
                "VALUES(3, 3)");

        return "Ok";
    }

    @GetMapping("/setup-note-db")
    public String setupNotes() {
        jdbcTemplate.execute("DELETE from note");

        jdbcTemplate.update("INSERT INTO " +
                        "note(" +
                        "id, lecture_id, account_id, status, content, minutes, seconds, publish_time" +
                        ") " +
                        "VALUES(1, 1, 1, 'created','노트 1', 1, 24, ?)"
                , LocalDateTime.now()
        );

        jdbcTemplate.update("INSERT INTO " +
                        "note(" +
                        "id, lecture_id, account_id, status, content, minutes, seconds, publish_time" +
                        ") " +
                        "VALUES(2, 1, 1, 'created','노트 2', 2, 24, ?)"
                , LocalDateTime.now()
        );

        jdbcTemplate.update("INSERT INTO " +
                        "note(" +
                        "id, lecture_id, account_id, status, content, minutes, seconds, publish_time" +
                        ") " +
                        "VALUES(3, 2, 1, 'created','노트 3', 3, 24, ?)"
                , LocalDateTime.now()
        );

        jdbcTemplate.update("INSERT INTO " +
                        "note(" +
                        "id, lecture_id, account_id, status, content, minutes, seconds, publish_time" +
                        ") " +
                        "VALUES(4, 1, 1, 'created','노트 4', 4, 24, ?)"
                , LocalDateTime.now()
        );

        jdbcTemplate.update("INSERT INTO " +
                        "note(" +
                        "id, lecture_id, account_id, status, content, minutes, seconds, publish_time" +
                        ") " +
                        "VALUES(5, 3, 1, 'created','노트 5', 5, 24, ?)"
                , LocalDateTime.now()
        );

        return "Ok";
    }

    @GetMapping("/setup-like-db")
    public String setupLikes() {
        jdbcTemplate.execute("DELETE from like_table");

        for (int i = 0; i < 9; i += 1) {
            int index = i + 1;
            int courseId = (i / 3) + 1;
            int accountId = (index % 3) == 0 ? 3 : index % 3;
            jdbcTemplate.update("INSERT INTO " +
                            "like_table(" +
                            "id, course_id, account_id, clicked" +
                            ") " +
                            "VALUES(?, ?, ?, false)",
                    index, courseId, accountId
            );
        }

        return "Ok";
    }
}
