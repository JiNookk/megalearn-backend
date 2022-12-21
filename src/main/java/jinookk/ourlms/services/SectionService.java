package jinookk.ourlms.services;

import jinookk.ourlms.dtos.SectionDto;
import jinookk.ourlms.dtos.SectionsDto;
import jinookk.ourlms.models.entities.Section;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.models.vos.ids.SectionId;
import jinookk.ourlms.repositories.ProgressRepository;
import jinookk.ourlms.repositories.SectionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class SectionService {
    private final SectionRepository sectionRepository;
    private final ProgressRepository progressRepository;

    public SectionService(SectionRepository sectionRepository,
                          ProgressRepository progressRepository) {
        this.sectionRepository = sectionRepository;
        this.progressRepository = progressRepository;
    }

    public SectionsDto list(CourseId courseId, AccountId accountId) {
        List<Section> sections = sectionRepository.findAllByCourseId(courseId);

        List<SectionDto> sectionDtos = sections.stream()
                .map(section -> section.toSectionDto(
                        progressRepository.findAllByAccountIdAndSectionId(
                                accountId, new SectionId(section.id()))))
                .toList();

        return new SectionsDto(sectionDtos);
    }
}
