package jinookk.ourlms.models.vos;

import jinookk.ourlms.dtos.HashTagDto;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class HashTag {
    private final String tagName;

    protected HashTag() {
        tagName = null;
    }

    public HashTag(String tagName) {
        this.tagName = tagName;
    }

    public String tagName() {
        return tagName;
    }

    public HashTagDto toDto() {
        return new HashTagDto(tagName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagName);
    }

    @Override
    public boolean equals(Object other) {
        return other != null &&
                other.getClass().equals(HashTag.class) &&
                ((HashTag) other).tagName.equals(this.tagName);
    }

    @Override
    public String toString() {
        return "HashTag tagName: " + tagName;
    }
}
