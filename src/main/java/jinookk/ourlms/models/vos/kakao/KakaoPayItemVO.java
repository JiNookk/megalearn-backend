package jinookk.ourlms.models.vos.kakao;

import jinookk.ourlms.models.entities.Course;

import java.util.List;

public class KakaoPayItemVO {
    private List<Course> courses;

    public KakaoPayItemVO() {
    }

    public KakaoPayItemVO(List<Course> courses) {
        this.courses = courses;
    }

    public List<Course> getCourses() {
        return courses;
    }
}
