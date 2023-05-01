package jinookk.ourlms.controllers;

import jinookk.ourlms.applications.skill.CreateSkillService;
import jinookk.ourlms.applications.skill.DeleteSkillService;
import jinookk.ourlms.applications.skill.GetSkillService;
import jinookk.ourlms.dtos.SkillDto;
import jinookk.ourlms.dtos.SkillRequestDto;
import jinookk.ourlms.dtos.SkillsDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SkillController {
    private final GetSkillService getSkillService;
    private final CreateSkillService createSkillService;
    private final DeleteSkillService deleteSkillService;

    public SkillController(GetSkillService getSkillService,
                           CreateSkillService createSkillService,
                           DeleteSkillService deleteSkillService) {
        this.getSkillService = getSkillService;
        this.createSkillService = createSkillService;
        this.deleteSkillService = deleteSkillService;
    }

    @GetMapping("skills")
    public SkillsDto list() {
        return getSkillService.list();
    }

    @PostMapping("skills")
    @ResponseStatus(HttpStatus.CREATED)
    public SkillDto skill(
            @RequestBody SkillRequestDto skillRequestDto
    ) {
        return createSkillService.post(skillRequestDto);
    }

    @DeleteMapping("skills/{skillId}")
    public SkillDto delete(
            @PathVariable Long skillId
    ) {
        return deleteSkillService.delete(skillId);
    }
}
