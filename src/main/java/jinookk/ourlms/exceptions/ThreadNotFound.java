package jinookk.ourlms.exceptions;

public class ThreadNotFound extends RuntimeException {
    public ThreadNotFound(Long threadId) {
        super("Thread is not Found By id: " + threadId);
    }
}
