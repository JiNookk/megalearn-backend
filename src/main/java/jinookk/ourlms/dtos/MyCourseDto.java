package jinookk.ourlms.dtos;

import jinookk.ourlms.models.vos.ImagePath;
import jinookk.ourlms.models.vos.Title;

public class MyCourseDto {
    private Long id;
    private String title;
    private String imagePath;

    public MyCourseDto() {
    }

    public MyCourseDto(Long id, Title title, ImagePath imagePath) {
        this.id = id;
        this.title = title.value();
        this.imagePath = imagePath.value();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImagePath() {
        return imagePath;
    }
}
