package jinookk.ourlms.exceptions;

public class CourseNotFound extends RuntimeException {
    public CourseNotFound(Long courseId) {
        super("course not found by ID: " + courseId);
    }
}
