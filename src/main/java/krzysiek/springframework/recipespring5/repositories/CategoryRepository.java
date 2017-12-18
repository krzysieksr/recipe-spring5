package krzysiek.springframework.recipespring5.repositories;

import krzysiek.springframework.recipespring5.domain.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Optional<Category> findByDescription(String description);
}
