package jinookk.ourlms.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SectionUpdateRequestDto {
    @NotBlank
    private String title;

    @NotNull
    private String goal;

    public SectionUpdateRequestDto() {
    }

    public SectionUpdateRequestDto(String title, String goal) {
        this.title = title;
        this.goal = goal;
    }

    public String getTitle() {
        return title;
    }

    public String getGoal() {
        return goal;
    }
}
