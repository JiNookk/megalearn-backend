package jinookk.ourlms.fixtures;

import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Cart;
import jinookk.ourlms.models.entities.Category;
import jinookk.ourlms.models.entities.Comment;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.models.entities.Inquiry;
import jinookk.ourlms.models.entities.Lecture;
import jinookk.ourlms.models.entities.Like;
import jinookk.ourlms.models.entities.Note;
import jinookk.ourlms.models.entities.Payment;
import jinookk.ourlms.models.entities.Progress;
import jinookk.ourlms.models.entities.Rating;
import jinookk.ourlms.models.entities.RefreshToken;
import jinookk.ourlms.models.entities.Section;
import jinookk.ourlms.models.entities.Skill;
import jinookk.ourlms.models.enums.CourseStatus;
import jinookk.ourlms.models.vos.Content;
import jinookk.ourlms.models.vos.HandOutUrl;
import jinookk.ourlms.models.vos.ImagePath;
import jinookk.ourlms.models.vos.LectureTime;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.models.vos.Price;
import jinookk.ourlms.models.vos.Title;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.VideoUrl;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.models.vos.ids.InquiryId;
import jinookk.ourlms.models.vos.ids.LectureId;
import jinookk.ourlms.models.vos.ids.SectionId;
import jinookk.ourlms.models.vos.status.Status;

import java.time.LocalDateTime;
import java.util.List;

public class Fixture {
    protected static Account account(String name) {
        UserName userName = new UserName("ojw0828@naver.com");
        Long id = 1L;

        return new Account(id, new Name(name, false), userName);
    }

    public static Cart cart(AccountId accountId) {
        return new Cart(accountId);
    }

    public static Category category(String content) {
        return new Category(new Content(content), "url");
    }

    public static Comment comment(String content) {
        return new Comment(11L, new InquiryId(1L), new AccountId(1L), new Status(Status.CREATED),
                new Name("1st Tester", false), new Content(content), LocalDateTime.now());
    }

    public static Course course(String title) {
        Long id = 1L;
        ImagePath imagePath = new ImagePath("imagePath");
        jinookk.ourlms.models.vos.Category category = new jinookk.ourlms.models.vos.Category("category");
        Name instructor = new Name("instructor", false);
        AccountId accountId = new AccountId(1L);
        Content description = new Content("description");
        Price price = new Price(10000);
        CourseStatus status = CourseStatus.PROCESSING;

        return new Course(id, new Title(title), description, status, imagePath, category, instructor, accountId, price);
    }

    public static Inquiry inquiry(String content) {
        return new Inquiry(1L, new CourseId(1L), new LectureId(1L), new AccountId(1L), List.of(), new Title("title"), new Content(content),
                new LectureTime(1, 24), new Name("tester", false), false, LocalDateTime.now(), LocalDateTime.now());
    }

    public static Inquiry inquiry(Name publisher) {
        return new Inquiry(1L, new CourseId(1L), new LectureId(1L), new AccountId(1L), List.of(), new Title("title"),
                new Content("hi"), new LectureTime(1, 24), publisher, false, LocalDateTime.now(), LocalDateTime.now());
    }

    public static Lecture lecture(String lectureTitle) {
        Long id = 1L;
        CourseId courseId = new CourseId(1L);
        Content lectureNote = new Content("lectureNote");
        SectionId sectionId = new SectionId(1L);
        HandOutUrl handOutUrl = new HandOutUrl("url");
        VideoUrl videoUrl = new VideoUrl("video/url");

        return new Lecture(id, courseId, sectionId, new Title(lectureTitle), lectureNote, handOutUrl, videoUrl);
    }

    public static Lecture lecture(LectureId lectureId) {
        Long id = lectureId.value();
        CourseId courseId = new CourseId(1L);
        Content lectureNote = new Content("lectureNote");
        SectionId sectionId = new SectionId(1L);
        Title title = new Title("title");
        HandOutUrl handOutUrl = new HandOutUrl("url");
        VideoUrl videoUrl = new VideoUrl("video/url");

        return new Lecture(id, courseId, sectionId, title, lectureNote, handOutUrl, videoUrl);
    }

    public static Like like(Boolean clicked) {
        return new Like(new AccountId(1L), new CourseId(1L), clicked);
    }

    public static Note note(String content) {
        return new Note(1L, new LectureId(1L), new AccountId(1L), new Status(Status.CREATED), new Content(content),
                new LectureTime(1, 24), LocalDateTime.now());
    }

    public static Payment payment(int price) {
        return new Payment(1L, new CourseId(1L), new AccountId(1L), new Price(price), new Title("course title"),
                new Name("purchaser", false), LocalDateTime.now());
    }

    public static Progress progress(String lectureTitle) {
        return new Progress(31L, new CourseId(1L), new AccountId(1L), new LectureId(1L), new Title(lectureTitle));
    }

    public static Rating rating(Double point) {
        return new Rating(1L, new AccountId(1L), new CourseId(1L), new Name("author", false), new Content("content"), point);
    }

    public static RefreshToken refreshToken(String expired) {
        return new RefreshToken("tester@email.com", expired);
    }

    public static Section section(String title) {
        return new Section(1L, new CourseId(1L), new Status(Status.CREATED), new Title(title), new Content("goal"));
    }

    public static Skill skill(String skill) {
        return new Skill(skill);
    }
}
