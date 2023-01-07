package jinookk.ourlms.dtos;

import java.util.List;

public class KakaoRequestDto {
    private List<Long> courseIds;

    public KakaoRequestDto() {
    }

    public KakaoRequestDto(List<Long> courseIds) {
        this.courseIds = courseIds;
    }

    public List<Long> getCourseIds() {
        return courseIds;
    }
}
