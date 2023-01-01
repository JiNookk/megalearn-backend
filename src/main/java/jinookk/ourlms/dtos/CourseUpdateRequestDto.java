package jinookk.ourlms.dtos;

import javax.validation.constraints.NotNull;

public class CourseUpdateRequestDto {
    @NotNull
    private String title;

    @NotNull
    private String category;

    @NotNull
    private String description;

    @NotNull
    private String thumbnailPath;

    @NotNull
    private String status;

    @NotNull
    private Integer price;

    public CourseUpdateRequestDto() {
    }

    public CourseUpdateRequestDto(String title, String category, String description, String thumbnailPath,
                                  String status, Integer price) {
        this.title = title;
        this.category = category;
        this.description = description;
        this.thumbnailPath = thumbnailPath;
        this.status = status;
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

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public Integer getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }
}
