package ar.itba.edu.BST_;

import java.util.Iterator;

/*
* El borrado usa el sucesor inorder, es decir, el nodo más a la izquierda del subárbol derecho.
 */


public class MainGithub {
    public static void main(String[] args) {


        BinarySearchTree<Integer> bst = new BinarySearchTree<>();

        // add elements
        
        
        bst.add(50);
      //  bst.add(30);
        bst.add(20);
        bst.add(40);
        bst.add(70);
        bst.add(60);
        bst.add(80);



        System.out.println("InOrder of the given tree");

        for(Integer num : bst.getDFS(BinarySearchTree.DepthFirstSearchOrder.inOrder)){
            System.out.print(num + " ");
        }
        System.out.println("\n------------------");


        System.out.println("preOrder of the given tree");

        for(Integer num : bst.getDFS(BinarySearchTree.DepthFirstSearchOrder.preOrder)){
            System.out.print(num + " ");
        }
        System.out.println("\n------------------");


        System.out.println("postOrder of the given tree");

        for(Integer num : bst.getDFS(BinarySearchTree.DepthFirstSearchOrder.postOrder)){
            System.out.print(num + " ");
        }
        System.out.println("\n------------------");


        Integer toDelete = 30;
        System.out.println(bst);
        System.out.println("Deleting " + toDelete);

        // Delete a node
        bst.remove(toDelete);

        System.out.println("-----------");
        System.out.println(bst);
        System.out.println("");


    }
}
