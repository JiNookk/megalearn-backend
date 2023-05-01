package jinookk.ourlms.applications.section;

import jinookk.ourlms.dtos.SectionDto;
import jinookk.ourlms.dtos.SectionRequestDto;
import jinookk.ourlms.models.entities.Section;
import jinookk.ourlms.repositories.SectionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CreateSectionService {
    private final SectionRepository sectionRepository;

    public CreateSectionService(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    public SectionDto create(SectionRequestDto sectionRequestDto) {
        Section section = Section.of(sectionRequestDto);

        Section saved = sectionRepository.save(section);

        return saved.toSectionDto();
    }
}
