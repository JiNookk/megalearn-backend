package jinookk.ourlms.applications.section;

import jinookk.ourlms.dtos.SectionDto;
import jinookk.ourlms.dtos.SectionUpdateRequestDto;
import jinookk.ourlms.exceptions.SectionNotFound;
import jinookk.ourlms.models.entities.Section;
import jinookk.ourlms.repositories.SectionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UpdateSectionService {
    private final SectionRepository sectionRepository;

    public UpdateSectionService(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    public SectionDto update(Long sectionId, SectionUpdateRequestDto sectionUpdateRequestDto) {
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new SectionNotFound(sectionId));

        section.update(sectionUpdateRequestDto);

        return section.toSectionDto();
    }
}
