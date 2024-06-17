
package ar.itba.edu.BST_;

import java.util.Iterator;

public class Main {
    public static void main(String[] args) {

       /* BST<Integer> bst2 = new BST<>();
        bst2.insert(50);
        bst2.insert(60);
        bst2.insert(80);
        bst2.insert(20);
        bst2.insert(70);
        bst2.insert(40);
        bst2.insert(44);
        bst2.insert(10);
        bst2.insert(40);

        bst2.inOrder();
        System.out.println();
        bst2.preOrder();
        System.out.println();
        bst2.postOrder();*/

       BST<Integer> bst = new BST<>();

        // Insert elements
        bst.insert(50);
        bst.insert(30);
        bst.insert(20);
        bst.insert(40);
        bst.insert(70);
        bst.insert(60);
        bst.insert(80);

        System.out.println("InOrder traversal of the given tree");
        Iterator<Integer> inOrderIterator = bst.iterator();
        while (inOrderIterator.hasNext()) {
            System.out.print(inOrderIterator.next() + " ");
        }

        System.out.println("\n\nByLevels traversal of the given tree");
        Iterator<Integer> byLevelsIterator = bst.iterator();
        while (byLevelsIterator.hasNext()) {
            System.out.print(byLevelsIterator.next() + " ");
        }

        // Delete a node
        bst.delete(20);

        System.out.println("\n\nInOrder traversal after deleting 20");
        inOrderIterator = bst.iterator();
        while (inOrderIterator.hasNext()) {
            System.out.print(inOrderIterator.next() + " ");
        }
    }
}