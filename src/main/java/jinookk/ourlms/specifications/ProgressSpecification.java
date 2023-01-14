package jinookk.ourlms.specifications;

import jinookk.ourlms.models.entities.Progress;
import jinookk.ourlms.models.vos.ids.AccountId;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class ProgressSpecification {
    public static Specification<Progress> equalAccountId(AccountId accountId) {
        return new Specification<Progress>() {
            @Override
            public Predicate toPredicate(Root<Progress> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("accountId"), accountId);
            }
        };
    }

    public static Specification<Progress> betweenCurrentWeek(String date) {
        return new Specification<Progress>() {
            @Override
            public Predicate toPredicate(Root<Progress> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                String trimmed = date.split(" ")[0];

                LocalDateTime dateTime = LocalDateTime.parse(trimmed);

                WeekFields weekFields = WeekFields.of(Locale.getDefault());

                int weekInYear = dateTime.get(weekFields.weekOfWeekBasedYear());

                LocalDateTime dateStart = LocalDateTime.now().with(weekFields.weekOfYear(), weekInYear).with(weekFields.dayOfWeek(), 1);
                LocalDateTime dateEnd = dateStart.plusDays(7);

                return criteriaBuilder.between(root.get("status").get("completedAt"), dateStart, dateEnd);
            }
        };
    }
}
