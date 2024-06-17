package xPractica.Parcial2020_1C.Ej1;

import Trees.AVL.src.main.java.AVL;

public class FiboTree {
    private Node root;

    public Node getRoot() {
        return root;
    }

    public void printHierarchy() {
        printHierarchy("",  root);
    }

    public void printHierarchy(String initial, Node current) {
        if (current == null) {
            System.out.println(initial +  "\\___ " + "null");
            return;
        }

        System.out.println(initial + "\\___ " + current);

        if ( current.left != null || current.right != null) {
            printHierarchy(initial + "    " ,  current.left) ;
            printHierarchy(initial + "    " ,   current.right) ;

        }
    }

    public FiboTree(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Parameter cannot be null");
        }
        root = FiboTreeRec(root, n);
    }

    private Node FiboTreeRec(Node node, int n) {
        if (n == 0) {
            return null;
        }
        if (n == 1) {
            return new Node();
        }
        if (n > 1) {
            node = new Node();
            node.left = FiboTreeRec(node.left, n - 1);
            node.right = FiboTreeRec(node.right, n - 2);
        }
        return node;
    }

    private class Node implements NodeFiboInterface {
        private String data;
        private Node left;
        private Node right;

        @Override
        public String getData() {
            return data;
        }

        @Override
        public NodeFiboInterface getLeft() {
            return left;
        }

        @Override
        public NodeFiboInterface getRight() {
            return right;
        }

        @Override
        public String toString(){
            return String.format("*");
        }
    }

    public static void main(String[] args) {
        FiboTree myTree= new FiboTree(0);
        //myTree.printHierarchy();

        FiboTree myTree1= new FiboTree(1);
        //myTree1.printHierarchy();

        FiboTree myTree2= new FiboTree(2);
        //myTree2.printHierarchy();

        FiboTree myTree3= new FiboTree(5);
        myTree3.printHierarchy();
    }
}
