package jinookk.ourlms.models.enums;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Level {
    TOBEDETERMINED("미정"),
    BEGINNER("입문"),
    INTERMEDIATE("초급"),
    EXPERT("중급이상");

    private static final Map<String, String> CODE_MAP = Collections.unmodifiableMap(
            Stream.of(values()).collect(Collectors.toMap(Level::getName, Level::name))
    );

    final private String name;

    Level(String name) {
        this.name = name;
    }

    public static Level of(String level, Level prevLevel) {
        return level.isBlank() ? prevLevel : of(level);
    }

    public static Level of(String level) {
        return Level.valueOf(CODE_MAP.get(level));
    }

    public String getName() {
        return name;
    }
}
