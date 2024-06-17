package BST;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import java.util.NoSuchElementException;



public class BSTTest {

    private BST<Integer> bst;


    @BeforeEach
    public void setUp() {
        bst = new BST<Integer>();
    }

    @Test
    public void testInsert() {
        bst.insert(10);
        bst.insert(20);
        bst.insert(3);
        bst.insert(14);
        bst.insert(10);
    }//se ve a ojo que funca
    @Test
    public void testInsertNode() {
        bst.insertNode(10);
        bst.insertNode(20);
        bst.insertNode(3);
        bst.insertNode(14);
        bst.insertNode(10);
    }//
    @Test
    public void testHeight() {
        assertEquals(-1, bst.getHeight());
        bst.insertNode(10);
        assertEquals(0, bst.getHeight());
        bst.insertNode(20);
        bst.insertNode(3);
        bst.insertNode(14);
        bst.insertNode(10);
        assertEquals(3, bst.getHeight());
    }//
    @Test
    public void testContains() {
        bst.insertNode(10);
        assertEquals(0, bst.getHeight());
        bst.insertNode(20);
        bst.insertNode(3);
        bst.insertNode(14);
        bst.insertNode(10);
        assertEquals(3, bst.getHeight());
    }//

    @Test
    public void testGetMax() {
        assertThrows(NoSuchElementException.class, () -> {
            // Código que debe lanzar la excepción
            bst.getMax();
        });

        bst.insertNode(10);
        bst.insertNode(20);
        assertEquals(20, bst.getMax());
        bst.insertNode(3);
        bst.insertNode(14);
        bst.insertNode(50);
        assertEquals(50, bst.getMax());
    }//

    @Test
    public void testDelete() {
        bst.delete(5);
        bst.insertNode(10);
        bst.insertNode(20);
        bst.insertNode(3);
        bst.insertNode(14);
        bst.insertNode(50);
        bst.preOrder();                     // 10 3 20 14 50
        bst.delete(10);
        bst.preOrder();                     //

    }//

    @Test
    public void testIterator() {

        int[] vector = new int[]{5,1,4,2,8,6,12};
        for(int i = 0; i < vector.length; i++){
            bst.insertNode(vector[i]);
        }
        bst.setTraversal(BSTreeInterface.Traversal.INORDER);
        Iterator<Integer> iterator = bst.iterator();

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        System.out.println("---------------------------------------------------");
        bst.inOrder();

    }//

    @Test
    public void testGetOccurrences() {

        int[] vector = new int[]{5,1,4,2,8,6,2,12,2};
        for(int i = 0; i < vector.length; i++){
            bst.insertNode(vector[i]);
        }

        assertEquals(3,bst.getOcurrences(2));
        assertEquals(0,bst.getOcurrences(55));
        assertEquals(1,bst.getOcurrences(5));


    }//


}
