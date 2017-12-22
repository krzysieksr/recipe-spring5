package krzysiek.springframework.recipespring5.controllers;

import krzysiek.springframework.recipespring5.services.RecipeServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class IndexControllerTest {

    @Mock
    RecipeServiceImpl recipeService;

    @Mock
    Model model;

    IndexController indexController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        indexController = new IndexController(recipeService);
    }

    @Test
    public void getIndexPage() {
//        Recipe recipe = new Recipe();
//        HashSet recipeData = new HashSet();
//
//        recipeData.add(recipe); WE DON'T NEED THIS

//        when(recipeService.getRecipeList()).thenReturn(recipeData);

        assertEquals("index", indexController.getIndexPage(model));
        verify(recipeService, times(1)).getRecipeList();
        verify(model, times(1)).addAttribute(eq("recipes"), anySet());
    }
}