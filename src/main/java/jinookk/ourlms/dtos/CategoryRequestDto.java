package jinookk.ourlms.dtos;

public class CategoryRequestDto {
    private String category;
    private String url;

    public CategoryRequestDto() {
    }

    public CategoryRequestDto(String category, String url) {
        this.category = category;
        this.url = url;
    }

    public String getCategory() {
        return category;
    }

    public String getUrl() {
        return url;
    }
}
