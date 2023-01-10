package jinookk.ourlms.dtos;

import java.util.List;

public class SectionsDto {
    private List<SectionDto> sections;

    public SectionsDto() {
    }

    public SectionsDto(List<SectionDto> sections) {
        this.sections = sections;
    }

    public List<SectionDto> getSections() {
        return sections;
    }
}
