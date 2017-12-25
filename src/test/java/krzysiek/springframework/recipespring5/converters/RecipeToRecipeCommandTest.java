package krzysiek.springframework.recipespring5.converters;

import krzysiek.springframework.recipespring5.commands.RecipeCommand;
import krzysiek.springframework.recipespring5.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;

public class RecipeToRecipeCommandTest {

    private final static Long ID_VALUE = 1L;
    private final static String DESCRIPTION = "description";
    public static final String DIRECTIONS = "Directions";
    public static final Difficulty DIFFICULTY = Difficulty.EASY;
    private final static Integer PREP_TIME = 20;
    private final static Integer COOK_TIME = 12;
    private final static Integer SERVINGS = 4;
    private final static String SOURCE = "Smaker";
    private final static String URL = "https://smaker.pl/";
    public static final Long CAT_ID_1 = 1L;
    public static final Long CAT_ID2 = 2L;
    public static final Long INGRED_ID_1 = 3L;
    public static final Long INGRED_ID_2 = 4L;
    public static final Long NOTES_ID = 9L;

    RecipeToRecipeCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new RecipeToRecipeCommand(new IngredientToIngredientCommand(
                new UnitOfMeasureToUnitOfMeasureCommand()),
                new NotesToNotesCommand(), new CategoryToCategoryCommand());
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new Recipe()));
    }

    @Test
    public void convert() {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(ID_VALUE);
        recipe.setDescription(DESCRIPTION);
        recipe.setPrepTime(PREP_TIME);
        recipe.setCookTime(COOK_TIME);
        recipe.setServings(SERVINGS);
        recipe.setSource(SOURCE);
        recipe.setUrl(URL);
        recipe.setDirections(DIRECTIONS);
        recipe.setDifficulty(DIFFICULTY);

        Notes notes = new Notes();
        notes.setId(NOTES_ID);
        recipe.setNotes(notes);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(INGRED_ID_1);
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(INGRED_ID_2);

        recipe.getIngredients().add(ingredient1);
        recipe.getIngredients().add(ingredient2);

        Category category1 = new Category();
        category1.setId(CAT_ID_1);
        Category category2 = new Category();
        category2.setId(CAT_ID2);

        recipe.getCategories().add(category1);
        recipe.getCategories().add(category2);

        //when
        RecipeCommand recipeCommand = converter.convert(recipe);

        //then
        assertNotNull(recipeCommand);
        assertNotNull(recipeCommand.getCategories());
        assertNotNull(recipeCommand.getIngredients());
        assertEquals(ID_VALUE, recipeCommand.getId());
        assertEquals(COOK_TIME, recipeCommand.getCookTime());
        assertEquals(PREP_TIME, recipeCommand.getPrepTime());
        assertEquals(DESCRIPTION, recipeCommand.getDescription());
        assertEquals(DIFFICULTY, recipeCommand.getDifficulty());
        assertEquals(DIRECTIONS, recipeCommand.getDirections());
        assertEquals(SERVINGS, recipeCommand.getServings());
        assertEquals(SOURCE, recipeCommand.getSource());
        assertEquals(URL, recipeCommand.getUrl());
        assertEquals(NOTES_ID, recipeCommand.getNotes().getId());
        assertEquals(2, recipeCommand.getCategories().size());
        assertEquals(2, recipeCommand.getIngredients().size());
    }
}