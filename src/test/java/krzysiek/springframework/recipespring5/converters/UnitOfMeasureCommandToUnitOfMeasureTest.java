package krzysiek.springframework.recipespring5.converters;

import krzysiek.springframework.recipespring5.commands.UnitOfMeasureCommand;
import krzysiek.springframework.recipespring5.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitOfMeasureCommandToUnitOfMeasureTest {
    private final static Long ID_VALUE = 1L;
    private final static String DESCRIPTION = "description";

    UnitOfMeasureCommandToUnitOfMeasure converter;

    @Before
    public void setUp() throws Exception {
        converter = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new UnitOfMeasureCommand()));
    }

    @Test
    public void convert() {
        //given
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(ID_VALUE);
        unitOfMeasureCommand.setDescription(DESCRIPTION);

        //when
        UnitOfMeasure unitOfMeasure = converter.convert(unitOfMeasureCommand);

        //then
        assertNotNull(unitOfMeasure);
        assertEquals(ID_VALUE, unitOfMeasure.getId());
        assertEquals(DESCRIPTION, unitOfMeasure.getDescription());
    }
}