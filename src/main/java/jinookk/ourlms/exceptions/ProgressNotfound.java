package jinookk.ourlms.exceptions;

import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.LectureId;

public class ProgressNotfound extends RuntimeException {
    public ProgressNotfound(LectureId lectureId) {
        super("Progress is not found by lectureId: " + lectureId);
    }

    public ProgressNotfound(Long progressId) {
        super("Progress is not found by id: " + progressId);
    }

    public ProgressNotfound(LectureId lectureId, AccountId accountId) {
        super("Progress is not found by LectureId: " + lectureId + " and accountId: " + accountId);
    }
}
