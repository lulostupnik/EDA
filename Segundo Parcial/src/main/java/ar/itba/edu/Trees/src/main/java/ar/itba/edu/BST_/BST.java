package ar.itba.edu.BST_;// QUEDÉ EN EL SLIDE 18 DE LA PPT 19B

import java.util.*;

/*
*   El borrado es con el predecesor lexicografico, es decir, el mas grande se su subarbol izquierdo
 */


public class BST<T extends Comparable<? super T>> implements BSTreeInterface<T> {

    Node<T> root;

    private Traversal traversal = Traversal.BYLEVELS;

    public BST() {
        root = null;
    }


    @Override
    public void setTraversal(Traversal traversal) {
        this.traversal = traversal;
    }

    @Override
    public void insert(T myData){

        if(root == null){
            root = new Node<T>(myData);
            return;
        }
        Node<T> currentNode = root;
        Node<T> parentNode = null;

        while(currentNode != null){
            parentNode = currentNode;
            if(myData.compareTo(currentNode.data) <= 0)
                currentNode = currentNode.left;
            else
                currentNode = currentNode.right;
        }

        if(myData.compareTo(parentNode.data) <= 0)
            parentNode.left = new Node<T>(myData);
        else
            parentNode.right = new Node<T>(myData);

    }

    /**
     * Insert a new node in the tree
     * Recursive version
     */
    public void insertRec(T myData) {
        if (root == null) {
            root = new Node<T>(myData);
            return;
        }
        root.insert(myData);
    }

    /**
     * Delete a node from the tree
     * Recursive version
     * copied from: PPT 19B slide 12
     */
    @Override
    public void delete(T myData) {
        if(myData == null)
            throw new RuntimeException("Element cannot be null");

        if(root != null)
            root = root.delete(myData);

    }


    /**
     * Print the tree in pre-order
     * Recursive version
     */
    @Override
    public void preOrder() {
        preOrder(root); // I could instead implement this method in the Node class and write root.preOrder()
    }

    private void preOrder(Node<T> root) { // I could delegate this method to the Node class
        if (root != null) {
            System.out.print(root.data + " ");
            preOrder(root.left);
            preOrder(root.right);
        }
    }

/////////////////////////////////////////////////////////////////
    private void getPreorder(Node<T> root, List<T> list){
        if (root != null) {
            list.add(root.data);
            getPreorder(root.left, list);
            getPreorder(root.right, list);
        }
    }
    public List<T> getPreorder(){
        List<T> list = new ArrayList<>();
        getPreorder(root,list);
        return list;
    }
    ///////////////////////////////////////////////////////////

    /**
     * Print the tree in pre-order
     * Iterative version
     */
    public void preOrderIterative() {
        if (root == null) {
            return;
        }
        Stack<Node<T>> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node<T> current = stack.pop();
            System.out.print(current.data + " ");
            if (current.right != null) {
                stack.push(current.right);
            }
            if (current.left != null) {
                stack.push(current.left);
            }
        }
    }

    /**
     * Print the tree in post-order
     * Recursive version
     */
    @Override
    public void postOrder() {
        postOrder(root); // I could instead implement this method in the Node class and write root.postOrder()
    }

    private void postOrder(Node<T> root) { // I could delegate this method to the Node class
        if (root != null) {
            postOrder(root.left);
            postOrder(root.right);
            System.out.print(root.data + " ");
        }
    }

    /**
     * Print the tree in post-order
     * Iterative version
     */
    public void postOrderIterative() {
        if (root == null) {
            return;
        }
        Stack<Node<T>> stack1 = new Stack<>();
        Stack<Node<T>> stack2 = new Stack<>();
        stack1.push(root);
        while (!stack1.isEmpty()) {
            Node<T> current = stack1.pop();
            stack2.push(current);
            if (current.left != null) {
                stack1.push(current.left);
            }
            if (current.right != null) {
                stack1.push(current.right);
            }
        }
        while (!stack2.isEmpty()) {
            System.out.print(stack2.pop().data + " ");
        }
    }

    /**
     * Print the tree in in-order
     * Recursive version
     */
    @Override
    public void inOrder() {
        inOrder(root); // I could instead implement this method in the Node class and write root.inOrder()
    }

    private void inOrder(Node<T> root) { // I could delegate this method to the Node class
        if (root != null) {
            inOrder(root.left);
            System.out.print(root.data + " ");
            inOrder(root.right);
        }
    }

    /**
     * Print the tree in in-order
     * Iterative version
     */
    public void inOrderIterative() {
        if (root == null) {
            return;
        }
        Stack<Node<T>> stack = new Stack<>();
        Node<T> current = root;
        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            current = stack.pop();
            System.out.print(current.data + " ");
            current = current.right;
        }
    }

    @Override
    public NodeTreeInterface<T> getRoot() {
        return root;
    }

    /**
     * Get the height of the tree
     * Recursive version
     */
    @Override
    public int getHeight() {
        return getHeight(root);
    }

    private int getHeight(Node<T> root) { // I could delegate this method to the Node class
        if (root == null) {
            return 0;
        }
        int leftHeight = getHeight(root.left);
        int rightHeight = getHeight(root.right);
        return Math.max(leftHeight, rightHeight) + 1;
    }
    // We could also implement the getHeight method iteratively
    // but the recursive version is much more elegant and concise


    /**
     * Copied from: PPT 19B
     */
    @Override
    public Iterator<T> iterator() {
        switch(traversal){
            case INORDER:
                return new BSTInOrderIterator();
            case BYLEVELS:
                return new BTSByLevelIterator();
        }
        throw new RuntimeException("Invalid traversal parameter");
    }




    
    private static class Node<T extends Comparable<? super T>> implements NodeTreeInterface<T> {
        private T data;
        private Node<T> left;
        private Node<T> right;

        public Node(T data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }

        // If we want to implement the (recursive) insert method
        // we would use the following method
        private void insert(T myData) { // @TODO: should this method be private?
            if (myData.compareTo(data) <= 0) {
                if (left == null) {
                    left = new Node<T>(myData);
                    return;
                }
                left.insert(myData);
                return;
            }
            // If I reach this point,
            // it means that myData is greater than data
            if (right == null) {
                right = new Node<T>(myData);
                return;
            }
            right.insert(myData);
        }

        /**
         *
         *
         */
        private Node delete(T myData) {
            if(myData.compareTo(this.data) < 0){
                if(left != null)
                    left = left.delete(myData);
                return this;
            }
            if(myData.compareTo(this.data) > 0){
                if(right != null)
                    right = right.delete(myData);
                return this;
            }

            // If I reach this point,
            // it means that myData == this.data
            if(left == null)
                return right;
            if(right == null)
                return left;

            T replacement = lexiAdjacent(left);
            this.data = replacement;
            left = left.delete(replacement);
            return this;
        }


        private T lexiAdjacent(Node<T> node) {
            Node<T> aux = node;
            while (aux.right != null)
                aux = aux.right;
            return aux.data;
        }

        @Override
        public T getData() {
            return data;
        }

        @Override
        public NodeTreeInterface<T> getLeft() {
            return left;
        }

        @Override
        public NodeTreeInterface<T> getRight() {
            return right;
        }


    
    }


    /**
     * Inner class that implements the Iterator interface
     * Copied from: PPT 19B slide 18
     */
    class BTSByLevelIterator implements Iterator<T> {
        private Queue<NodeTreeInterface<T>> queue;

        private BTSByLevelIterator() {
            // create an empty queue and add the root node to it
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
            if (!hasNext())
                throw new RuntimeException("There are no more elements to iterate");
            NodeTreeInterface<T> current = queue.remove();
            if (current.getLeft() != null)
                queue.add(current.getLeft());
            if (current.getRight() != null)
                queue.add(current.getRight());
            return current.getData();
        }
    }


    class BSTInOrderIterator implements Iterator<T> {
        private Stack<NodeTreeInterface<T>> stack;
        NodeTreeInterface current;

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
}



// RECURSIVE VERSION OF INSERT METHOD --> IT COULD BE BETTER BY DELEGATING THE INSERTION TO THE NODE CLASS
/*
    public void insertRec(T myData) {
        root = insert(root, myData);
    }

    private Node<T> insertRec(Node<T> root, T myData) {
        if (root == null) {
            root = new Node<T>(myData);
            return root;
        }
        if (myData.compareTo(root.data) < 0) {
            root.left = insert(root.left, myData);
        } else if (myData.compareTo(root.data) > 0) {
            root.right = insert(root.right, myData);
        }
        return root;
    }
 */