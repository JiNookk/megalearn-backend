package jinookk.ourlms.dtos;

import java.util.List;

public class SectionsDto {
    private List<SectionWithProgressDto> sections;

    public SectionsDto() {
    }

    public SectionsDto(List<SectionWithProgressDto> sections) {
        this.sections = sections;
    }

    public List<SectionWithProgressDto> getSections() {
        return sections;
    }
}
