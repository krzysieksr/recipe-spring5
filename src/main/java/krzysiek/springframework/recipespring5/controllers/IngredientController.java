package krzysiek.springframework.recipespring5.controllers;

import krzysiek.springframework.recipespring5.commands.IngredientCommand;
import krzysiek.springframework.recipespring5.services.IngredientService;
import krzysiek.springframework.recipespring5.services.RecipeService;
import krzysiek.springframework.recipespring5.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/recipe")
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping
    @RequestMapping("/{recipeId}/ingredients")
    public String listIngredients(@PathVariable Long recipeId, Model model) {
        log.debug("Getting ingredient list for recipe id: " + recipeId);

        model.addAttribute("recipe", recipeService.findCommandById(recipeId));

        return "recipe/ingredient/list";
    }

    @GetMapping
    @RequestMapping("/{recipeId}/ingredient/{ingredientId}/show")
    public String showRecipeIngredient(@PathVariable Long recipeId, @PathVariable Long ingredientId, Model model) {
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId));

        return "recipe/ingredient/show";
    }

    @GetMapping
    @RequestMapping("/{recipeId}/ingredient/{ingredientId}/update")
    public String updateIngredientOfRecipe(@PathVariable Long recipeId,
                                           @PathVariable Long ingredientId, Model model) {
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId));

        model.addAttribute("uomList", unitOfMeasureService.listAllUOMs());

        return "recipe/ingredient/ingredientform";
    }

    @PostMapping
    @RequestMapping("/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand ingredientCommand) {
        IngredientCommand savedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);

        log.debug("saved recipe id: " + savedIngredientCommand.getRecipeId());
        log.debug("saved ingredient id: " + savedIngredientCommand.getId());

        return "redirect:/recipe/" + savedIngredientCommand.getRecipeId() + "/ingredient/" + savedIngredientCommand.getId() + "/show";
    }

}
