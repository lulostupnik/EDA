package Trees.BSTree.src.main.java.core;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;


public class BST<T extends Comparable<? super T>> implements BSTreeInterface<T> {
    protected Node<T> root;
    private Traversal aTraversal = Traversal.INORDER;

    public void printHierarchy() {
        printHierarchy("", root);
    }

    public void printHierarchy(String initial, Node<T> current) {
        if (current == null) {
            System.out.println(initial + "└── " + "null");
            return;
        }
        System.out.println(initial + "└── " + current.data);
        if (!current.isLeaf()) {
            printHierarchy(initial + "    ", current.left);
            printHierarchy(initial + "    ", current.right);
        }
    }

    @Override
    public NodeTreeInterface<T> getRoot() {
        return root;
    }


    @Override
    public void insert(T data) {
        if (data == null)
            throw new RuntimeException("element cannot be null");
        if (root == null) {
            root = new Node<>(data);
        } else {
            root.insert(data);
        }
    }

    @Override
    public void preOrder() {
        System.out.println(root.preorder());
    }

    @Override
    public void postOrder() {
        System.out.println(root.postorder());
    }

    @Override
    public void inOrder() {
        System.out.println(root.inorder());
    }


    public int getHeight() {
        if (root == null) {
            return -1;
        }

        return getHeight(root, 0);
    }

    private int getHeight(Node<T> node, int height) {
        if (node.right == null && node.left == null)
            return height;

        int heightLeft = 0;
        int heightRight = 0;

        if (node.left != null) heightRight = getHeight(node.left, height + 1);
        if (node.right != null) heightLeft = getHeight(node.right, height + 1);

        return Integer.max(heightRight, heightLeft);
    }

    @Override
    public boolean contains(T data) {
        return contains(data, root);
    }

    private boolean contains(T data, NodeTreeInterface<T> node) {
        if (node == null)
            return false;
        if (data.compareTo(node.getData()) == 0)
            return true;
        if (data.compareTo(node.getData()) < 0) {
            return contains(data, node.getLeft());
        } else {
            return contains(data, node.getRight());
        }
    }

    @Override
    public T getMin() {
        if (root == null) return null;
        NodeTreeInterface<T> current = root;
        while (current.getLeft() != null) {
            current = current.getLeft();
        }
        return current.getData();
    }

    @Override
    public T getMax() {
        if (root == null) return null;
        NodeTreeInterface<T> current = root;
        while (current.getRight() != null) {
            current = current.getRight();
        }
        return current.getData();
    }


    @Override
    public void printByLevels() {
        if (root == null) return;

        Queue<Node<T>> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node<T> current = queue.remove();
            System.out.print(current.data + " ");
            if (current.left != null) queue.add(current.left);
            if (current.right != null) queue.add(current.right);
        }
    }

    @Override
    public void delete(T myData) {
        if (myData == null) throw new RuntimeException("elemnet cannot be null");
        if (root != null)
            root = root.delete(myData);
    }

    @Override
    public void setTraversal(Traversal traversal) {
        this.aTraversal = traversal;
    }

    @Override
    public Iterator<T> iterator() {
        switch (aTraversal) {
            case BYLEVELS:
                return new BSTByLevelIterator();
            case INORDER:
                return new BSTInOrderIterator();
        }
        return new BSTInOrderIterator();
    }

    public void inOrderIter() {
        Stack<NodeTreeInterface<T>> stack = new Stack<>();
        NodeTreeInterface<T> current = root;
        while (!stack.isEmpty() || current != null) {
            if (current != null) {
                stack.push(current);
                current = current.getLeft();
            } else {
                NodeTreeInterface<T> elementToProcess = stack.pop();
                System.out.print(elementToProcess.getData() + "\t");
                current = elementToProcess.getRight();
            }
        }
    }

    @Override
    public int getOcurrences(T element) {
        if (root == null)
            return 0;
        return root.getOcurrences(element);
    }

    @Override
    public T kesimo(int k) {
        if (root == null || k < 1)
            return null;
        int count = 1;
        BSTInOrderIterator iterator = new BSTInOrderIterator();
        while (iterator.hasNext()) {
            if (count == k)
                return iterator.next();
            count++;
            iterator.next();
        }
        return null;
    }

    @Override
    public T getCommonNode(T element1, T element2) {
        if (root == null || element1 == element2) {
            return null;
        }
        return root.getCommonNode(element1, element2);
    }

    @Override
    public T getCommonNodeWithRepeated(T element1, T element2) {
        if (root == null || element1 == element2) {
            return null;
        }
        return root.getCommonNodeWithRepeated(element1, element2);
    }

    static class Node<T extends Comparable<? super T>> implements NodeTreeInterface<T> {
        private T data;
        private Node<T> left;
        private Node<T> right;

        public Node() {
            // TODO Auto-generated constructor stub
        }

        public Node(T data) {
            this.data = data;
        }

        @Override
        public T getData() {
            return data;
        }

        public void insert(T data) {
            if (data.compareTo(this.data) <= 0) {
                if (left == null)
                    left = new Node<>(data);
                else
                    left.insert(data);
            } else {
                if (right == null)
                    right = new Node<>(data);
                else
                    right.insert(data);
            }
        }

        @Override
        public NodeTreeInterface<T> getLeft() {
            return left;
        }

        @Override
        public NodeTreeInterface<T> getRight() {
            return right;
        }

        private boolean isLeaf() {
            return left == null && right == null;
        }

        public String preorder() {
            String toRet = data + " ";
            if (left != null)
                toRet += left.preorder();
            if (right != null)
                toRet += right.preorder();
            return toRet;
        }

        public String postorder() {
            String toRet = "";
            if (left != null)
                toRet += left.postorder();
            if (right != null)
                toRet += right.postorder();
            toRet += data + " ";
            return toRet;
        }

        public String inorder() {
            String toRet = "";
            if (left != null) {
                toRet += left.inorder();
            }
            toRet += data + " ";
            if (right != null) {
                toRet += right.inorder();
            }
            return toRet;
        }

        public Node delete(T myData) {
            if (myData.compareTo(this.data) < 0) {
                if (left != null) {
                    left = left.delete(myData);
                }
                return this;
            }
            if (myData.compareTo(this.data) > 0) {
                if (right != null) {
                    right = right.delete(myData);
                }
                return this;
            }
            if (left == null)
                return right;
            if (left == null)
                return right;
            T replacement = lexiAdjacent(left);
            this.data = replacement;
            left = left.delete(this.data);
            return this;
        }

        private T lexiAdjacent(Node candidate) {
            Node<T> auxi = candidate;
            while (auxi.right != null) {
                auxi = auxi.right;
            }
            return auxi.data;
        }

        int getOcurrences(T element) {
            if (data.compareTo(element) < 0)
                return right.getOcurrences(element);
            if (data.compareTo(element) == 0)
                return 1 + left.getOcurrences(element);
            return left.getOcurrences(element);
        }

        private T getCommonNode(T element1, T element2) {
            if (data.compareTo(element1) > 0 && data.compareTo(element2) > 0) {
                return left.getCommonNode(element1, element2);
            } else if (data.compareTo(element1) < 0 && data.compareTo(element2) < 0) {
                return right.getCommonNode(element1, element2);
            } else {
                if (element1.compareTo(element2) != 0 && hasChildren(element1) && hasChildren(element2))
                    return data;
                return null;
            }
        }

        private T getCommonNodeWithRepeated(T element1, T element2) {
            if (data.compareTo(element1) > 0 && data.compareTo(element2) > 0) {
                return left.getCommonNodeWithRepeated(element1, element2);
            } else if (data.compareTo(element1) < 0 && data.compareTo(element2) < 0) {
                return right.getCommonNodeWithRepeated(element1, element2);
            } else {
                if (element1.compareTo(element2) != 0 && hasChildren(element1) && hasChildren(element2))
                    return data;
                if (element1.compareTo(element2) == 0 && getOcurrences(element1) >= 2)
                    return data;
                return null;
            }
        }

        private boolean hasChildren(T myData) {
            if (data.compareTo(myData) == 0)
                return true;
            if (data.compareTo(myData) > 0 && left != null)
                return left.hasChildren(myData);
            if (data.compareTo(myData) < 0 && right != null)
                return right.hasChildren(myData);
            return false;
        }

    }  // end Node class

    class BSTByLevelIterator implements Iterator<T> {

        private Queue<NodeTreeInterface<T>> queue;

        private BSTByLevelIterator() {
            queue = new LinkedList<>();
            if (root != null)
                queue.add(root);
        }

        @Override
        public boolean hasNext() {
            return !queue.isEmpty();
        }

        @Override
        public T next() {
            NodeTreeInterface<T> currentNode = queue.remove();

            if (currentNode.getLeft() != null) queue.add(currentNode.getLeft());
            if (currentNode.getRight() != null) queue.add(currentNode.getRight());

            return currentNode.getData();
        }
    }

    class BSTInOrderIterator implements Iterator<T> {

        Stack<NodeTreeInterface<T>> stack;

        NodeTreeInterface<T> current;

        public BSTInOrderIterator() {
            stack = new Stack<>();
            current = root;
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty() || current != null;
        }

        @Override
        public T next() {
            while (current != null) {
                stack.push(current);
                current = current.getLeft();
            }
            NodeTreeInterface<T> elementToProcess = stack.pop();
            current = elementToProcess.getRight();
            return elementToProcess.getData();
        }
    }

    public static void main(String[] args) {
        BST<Integer> myTree = new BST<>();
        myTree.insert(35);
        myTree.insert(74);
        myTree.insert(20);
        myTree.insert(22);
        myTree.insert(55);
        myTree.insert(15);
        myTree.insert(8);
        myTree.insert(27);
        myTree.insert(25);
        myTree.printHierarchy();
        myTree.inOrder();
        myTree.preOrder();
        myTree.postOrder();

        //for (Object data : myTree){
        //    System.out.print(data + " ");
        //}

        //myTree.forEach(t-> System.out.print(t + " "));

//        myTree.getOcurrences(40);
//        myTree.getOcurrences(70);
//        int k1 = myTree.Kesimo(1);
//        System.out.println(String.format("%d", k1 ));
//        int k2 = myTree.Kesimo(3);
//        System.out.println(String.format("%d", k2 ));
//        int k3 = myTree.Kesimo(8);
//        System.out.println(String.format("%d", k3 ));
//        int k4 = myTree.Kesimo(0);
//        System.out.println(String.format("%d", k4 ));
//        int k5 = myTree.Kesimo(10);
//        System.out.println(String.format("%d", k5 ));
//
//        myTree = new BST<>();
//        myTree.insert(5);
//        myTree.insert(70);
//        myTree.insert(30);
//        myTree.insert(35);
//        myTree.insert(20);
//        myTree.insert(40);
//        myTree.insert(80);
//        myTree.insert(90);
//        myTree.insert(85);
//        int n1= 35;
//        int n2= 35;
//        Integer rta = myTree.getCommonNode (n1, n2);
//        System.out.println(String.format("common entre %d y %d es %d", n1, n2, rta ) );
//        n1= 40;
//        n2= 80;
//        rta = myTree.getCommonNode(n1, n2);
//        System.out.println(String.format("common entre %d y %d es %d", n1, n2, rta ) );
//
//        myTree = new BST<>();
//        myTree.insert(5);
//        myTree.insert(70);
//        myTree.insert(30);
//        myTree.insert(70);
//        myTree.insert(20);
//        myTree.insert(40);
//        myTree.insert(80);
//        myTree.insert(90);
//        myTree.insert(85);
        int n1 = 15;
        int n2 = 74;
        Integer rta2 = myTree.getCommonNodeWithRepeated(n1, n2);
        System.out.println(String.format("common entre %d y %d es %d", n1, n2, rta2));
    }
}
