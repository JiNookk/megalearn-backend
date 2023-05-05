package jinookk.ourlms.models.entities;

import jinookk.ourlms.dtos.CartDto;
import jinookk.ourlms.exceptions.CartItemNotFound;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Cart {
    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "account_id"))
    private AccountId accountId;

    @ElementCollection(fetch = FetchType.LAZY)
    @AttributeOverride(name = "value", column = @Column(name = "item_id"))
    private Set<CourseId> itemIds = new HashSet<>();

    public Cart() {
    }

    public Cart(AccountId accountId) {
        this.accountId = accountId;
    }

    public static Cart of(AccountId accountId) {
        return new Cart(accountId);
    }

    public CartDto toDto() {
        return new CartDto(itemIds);
    }

    public Cart addItem(Long itemId) {
        if (itemId == null) {
            return this;
        }

        this.itemIds.add(new CourseId(itemId));

        return this;
    }

    public List<CourseId> itemIds() {
        return List.copyOf(itemIds);
    }

    public Cart removeItems(List<CourseId> courseIds) {
        if (courseIds == null) {
            return this;
        }

        for (CourseId courseId : courseIds) {
            removeId(courseId);
        }

        return this;
    }

    private void removeId(CourseId courseId) {
        if (!this.itemIds.remove(courseId)) {
            throw new CartItemNotFound(courseId == null ? "Null" : courseId.value().toString());
        }
    }

    public Cart removeItem(CourseId itemId) {
        removeId(itemId);

        return this;
    }
}
