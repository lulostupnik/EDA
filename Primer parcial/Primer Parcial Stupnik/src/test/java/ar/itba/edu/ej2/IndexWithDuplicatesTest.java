package ar.itba.edu.ej2;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class IndexWithDuplicatesTest  {
    private IndexWithDuplicates index1;
    private IndexWithDuplicates index2;
    @BeforeEach
    void setup() {
        index1 = new IndexWithDuplicates();
        index2 = new IndexWithDuplicates();
    }
    @Test
    void testCase1() {
        index1.initialize(new int[] {1, 3, 5, 7});
        index2.initialize(new int[] {2, 4, 6, 8});
        index1.merge(index2);

        int[] expected = new int[] {1,2,3,4,5,6,7,8};
        assertArrayEquals(expected, index1.getIndexedData());
    }

    @Test
    void testCase2() {
        index1.initialize(new int[] {1, 1, 3, 5, 7});
        index2.initialize(new int[] {2, 4, 4, 6, 8});
        index1.merge(index2);

        int[] expected = new int[] {1,1,2,3,4,4,5,6,7,8};
        assertArrayEquals(expected, index1.getIndexedData());
    }

    @Test
    void testCase3() {
        index1.initialize(new int[] {1, 3, 5});
        index2.initialize(new int[] {2, 4, 6, 8, 10});
        index1.merge(index2);

        int[] expected = new int[] {1,2,3,4,5,6,8,10};
        assertArrayEquals(expected, index1.getIndexedData());
    }

    @Test
    void testCase4() {
        index1.initialize(new int[] {1, 3, 5});
        index2.initialize(new int[] {2, 4, 6, 8, 10});

        IndexWithDuplicates index2_before_merge = new IndexWithDuplicates();
        index2_before_merge.initialize(new int[] {2, 4, 6, 8, 10});

        index1.merge(index2);

        assertArrayEquals(index2.getIndexedData(), index2_before_merge.getIndexedData());
    }


}