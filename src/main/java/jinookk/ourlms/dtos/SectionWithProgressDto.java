package jinookk.ourlms.dtos;

import jinookk.ourlms.models.vos.Title;

import java.util.List;

public class SectionWithProgressDto {
    private Long id;
    private String title;
    private List<ProgressDto> progresses;

    public SectionWithProgressDto() {
    }

    public SectionWithProgressDto(Long id, Title title, List<ProgressDto> progresses) {
        this.id = id;
        this.title = title.value();
        this.progresses = progresses;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<ProgressDto> getProgresses() {
        return progresses;
    }
}
