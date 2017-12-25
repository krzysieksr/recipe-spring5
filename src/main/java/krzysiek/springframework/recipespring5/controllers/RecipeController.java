package krzysiek.springframework.recipespring5.controllers;

import krzysiek.springframework.recipespring5.commands.RecipeCommand;
import krzysiek.springframework.recipespring5.domain.Difficulty;
import krzysiek.springframework.recipespring5.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/recipe")
@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    @RequestMapping("/{id}/show")
    public String showById(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.findById(id));
        return "recipe/show";
    }

    @GetMapping
    @RequestMapping("/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());
        model.addAttribute("difficult", Difficulty.values());

        return "recipe/recipeform";
    }

    @GetMapping
    @RequestMapping("/{id}/update")
    public String updateRecipe(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(id));

        return "recipe/recipeform";
    }

    @PostMapping
    @RequestMapping("")
    public String saveOrUpdate(@ModelAttribute RecipeCommand recipeCommand) {
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(recipeCommand);

        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }

    @GetMapping
    @RequestMapping("/{id}/delete")
    public String deleteById(@PathVariable Long id) {
        log.debug("Deleting id: " + id);

        recipeService.deleteById(id);

        return "redirect:/index";
    }

}
