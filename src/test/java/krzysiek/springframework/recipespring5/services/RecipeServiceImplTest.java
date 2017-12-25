package krzysiek.springframework.recipespring5.services;

import krzysiek.springframework.recipespring5.converters.RecipeToRecipeCommand;
import krzysiek.springframework.recipespring5.converters.RecipeCommandToRecipe;
import krzysiek.springframework.recipespring5.domain.Recipe;
import krzysiek.springframework.recipespring5.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    RecipeServiceImpl recipeService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    public void getRecipeList() {
        Recipe recipe = new Recipe();
        HashSet recipeData = new HashSet();

        recipeData.add(recipe);

        when(recipeRepository.findAll()).thenReturn(recipeData);

        Set<Recipe> recipes = recipeService.getRecipeList();

        assertEquals(1, recipes.size());
        verify(recipeRepository, times(1)).findAll();
        verify(recipeRepository, never()).findById(anyLong());
    }

    @Test
    public void findById() {
        Recipe recipe = new Recipe();
        Long id = 1L;
        Optional<Recipe> optionalRecipe = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(optionalRecipe);

        Recipe recipeReturned = recipeService.findById(id);

        assertNotNull(recipeReturned);
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }
}