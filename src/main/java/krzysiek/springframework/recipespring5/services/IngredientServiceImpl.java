package krzysiek.springframework.recipespring5.services;

import krzysiek.springframework.recipespring5.commands.IngredientCommand;
import krzysiek.springframework.recipespring5.converters.IngredientToIngredientCommand;
import krzysiek.springframework.recipespring5.domain.Recipe;
import krzysiek.springframework.recipespring5.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand ingredientToIngredientCommand) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()) {
            log.error("recipe id not found. Id: " + recipeId);
            throw new RuntimeException("Recipe Not Found");
        }

        Optional<IngredientCommand> ingredientCommandOptional = recipeOptional.get().getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient))
                .findFirst();

        if (!ingredientCommandOptional.isPresent()) {
            log.error("Ingredient id not found: " + ingredientId);
            throw new RuntimeException("Ingredient Not Found");
        }

        return ingredientCommandOptional.get();
    }
}
