package krzysiek.springframework.recipespring5.services;

import krzysiek.springframework.recipespring5.commands.IngredientCommand;
import krzysiek.springframework.recipespring5.converters.IngredientCommandToIngredient;
import krzysiek.springframework.recipespring5.converters.IngredientToIngredientCommand;
import krzysiek.springframework.recipespring5.domain.Ingredient;
import krzysiek.springframework.recipespring5.domain.Recipe;
import krzysiek.springframework.recipespring5.repositories.RecipeRepository;
import krzysiek.springframework.recipespring5.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(RecipeRepository recipeRepository,
                                 IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient,
                                 UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
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

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());

        if (!recipeOptional.isPresent()) {
            log.error("Recipe not found for id: " + ingredientCommand.getRecipeId());
//            throw new RuntimeException("Recipe Not Found");
            return new IngredientCommand();
        } else {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe.getIngredients()
                    .stream().filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            if (ingredientOptional.isPresent()) {
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(ingredientCommand.getDescription());
                ingredientFound.setAmount(ingredientCommand.getAmount());
                ingredientFound.setUom(unitOfMeasureRepository
                        .findById(ingredientCommand.getUom().getId())
                        .orElseThrow(() -> new RuntimeException("UOM NOT FOUND")));
            } else {
                //add new Ingredient
                Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
                recipe.addIngredient(ingredient);
                ingredient.setRecipe(recipe);
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            if (!savedIngredientOptional.isPresent()) {
                savedIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(recipeIngredient -> recipeIngredient.getDescription().equals(ingredientCommand.getDescription()))
                        .filter(recipeIngredient -> recipeIngredient.getAmount().equals(ingredientCommand.getAmount()))
                        .filter(recipeIngredient -> recipeIngredient.getUom().getId().equals(ingredientCommand.getUom().getId()))
                        .findFirst();
            }

            return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
        }
    }

    @Override
    public void deleteById(Long recipeId, Long ingredientToDeleteId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()) {
            log.error("recipe id not found. Id: " + recipeId);
            throw new RuntimeException("Recipe Not Found");
        }
        Recipe recipe = recipeOptional.get();

        Optional<Ingredient> ingredientOptional = recipeOptional.get().getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientToDeleteId))
                .findFirst();

        if (ingredientOptional.isPresent()) {
            log.debug("found ingredient");
            Ingredient ingredientToDelete = ingredientOptional.get();
            ingredientToDelete.setRecipe(null);
            recipe.getIngredients().remove(ingredientToDelete);
            recipeRepository.save(recipe);
        }
    }
}
