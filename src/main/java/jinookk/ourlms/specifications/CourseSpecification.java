package jinookk.ourlms.specifications;

import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.models.enums.Level;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


public class CourseSpecification {
    public static Specification<Course> equalLevel(Level level) {
        return new Specification<Course>() {
            @Override
            public Predicate toPredicate(Root<Course> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("level"), level);
            }
        };
    }

    public static Specification<Course> equalCost(String cost) {
        return new Specification<Course>() {
            @Override
            public Predicate toPredicate(Root<Course> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                if (cost.equals("무료")) {
                    return criteriaBuilder.equal(root.get("price").get("value"), 0);
                }

                if (cost.equals("유료")) {
                    return criteriaBuilder.greaterThan(root.get("price").get("value"), 0);
                }

                return null;
            }
        };
    }

    public static Specification<Course> equalSkills(String skill) {
        return new Specification<Course>() {
            @Override
            public Predicate toPredicate(Root<Course> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {

                return criteriaBuilder.equal(root.join("skillSets").get("tagName"), skill);
            }
        };
    }

    public static Specification<Course> likeContent(String content) {
        return new Specification<Course>() {
            @Override
            public Predicate toPredicate(Root<Course> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {

                return criteriaBuilder.like(root.get("description").get("value"), "%" + content + "%");
            }
        };
    }

    public static Specification<Course> likeGoals(String content) {
        return new Specification<Course>() {
            @Override
            public Predicate toPredicate(Root<Course> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {

                return criteriaBuilder.like(root.join("goals").get("value"), "%" + content + "%");
            }
        };
    }

    public static Specification<Course> likeTitle(String content) {
        return new Specification<Course>() {
            @Override
            public Predicate toPredicate(Root<Course> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {

                return criteriaBuilder.like(root.get("title").get("value"), "%" + content + "%");
            }
        };
    }
}
