package jinookk.ourlms.dtos;

import jinookk.ourlms.models.vos.ids.CourseId;

import java.util.List;
import java.util.Set;

public class CartDto {
    private List<Long> itemIds;

    public CartDto() {
    }

    public CartDto(Set<CourseId> itemIds) {
        this.itemIds = itemIds.stream()
                .map(CourseId::value)
                .toList();
    }

    public List<Long> getItemIds() {
        return itemIds;
    }
}
