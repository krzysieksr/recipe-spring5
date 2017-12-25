package krzysiek.springframework.recipespring5.services;

import krzysiek.springframework.recipespring5.commands.RecipeCommand;
import krzysiek.springframework.recipespring5.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipeList();

    Recipe findById(Long id);

    RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);

    RecipeCommand findCommandById(Long id);
}
