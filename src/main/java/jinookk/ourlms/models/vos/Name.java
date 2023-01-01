package jinookk.ourlms.models.vos;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Name {
    // 익명으로 게시한 글을 계속 익명으로 사용하고 싶다. 댓글도 익명으로 사용하고 싶다.
    private String value;

    public Name() {
    }

    public Name(String value) {
        this.value = value;
    }

    public Name(String value, Boolean anonymous) {
        this.value = anonymous ? randomizeName() : value;
    }

    public String randomizeName() {
        List<String> prefixes = List.of("씩씩거리는", "엄지척", "권투하는", "귀여운", "기타치는", "눈빛 애교", "눈물 바다에 빠진",
                "돈다발 들고 좋아하는", "라면먹는", "라이언 붕붕카를 탄", "멋쩍은");
        List<String> suffixes = List.of("무지", "프로도", "라이언", "튜브", "네오", "제이지", "어피치");

        String prefix = prefixes.get(new Random().nextInt(prefixes.size()));
        String suffix = suffixes.get(new Random().nextInt(suffixes.size()));

        return String.join(" ", prefix, suffix);
    }

    public String value() {
        return value;
    }

    public void delete() {
        this.value = null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object other) {
        return other != null &&
                other.getClass().equals(Name.class) &&
                ((Name) other).value.equals(this.value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
