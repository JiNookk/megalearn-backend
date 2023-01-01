package jinookk.ourlms.exceptions;

public class SectionNotFound extends RuntimeException {
    public SectionNotFound(Long sectionId) {
        super("section is not found by id: " + sectionId);
    }
}
