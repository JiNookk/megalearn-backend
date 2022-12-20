package jinookk.ourlms.dtos;

import java.util.List;

public class ProgressesDto {
    private List<ProgressDto> progresses;

    public ProgressesDto() {
    }

    public ProgressesDto(List<ProgressDto> progresses) {
        this.progresses = progresses;
    }

    public List<ProgressDto> getProgresses() {
        return progresses;
    }
}
