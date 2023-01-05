package jinookk.ourlms.models.enums;

public enum Level {
    BEGINNER("입문"),
    INTERMEDIATE("초급"),
    EXPERT("중급");

    final private String name;

    Level(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
