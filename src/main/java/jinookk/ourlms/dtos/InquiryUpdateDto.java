package jinookk.ourlms.dtos;

import java.util.List;

public class InquiryUpdateDto {
    private String title;
    private List<String> hashTags;
    private String content;

    public InquiryUpdateDto() {
    }

    public InquiryUpdateDto(String title, List<String> hashTags, String content) {
        this.title = title;
        this.hashTags = hashTags;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getHashTags() {
        return hashTags;
    }

    public String getContent() {
        return content;
    }
}
