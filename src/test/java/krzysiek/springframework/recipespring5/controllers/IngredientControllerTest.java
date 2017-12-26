package krzysiek.springframework.recipespring5.controllers;

import krzysiek.springframework.recipespring5.commands.IngredientCommand;
import krzysiek.springframework.recipespring5.commands.RecipeCommand;
import krzysiek.springframework.recipespring5.services.IngredientService;
import krzysiek.springframework.recipespring5.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class IngredientControllerTest {

    private final static Long ID = 1L;

    @Mock
    IngredientService ingredientService;

    @Mock
    RecipeService recipeService;

    IngredientController ingredientController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        ingredientController = new IngredientController(recipeService, ingredientService);
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
    }

    @Test
    public void listIngredientsTest() throws Exception {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(ID);

        //when
        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

        //then
        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recipe/ingredient/list"));

        verify(recipeService, times(1)).findCommandById(anyLong());
    }

    @Test
    public void showRecipeIngredientTest() throws Exception {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(ID);

        //when
        when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);

        //then
        mockMvc.perform(get("/recipe/1/ingredient/1/show"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(view().name("recipe/ingredient/show"));

        verify(ingredientService, times(1)).findByRecipeIdAndIngredientId(anyLong(), anyLong());
    }
}