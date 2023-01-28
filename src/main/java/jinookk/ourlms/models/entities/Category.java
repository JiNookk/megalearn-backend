package jinookk.ourlms.models.entities;

import jinookk.ourlms.dtos.CategoryDto;
import jinookk.ourlms.dtos.CategoryRequestDto;
import jinookk.ourlms.models.vos.Content;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Category {
    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "content"))
    private Content content;

    private String url;

    public Category() {
    }

    public Category(Content content, String url) {
        this.content = content;
        this.url = url;
    }

    public static Category fake(String content) {
        return fake(new Content(content));
    }

    private static Category fake(Content content) {
        return new Category(content, "url");
    }

    public static Category of(CategoryRequestDto categoryRequestDto) {
        return new Category(new Content(categoryRequestDto.getCategory()), categoryRequestDto.getUrl());
    }

    public CategoryDto toDto() {
        return new CategoryDto(id, content, url);
    }
}
