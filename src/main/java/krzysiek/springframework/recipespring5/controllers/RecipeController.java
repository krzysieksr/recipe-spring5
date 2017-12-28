package krzysiek.springframework.recipespring5.controllers;

import krzysiek.springframework.recipespring5.commands.RecipeCommand;
import krzysiek.springframework.recipespring5.domain.Difficulty;
import krzysiek.springframework.recipespring5.exception.NotFoundException;
import krzysiek.springframework.recipespring5.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RequestMapping("/recipe")
@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    //    @GetMapping
    @GetMapping("/{id}/show")
    public String showById(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findById(new Long(id)));
        return "recipe/show";
    }

    //    @GetMapping
    @GetMapping("/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());
//        model.addAttribute("difficult", Difficulty.values());

        return "recipe/recipeform";
    }

    //    @GetMapping
    @GetMapping("/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(new Long(id)));

        return "recipe/recipeform";
    }

    //    @PostMapping
    @PostMapping("")
    public String saveOrUpdate(@ModelAttribute RecipeCommand recipeCommand) {
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(recipeCommand);

        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }

    //    @GetMapping
    @GetMapping("/{id}/delete")
    public String deleteById(@PathVariable String id) {
        log.debug("Deleting id: " + id);

        recipeService.deleteById(new Long(id));

        return "redirect:/index";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception) {

        log.error("Handling not found exception");
        log.error(exception.getMessage());

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("404error");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleNumberFormatException(Exception exception) {

        log.error("Handling number format exception");
        log.error(exception.getMessage());

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("400error");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }

}
