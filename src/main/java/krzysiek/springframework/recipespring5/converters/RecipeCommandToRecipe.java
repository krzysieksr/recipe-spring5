package krzysiek.springframework.recipespring5.converters;

import krzysiek.springframework.recipespring5.commands.RecipeCommand;
import krzysiek.springframework.recipespring5.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final IngredientCommandToIngredient ingredientConverter;
    private final NotesCommandToNotes notesConverter;
    private final CategoryCommandToCategory categoryConverter;


    public RecipeCommandToRecipe(IngredientCommandToIngredient ingredientConverter, NotesCommandToNotes notesConverter, CategoryCommandToCategory categoryConverter) {
        this.ingredientConverter = ingredientConverter;
        this.notesConverter = notesConverter;
        this.categoryConverter = categoryConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand source) {
        if (source == null) {
            return null;
        }

        final Recipe recipe = new Recipe();
        recipe.setId(source.getId());
        recipe.setDescription(source.getDescription());
        recipe.setPrepTime(source.getPrepTime());
        recipe.setCookTime(source.getCookTime());
        recipe.setServings(source.getServings());
        recipe.setSource(source.getSource());
        recipe.setUrl(source.getUrl());
        recipe.setDirections(source.getDirections());
        recipe.setDifficulty(source.getDifficulty());
        recipe.setNotes(notesConverter.convert(source.getNotes()));

        if (source.getIngredients() != null && source.getIngredients().size() > 0) {
            source.getIngredients()
                    .forEach(ingredient -> recipe.getIngredients().add(ingredientConverter.convert(ingredient)));
        }

        if (source.getCategories() != null && source.getCategories().size() > 0) {
            source.getCategories()
                    .forEach(category -> recipe.getCategories().add(categoryConverter.convert(category)));
        }

        return recipe;
    }
}
