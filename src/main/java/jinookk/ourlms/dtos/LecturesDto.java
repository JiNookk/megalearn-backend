package jinookk.ourlms.dtos;

import java.util.List;

public class LecturesDto {
    private List<LectureDto> lectures;

    public LecturesDto() {
    }

    public LecturesDto(List<LectureDto> lectures) {
        this.lectures = lectures;
    }

    public List<LectureDto> getLectures() {
        return lectures;
    }
}
