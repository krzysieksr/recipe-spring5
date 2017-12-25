package krzysiek.springframework.recipespring5.converters;

import krzysiek.springframework.recipespring5.commands.CategoryCommand;
import krzysiek.springframework.recipespring5.domain.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryCommandToCategoryTest {

    public static final Long ID = 1L;
    public static final String DESCRIPTION = "description";

    CategoryCommandToCategory converter;

    @Before
    public void setUp() throws Exception {
        converter = new CategoryCommandToCategory();
    }

    @Test
    public void convert() {
        //given
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(ID);
        categoryCommand.setDescription(DESCRIPTION);

        //when
        Category category = converter.convert(categoryCommand);

        //then
        assertEquals(ID, category.getId());
        assertEquals(DESCRIPTION, category.getDescription());
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new CategoryCommand()));
    }
}