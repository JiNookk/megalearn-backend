package jinookk.ourlms.controllers;

import jinookk.ourlms.dtos.SkillsDto;
import jinookk.ourlms.dtos.SkillDto;
import jinookk.ourlms.dtos.SkillRequestDto;
import jinookk.ourlms.services.SkillService;
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
    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping("skills")
    public SkillsDto list() {
        return skillService.list();
    }

    @PostMapping("skills")
    @ResponseStatus(HttpStatus.CREATED)
    public SkillDto skill(
            @RequestBody SkillRequestDto skillRequestDto
    ) {
        return skillService.post(skillRequestDto);
    }

    @DeleteMapping("skills/{skillId}")
    public SkillDto delete(
            @PathVariable Long skillId
    ) {
        return skillService.delete(skillId);
    }
}
