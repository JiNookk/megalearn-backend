package jinookk.ourlms.applications.section;

import jinookk.ourlms.dtos.SectionDto;
import jinookk.ourlms.dtos.SectionRequestDto;
import jinookk.ourlms.dtos.SectionUpdateRequestDto;
import jinookk.ourlms.dtos.SectionsDto;
import jinookk.ourlms.exceptions.SectionNotFound;
import jinookk.ourlms.models.entities.Section;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.ProgressRepository;
import jinookk.ourlms.repositories.SectionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class GetSectionService {
    private final SectionRepository sectionRepository;

    public GetSectionService(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }


    public SectionsDto list() {
        List<Section> sections = sectionRepository.findAll();

        List<SectionDto> sectionDtos = sections.stream()
                .map(Section::toSectionDto)
                .toList();

        return new SectionsDto(sectionDtos);
    }

    public SectionsDto listByCourseId(CourseId courseId) {
        List<Section> sections = sectionRepository.findAllByCourseId(courseId);

        List<SectionDto> sectionDtos = sections.stream()
                .map(Section::toSectionDto)
                .toList();

        return new SectionsDto(sectionDtos);
    }
}
