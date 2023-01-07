package jinookk.ourlms.dtos;

import java.util.List;

public class CartRequestDto {
    private List<Long> itemIds;

    public CartRequestDto() {
    }

    public CartRequestDto(List<Long> itemIds) {
        this.itemIds = itemIds;
    }

    public List<Long> getItemIds() {
        return itemIds;
    }
}
