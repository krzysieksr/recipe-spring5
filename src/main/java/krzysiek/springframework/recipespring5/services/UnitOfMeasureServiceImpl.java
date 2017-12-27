package krzysiek.springframework.recipespring5.services;

import krzysiek.springframework.recipespring5.commands.UnitOfMeasureCommand;
import krzysiek.springframework.recipespring5.converters.UnitOfMeasureToUnitOfMeasureCommand;
import krzysiek.springframework.recipespring5.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;


    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository, UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    @Override
    public Set<UnitOfMeasureCommand> listAllUOMs() {
        Set<UnitOfMeasureCommand> unitOfMeasureCommands = new HashSet<>();

//        unitOfMeasureRepository.findAll().iterator()
//                .forEachRemaining(unitOfMeasure -> unitOfMeasureCommands.add(unitOfMeasureToUnitOfMeasureCommand.convert(unitOfMeasure)));

        unitOfMeasureCommands = StreamSupport.stream(unitOfMeasureRepository.findAll()
                .spliterator(), false)
                .map(unitOfMeasureToUnitOfMeasureCommand::convert)
                .collect(Collectors.toSet());


        return unitOfMeasureCommands;
    }
}
