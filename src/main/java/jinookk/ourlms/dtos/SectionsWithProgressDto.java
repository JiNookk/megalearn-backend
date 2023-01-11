package jinookk.ourlms.dtos;

import java.util.List;

public class SectionsWithProgressDto {
    private List<SectionWithProgressDto> sections;

    public SectionsWithProgressDto() {
    }

    public SectionsWithProgressDto(List<SectionWithProgressDto> sections) {
        this.sections = sections;
    }

    public List<SectionWithProgressDto> getSections() {
        return sections;
    }
}
