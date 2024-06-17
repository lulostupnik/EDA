package Trees.Binary_Tree.src.main.java;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


public class BinaryTree implements BinaryTreeService {

    private Node root;

    private final Scanner inputScanner;

    private int tokenCount;

    public BinaryTree(String fileName) throws IllegalArgumentException, SecurityException, FileNotFoundException {
        InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);

        if (is == null)
            throw new FileNotFoundException(fileName);

        inputScanner = new Scanner(is);
        inputScanner.useDelimiter("\\s+");

        buildTree();

        inputScanner.close();
    }


    private void buildTree() throws IllegalArgumentException, SecurityException {

        Queue<NodeHelper> pendingOps = new LinkedList<>();
        String token;

        root = new Node();
        pendingOps.add(new NodeHelper(root, NodeHelper.Action.CONSUMIR));

        while (inputScanner.hasNext()) {

            token = inputScanner.next();

            NodeHelper aPendingOp = pendingOps.remove();
            Node currentNode = aPendingOp.getNode();

            if (token.equals("?")) {
                // no hace falta poner en null al L o R porque ya esta con null

                // reservar el espacio en Queue aunque NULL no tiene hijos para aparear
                pendingOps.add(new NodeHelper(null, NodeHelper.Action.CONSUMIR));  // como si hubiera izq
                pendingOps.add(new NodeHelper(null, NodeHelper.Action.CONSUMIR));  // como si hubiera der
            } else {
                switch (aPendingOp.getAction()) {
                    case LEFT -> currentNode = currentNode.setLeftTree(new Node());
                    case RIGHT -> currentNode = currentNode.setRightTree(new Node());
                }


                // armo la info del izq, der o el root
                currentNode.data = token;


                // hijos se postergan
                pendingOps.add(new NodeHelper(currentNode, NodeHelper.Action.LEFT));
                pendingOps.add(new NodeHelper(currentNode, NodeHelper.Action.RIGHT));
            }
            tokenCount++;

        }

        if (root.data == null)  // no entre al ciclo jamás
            root = null;

    }


    @Override
    public void preorder() {
        if (root != null) {
            root.preorder();
            System.out.println();
        }
    }


    @Override
    public void postorder() {
        if (root != null) {
            root.postorder();
            System.out.println();
        }
    }

    @Override
    public void printHierarchy() {
        if (root != null) {
            root.printHierarchy("");
            System.out.println();
        }
    }

    @Override
    public int getHeight() {
        if (root == null)
            return -1;
        return getHeightRec(root, 0);
    }

    private int getHeightIter() {
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        int height = -1;

        while (!queue.isEmpty()) {
            int size = queue.size();
            height++;

            for (int i = 0; i < size; i++) {
                Node node = queue.remove();

                if (node.left != null)
                    queue.add(node.left);
                if (node.right != null)
                    queue.add(node.right);
            }
        }

        return height;
    }

    private int getHeightRec(Node node, int height) {
        if (node.isLeaf())
            return height;

        int heightLeft = 0;
        int heightRight = 0;

        if (node.left != null)
            heightLeft = getHeightRec(node.left, height + 1);
        if (node.right != null)
            heightRight = getHeightRec(node.right, height + 1);

        return Math.max(heightLeft, heightRight);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof BinaryTree that))
            return false;
        return getString().equals(that.getString());
    }

    @Override
    public void toFile(String name) throws IOException {
        String path = "C:\\Users\\matia\\OneDrive - ITBA\\ITBA laptop\\2023_1C\\EDA\\Segunda Parte\\Arboles\\src\\main\\resources\\" + name;
        PrintWriter writer = new PrintWriter(path, StandardCharsets.UTF_8);
        writer.print(getString());
        writer.close();
    }

    private String getString() {
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        StringBuilder sb = new StringBuilder();

        int count = tokenCount;

        while (count != 0) {
            Node current = queue.remove();
            if (current == null) {
                sb.append("?\t");
                queue.add(null);
                queue.add(null);
            } else {
                sb.append(current.data).append("\t");
                queue.add(current.left);
                queue.add(current.right);
            }
            count--;
        }
        return sb.toString();
    }

    public void removeLeaves() {
        if (root != null)
            root.removeLeaves();
    }

    // hasta el get() no se evalua
    static class Node {
        private String data;
        private Node left;
        private Node right;

        public Node setLeftTree(Node aNode) {
            left = aNode;
            return left;
        }


        public Node setRightTree(Node aNode) {
            right = aNode;
            return right;
        }


        public Node() {
        }

        private boolean isLeaf() {
            return left == null && right == null;
        }

        public void preorder() {
            System.out.print(data + " ");

            if (left != null)
                left.preorder();

            if (right != null)
                right.preorder();
        }

        public void postorder() {
            if (left != null)
                left.postorder();

            if (right != null)
                right.postorder();

            System.out.print(data + " ");
        }

        public void printHierarchy(String format) {
            System.out.println(format + "└── " + data);

            if (isLeaf())
                return;

            format += '\t';

            if (left != null)
                left.printHierarchy(format);
            else
                System.out.println(format + "└── null");

            if (right != null)
                right.printHierarchy(format);
            else
                System.out.println(format + "└── null");
        }

        public Node removeLeaves() {
            if (this == null || this.isLeaf())
                return null;
            if (left != null)
                left = left.removeLeaves();
            if (right != null)
                right = right.removeLeaves();
            return this;
        }

    }  // end Node class


    static class NodeHelper {

        enum Action {LEFT, RIGHT, CONSUMIR}


        private final Node aNode;
        private final Action anAction;

        public NodeHelper(Node aNode, Action anAction) {
            this.aNode = aNode;
            this.anAction = anAction;
        }


        public Node getNode() {
            return aNode;
        }

        public Action getAction() {
            return anAction;
        }

    }


    public static void main(String[] args) throws IOException, IllegalArgumentException, SecurityException {

        BinaryTree original = new BinaryTree("data0_3");
        original.toFile("mydata0_3");

        original.printHierarchy();
        original.removeLeaves();
        original.printHierarchy();
        //System.out.println(original.getHeight());

        /*
        BinaryTree copia = new BinaryTree("mydata0_3");
        System.out.println(original.equals(copia)); // true
        System.out.println(copia.equals(original)); // true

        BinaryTree otro = new BinaryTree("data0_1");
        System.out.println(original.equals(otro)); // false
        System.out.println(otro.equals(original)); // false
         */
    }

}  