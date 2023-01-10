package jinookk.ourlms.services;

import jinookk.ourlms.dtos.SectionDto;
import jinookk.ourlms.dtos.SectionUpdateRequestDto;
import jinookk.ourlms.dtos.SectionWithProgressDto;
import jinookk.ourlms.dtos.SectionRequestDto;
import jinookk.ourlms.dtos.SectionsDto;
import jinookk.ourlms.dtos.SectionsWithProgressDto;
import jinookk.ourlms.exceptions.SectionNotFound;
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

    public SectionsDto list() {
        List<Section> sections = sectionRepository.findAll();

        List<SectionDto> sectionDtos = sections.stream()
                .map(Section::toSectionDto)
                .toList();

        return new SectionsDto(sectionDtos);
    }

    public SectionsWithProgressDto listWithProgress(CourseId courseId, AccountId accountId) {
        List<Section> sections = sectionRepository.findAllByCourseId(courseId);

        List<SectionWithProgressDto> sectionWithProgressDtos = sections.stream()
                .map(section -> section.toSectionWithProgressDto(
                        progressRepository.findAllByAccountIdAndSectionId(
                                accountId, new SectionId(section.id()))))
                .toList();

        return new SectionsWithProgressDto(sectionWithProgressDtos);
    }

    public SectionDto create(SectionRequestDto sectionRequestDto) {
        Section section = Section.of(sectionRequestDto);

        Section saved = sectionRepository.save(section);

        return saved.toSectionDto();
    }


    public SectionDto update(Long sectionId, SectionUpdateRequestDto sectionUpdateRequestDto) {
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new SectionNotFound(sectionId));

        section.update(sectionUpdateRequestDto);

        return section.toSectionDto();
    }

    public SectionDto delete(Long sectionId) {
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new SectionNotFound(sectionId));

        section.delete();

        return section.toSectionDto();
    }
}
