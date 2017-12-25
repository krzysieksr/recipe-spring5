package krzysiek.springframework.recipespring5.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CategoryCommand {
    private Long id;
    private String description;
}
