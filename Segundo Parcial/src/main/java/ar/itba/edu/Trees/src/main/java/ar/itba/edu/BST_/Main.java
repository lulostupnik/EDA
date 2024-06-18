
package ar.itba.edu.BST_;

import java.util.Iterator;

public class Main {
    public static void main(String[] args) {

     main4(args);
     //main3(args);

    }

    public static void main3(String[] args) {
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


        System.out.println("Post order: ");
        bst.postOrder();
        System.out.println("\nPre order: ");
        bst.preOrder();
        System.out.println("\nIn order: ");
        bst.inOrder();

        System.out.println("\n\nInOrder traversal of the given tree");
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

    //pag 41 ppt 19
    public static void main2(String[] args) {
        BST<Integer>  myTree= new BST<>();
        myTree.insert(35);   myTree.insert(74);
        myTree.insert(20);   myTree.insert(22);
        myTree.insert(55);   myTree.insert(15);
        myTree.insert(8);     myTree.insert(27);
        myTree.insert(25);

        System.out.println("\n\nDefault Traversal…\n");
        myTree.forEach( t-> System.out.print(t + " ") );

        myTree.setTraversal(BST.Traversal.INORDER);
        System.out.println("\n\nUna vez más INORDER\n");
        myTree.forEach( t-> System.out.print(t + " ") );

        myTree.setTraversal(BST.Traversal.BYLEVELS);
        System.out.println("\n\nUna vez más BYLEVELS\n");
        myTree.forEach( t-> System.out.print(t + " ") );

        myTree.setTraversal(BST.Traversal.INORDER);
        System.out.println("\n\nUna vez más INORDER\n");
        myTree.forEach( t-> System.out.print(t + " ") );

    }

    public static void main4(String[] args) {
        BST<Integer> bst = new BST<>();
        // Insert elements
        bst.insert(50);
        bst.insert(30);
        bst.insert(20);
        bst.insert(40);
        bst.insert(70);
        bst.insert(60);
        bst.insert(80);

        //bstToGithubTreePrint(bst);
        bst = deleteAndPrint(bst, 30);
    }


    public static <T extends Comparable<T>> void bstToGithubTreePrint(BST<T> tree) {
        BinarySearchTree<T> ans = new BinarySearchTree();
        for(T elem : tree.getPreorder()){
            ans.add(elem);
        }
        System.out.println(ans);
    }
    public static <T extends Comparable<T>> BST<T> deleteAndPrint(BST<T> bst, T elem) {
        System.out.println("----------------");
        bstToGithubTreePrint(bst);
        System.out.println("\nDeleting " + elem);
        bst.delete(elem);
        bstToGithubTreePrint(bst);
        System.out.println("------------------");
        return bst;
    }

}