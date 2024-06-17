package BinaryTree;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.function.Function;

public class ParametrizedBinaryTree <T extends Comparable<? super T>> implements ParametrizedBinaryTreeService<T> {
    
    private Node<T> root;
    private Scanner inputScanner;
    int size = 0;

    public ParametrizedBinaryTree(String fileName) throws IllegalArgumentException, SecurityException, FileNotFoundException {
        InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);

        if (is == null)
            throw new FileNotFoundException(fileName);

        inputScanner = new Scanner(is);
        inputScanner.useDelimiter("\\s+");
        buildTree();
        inputScanner.close();

    }

    public void printHierarchy() {
        printRec(0, root);
    }

    public void buildTree() throws IllegalArgumentException, SecurityException {
        Queue<NodeHelper<T>> pendingOps = new LinkedList<>();
        T token;
        root = new Node<T>();
        pendingOps.add(new NodeHelper<T>(root, n -> n));

        while(inputScanner.hasNext()) {
            token = (T) inputScanner.next();
            NodeHelper<T> aPendingOp = pendingOps.remove();
            Node<T> currentNode = aPendingOp.getNode();

            if (token.equals("?")) {
                // no hace falta poner en null al L o R porque ya esta con null
                // reservar el espacio en Queue aunque NULL no tiene hijos para aparear
                pendingOps.add( new NodeHelper(null, null) );  // como si hubiera izq
                pendingOps.add( new NodeHelper(null, null) );  // como si hubiera der
            }
            else {
                Function<Node<T>, Node<T>> anAction = aPendingOp.getAction();
                currentNode = anAction.apply(currentNode);
                // armo la info del izq, der o el root
                currentNode.data = token;

                // hijos se postergan
                pendingOps.add(new NodeHelper<T>(currentNode, n -> n.setLeftTree(new Node<T>())));
                pendingOps.add(new NodeHelper<T>(currentNode, n -> n.setRightTree(new Node<T>())));
            }
            size++;
        }


        if (root.data == null)  // no entre al ciclo jamas
            root = null;
    }

    private void printRec(int level, Node<T> current) {
        if(current == null) {
            System.out.println(" ".repeat(level * 5) + "└── " + "null");
            return;
        }
        System.out.println(" ".repeat(level * 5) + "└── " + current.data);
        if(current.isLeaf()){
            return;
        }
        printRec(level + 1, current.left);
        printRec(level + 1, current.right);
    }

    public void toFile(String name) throws IOException {
        String path = "/home/conradohillar/Documents/ITBA/EDA/Unidad5_B/src/main/resources/" + name;
        PrintWriter writer = new PrintWriter(path, StandardCharsets.UTF_8);
        writer.print(getString());
        writer.close();
    }


    private String getString() {
        Queue<Node<T>> queue = new LinkedList<>();
        queue.add(root);
        StringBuilder sb = new StringBuilder();
        int count = size;

        while(count != 0) {
            Node<T> current = queue.remove();
            if(current == null) {
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

    @Override
    public boolean equals(ParametrizedBinaryTree<T> other) {
        if(other.size != size) return false;
        Queue<Node<T>> queue1 = new LinkedList<>();
        Queue<Node<T>> queue2 = new LinkedList<>();
        queue1.add(root);
        queue2.add(other.root);
        int count = size;

        while(count != 0) {
            Node<T> currentThis = queue1.remove();
            Node<T> currentOther = queue2.remove();
            if(currentThis == null || currentOther == null) {
                if(currentThis == null) {
                    queue1.add(null);
                    queue1.add(null);
                }
                if(currentOther == null) {
                    queue2.add(null);
                    queue2.add(null);
                }
            } else {
                if(!currentThis.data.equals(currentOther.data)) {
                    return false;
                }
                queue1.add(currentThis.left);
                queue1.add(currentThis.right);
                queue2.add(currentOther.left);
                queue2.add(currentOther.right);
            }
            count--;
        }
        return true;
    }

    public int getHeightIter() {
        Queue<Node<T>> queue = new LinkedList<>();
        queue.add(root);
        int height = -1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            height++;
            while (size > 0) {
                Node<T> treeNode = queue.remove();
                if (treeNode.left != null)
                    queue.add(treeNode.left);
                if (treeNode.right != null)
                    queue.add(treeNode.right);
                size--;
            }
        }
        return height;
    }

    public int getHeightRec() {
        int height = -1;
        int aux = getHeightSubtreeRec(root);
        return height + aux;
    }

    private int getHeightSubtreeRec(Node<T> current) {
        if(current == null) {
            return 0;
        }
        int leftHeight = getHeightSubtreeRec(current.left);
        int rightHeight = getHeightSubtreeRec(current.right);
        return 1 + Math.max(leftHeight, rightHeight);
    }

    @Override
    public void preorder() {
        // COMPLETE
    }

    @Override
    public void postorder() {
        // COMPLETE
    }





    // hasta el get() no se evalua
    class Node<T> {
        private T data;
        private Node<T> left;
        private Node<T> right;

        public Node<T> setLeftTree(Node<T> aNode) {
            left = aNode;
            return left;
        }

        public Node<T> setRightTree(Node<T> aNode) {
            right = aNode;
            return right;
        }

        private boolean isLeaf() {
            return left == null && right == null;
        }


    }  // end Node class

    class NodeHelper<T> {
        private Node<T> aNode;
        private Function<Node<T>, Node<T>> anAction;

        public NodeHelper(Node<T> aNode, Function<Node<T>, Node<T>> anAction ) {
            this.aNode = aNode;
            this.anAction= anAction;
        }


        public Node<T> getNode() {
            return aNode;
        }

        public Function<Node<T>, Node<T>> getAction() {
            return anAction;
        }

    }

    public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        ParametrizedBinaryTree<Double> original = new ParametrizedBinaryTree<Double>("data0_3");
        System.out.println(original.getHeightIter());

    }
}
