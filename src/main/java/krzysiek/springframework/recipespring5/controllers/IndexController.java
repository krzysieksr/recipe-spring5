package krzysiek.springframework.recipespring5.controllers;

import krzysiek.springframework.recipespring5.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/", "index"})
    public String getIndexPage(Model model) {
        model.addAttribute("recipes", recipeService.getRecipeList());

        return "index";
    }

//    private CategoryRepository categoryRepository;
//    private UnitOfMeasureRepository unitOfMeasureRepository;
//
//    @Autowired
//    public void setCategoryRepository(CategoryRepository categoryRepository) {
//        this.categoryRepository = categoryRepository;
//    }
//
//    @Autowired
//    public void setUnitOfMeasureRepository(UnitOfMeasureRepository unitOfMeasureRepository) {
//        this.unitOfMeasureRepository = unitOfMeasureRepository;
//    }


//    @RequestMapping({"", "/", "index"})
//    public String getIndexPage() {
//        Optional<Category> categoryOptional = categoryRepository.findByDescription("American");
//        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Cup");
//
//        System.out.println("'American' category Id is: " + categoryOptional.get().getId());
//        System.out.println("'Cup' unit of measure ID is: " + unitOfMeasureOptional.get().getId());
//
//
//        List<Category> categoryList = new ArrayList<>();
//        categoryRepository.findAll().forEach(categoryList::add);
//        categoryList.forEach(category -> System.out.println(category.getDescription()));
////        System.out.println("Say something...1234");
//        return "index";
//    }
}
