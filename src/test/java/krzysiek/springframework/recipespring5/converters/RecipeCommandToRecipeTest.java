package krzysiek.springframework.recipespring5.converters;

import krzysiek.springframework.recipespring5.commands.CategoryCommand;
import krzysiek.springframework.recipespring5.commands.IngredientCommand;
import krzysiek.springframework.recipespring5.commands.NotesCommand;
import krzysiek.springframework.recipespring5.commands.RecipeCommand;
import krzysiek.springframework.recipespring5.domain.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RecipeCommandToRecipeTest {
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

    RecipeCommandToRecipe converter;

    @Before
    public void setUp() throws Exception {
        converter = new RecipeCommandToRecipe(new IngredientCommandToIngredient(
                new UnitOfMeasureCommandToUnitOfMeasure()),
                new NotesCommandToNotes(), new CategoryCommandToCategory());
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new RecipeCommand()));
    }

    @Test
    public void convert() {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(ID_VALUE);
        recipeCommand.setDescription(DESCRIPTION);
        recipeCommand.setPrepTime(PREP_TIME);
        recipeCommand.setCookTime(COOK_TIME);
        recipeCommand.setServings(SERVINGS);
        recipeCommand.setSource(SOURCE);
        recipeCommand.setUrl(URL);
        recipeCommand.setDirections(DIRECTIONS);
        recipeCommand.setDifficulty(DIFFICULTY);

        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(NOTES_ID);
        recipeCommand.setNotes(notesCommand);

        IngredientCommand ingredient1 = new IngredientCommand();
        ingredient1.setId(INGRED_ID_1);
        IngredientCommand ingredient2 = new IngredientCommand();
        ingredient2.setId(INGRED_ID_2);

        recipeCommand.getIngredients().add(ingredient1);
        recipeCommand.getIngredients().add(ingredient2);

        CategoryCommand category1 = new CategoryCommand();
        category1.setId(CAT_ID_1);
        CategoryCommand category2 = new CategoryCommand();
        category2.setId(CAT_ID2);

        recipeCommand.getCategories().add(category1);
        recipeCommand.getCategories().add(category2);

        //when
        Recipe recipe = converter.convert(recipeCommand);

        //then
        assertNotNull(recipe);
        assertNotNull(recipe.getCategories());
        assertNotNull(recipe.getIngredients());
        assertEquals(ID_VALUE, recipe.getId());
        assertEquals(COOK_TIME, recipe.getCookTime());
        assertEquals(PREP_TIME, recipe.getPrepTime());
        assertEquals(DESCRIPTION, recipe.getDescription());
        assertEquals(DIFFICULTY, recipe.getDifficulty());
        assertEquals(DIRECTIONS, recipe.getDirections());
        assertEquals(SERVINGS, recipe.getServings());
        assertEquals(SOURCE, recipe.getSource());
        assertEquals(URL, recipe.getUrl());
        assertEquals(NOTES_ID, recipe.getNotes().getId());
        assertEquals(2, recipe.getCategories().size());
        assertEquals(2, recipe.getIngredients().size());
    }

}