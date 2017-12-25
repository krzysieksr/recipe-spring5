package krzysiek.springframework.recipespring5.services;

import krzysiek.springframework.recipespring5.commands.RecipeCommand;
import krzysiek.springframework.recipespring5.converters.RecipeToRecipeCommand;
import krzysiek.springframework.recipespring5.domain.Recipe;
import krzysiek.springframework.recipespring5.repositories.RecipeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceIT {

    public static final String NEW_DESCRIPTION = "New description";

    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Transactional
    @Test
    public void testSaveOfDescription() {
        //given
        Iterable<Recipe> recipes = recipeRepository.findAll();
        Recipe recipeTest = recipes.iterator().next();
        RecipeCommand testRecipeCommand = recipeToRecipeCommand.convert(recipeTest);

        //when
        testRecipeCommand.setDescription(NEW_DESCRIPTION);
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(testRecipeCommand);

        //then
        assertEquals(NEW_DESCRIPTION, savedRecipeCommand.getDescription());
        assertEquals(recipeTest.getId(), savedRecipeCommand.getId());
        assertEquals(recipeTest.getCategories().size(), savedRecipeCommand.getCategories().size());
        assertEquals(recipeTest.getIngredients().size(), savedRecipeCommand.getIngredients().size());
    }
}