package jinookk.ourlms.dtos;

public class LectureUpdateRequestDto {
    private String title;
    private String videoUrl;
    private String lectureNote;
    private String filePath;

    public LectureUpdateRequestDto() {
    }

    public LectureUpdateRequestDto(String title, String videoUrl, String lectureNote, String filePath) {
        this.title = title;
        this.videoUrl = videoUrl;
        this.lectureNote = lectureNote;
        this.filePath = filePath;
    }

    public String getTitle() {
        return title;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getLectureNote() {
        return lectureNote;
    }

    public String getFilePath() {
        return filePath;
    }
}
