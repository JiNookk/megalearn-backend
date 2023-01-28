package jinookk.ourlms.dtos;

import javax.validation.constraints.NotNull;
import java.util.List;

public class CourseUpdateRequestDto {
    @NotNull
    private String title;

    @NotNull
    private String category;

    @NotNull
    private String description;

    @NotNull
    private String imagePath;

    @NotNull
    private String status;

    @NotNull
    private String level;

    @NotNull
    private List<String> skills;

    @NotNull
    private Integer price;

    public CourseUpdateRequestDto() {
    }

    public CourseUpdateRequestDto(String title, String category, String description, String imagePath,
                                  String status, String level, List<String> skills, Integer price) {
        this.title = title;
        this.category = category;
        this.description = description;
        this.imagePath = imagePath;
        this.status = status;
        this.level = level;
        this.skills = skills;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Integer getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public String getLevel() {
        return level;
    }

    public List<String> getSkills() {
        return skills;
    }
}
