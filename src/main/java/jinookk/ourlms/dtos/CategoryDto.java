package jinookk.ourlms.dtos;

import jinookk.ourlms.models.vos.Content;

public class CategoryDto {
    private Long id;
    private String content;
    private String url;

    public CategoryDto() {
    }

    public CategoryDto(Long id, Content content, String url) {
        this.id = id;
        this.content = content.value();
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }
}
