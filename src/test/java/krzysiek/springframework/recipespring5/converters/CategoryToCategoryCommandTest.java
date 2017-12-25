package krzysiek.springframework.recipespring5.converters;

import krzysiek.springframework.recipespring5.commands.CategoryCommand;
import krzysiek.springframework.recipespring5.domain.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryToCategoryCommandTest {

    public static final Long ID = 1L;
    public static final String DESCRIPTION = "description";

    CategoryToCategoryCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new CategoryToCategoryCommand();
    }

    @Test
    public void convert() {
        //given
        Category category = new Category();
        category.setId(ID);
        category.setDescription(DESCRIPTION);

        //when
        CategoryCommand categoryCommand = converter.convert(category);

        //then
        assertEquals(ID, categoryCommand.getId());
        assertEquals(DESCRIPTION, categoryCommand.getDescription());
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new Category()));
    }
}