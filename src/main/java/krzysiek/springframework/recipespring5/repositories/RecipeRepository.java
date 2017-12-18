package krzysiek.springframework.recipespring5.repositories;

import krzysiek.springframework.recipespring5.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
