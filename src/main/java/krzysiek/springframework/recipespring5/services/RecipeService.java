package krzysiek.springframework.recipespring5.services;

import krzysiek.springframework.recipespring5.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipeList();
}
