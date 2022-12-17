package jinookk.ourlms.exceptions;

public class LectureNotFound extends RuntimeException {
    public LectureNotFound(Long lectureId) {
        super("lecture not found by Id: " + lectureId);
    }
}
