package Fibo;

import BST.NodeTree;

public class FiboTree {
    private Node root;
    public Node getRoot() {
        return root;
    }
    public FiboTree(int n) {
        this.root = buildFibo(n);
    }

    private Node buildFibo(int n){
        if(n == 0) return null;
        if(n == 1) return new Node("*");
        Node node = new Node("*");
        node.left = buildFibo(n-1);
        node.right = buildFibo(n-2);
        return node;
    }

    public void printByLevels() {
        printRec(0, root);
    }

    private void printRec(int level, NodeFiboInterface current) {
        if (current == null) {
            System.out.println(" ".repeat(level * 5) + "└── " + "null");
            return;
        }
        System.out.println(" ".repeat(level * 5) + "└── " + current.getData());
        if (current.getLeft() == null && current.getRight() == null) {
            return;
        }
        printRec(level + 1, current.getLeft());
        printRec(level + 1, current.getRight());
    }


    private class Node implements NodeFiboInterface{
        private String data;
        private Node left;
        private Node right;

        public Node(String data) {
            this.data = data;
        }

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
    }

    public static void main(String[] args) {
        FiboTree myTree= new FiboTree(4);
        myTree.printByLevels();
    }

}
