package jinookk.ourlms.repositories;

import jinookk.ourlms.models.entities.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
}
