

import ar.edu.itba.eda.Index.GenericIndexWithDuplicates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;



public class GenericIndexWithDuplicatesTest {

    private GenericIndexWithDuplicates<Integer> indexSearch;

    @BeforeEach
    public void setUp() {
        indexSearch = new GenericIndexWithDuplicates<>(Integer.class);
    }

    @Test
    public void testInsertAndSearch() {
        indexSearch.insert(5);
        indexSearch.insert(3);
        indexSearch.insert(7);

        assertTrue(indexSearch.search(5));
        assertTrue(indexSearch.search(3));
        assertTrue(indexSearch.search(7));
        assertFalse(indexSearch.search(4));
    }

    @Test
    public void testDelete() {
        indexSearch.insert(5);
        indexSearch.insert(3);
        indexSearch.insert(7);
        indexSearch.insert(5);

        assertEquals(2, indexSearch.occurrences(5));

    }

    @Test
    public void testMinMax() {
        indexSearch.insert(5);
        indexSearch.insert(3);
        indexSearch.insert(7);

        assertEquals(Integer.valueOf(3), indexSearch.getMin());
        assertEquals(Integer.valueOf(7), indexSearch.getMax());
    }

    @Test
    public void testRange() {
        indexSearch.insert(1);
        indexSearch.insert(2);
        indexSearch.insert(3);
        indexSearch.insert(4);
        indexSearch.insert(5);



        Integer[] range = indexSearch.range(2, 4, true, true);

        assertEquals(3, range.length);
        assertEquals(Integer.valueOf(2), range[0]);
        assertEquals(Integer.valueOf(3), range[1]);
        assertEquals(Integer.valueOf(4), range[2]);
    }

    @Test
    public void testInitialize() {
        Integer[] elements = {5, 3, 7};
        indexSearch.initialize(elements);
        assertEquals(indexSearch.occurrences(6),0);
        assertTrue(indexSearch.search(5));
        assertTrue(indexSearch.search(3));
        assertTrue(indexSearch.search(7));
    }

}
