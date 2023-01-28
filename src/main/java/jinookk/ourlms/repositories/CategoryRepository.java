package jinookk.ourlms.repositories;

import jinookk.ourlms.models.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
