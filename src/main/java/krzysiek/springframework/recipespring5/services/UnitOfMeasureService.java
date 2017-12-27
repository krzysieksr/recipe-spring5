package krzysiek.springframework.recipespring5.services;

import krzysiek.springframework.recipespring5.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {

    Set<UnitOfMeasureCommand> listAllUOMs();
}
