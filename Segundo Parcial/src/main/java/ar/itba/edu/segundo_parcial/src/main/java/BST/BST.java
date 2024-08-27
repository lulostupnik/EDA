package BST;

import BinaryTree.BinaryTree;
import org.w3c.dom.Node;

import java.util.*;
import BST.BSTreeInterface;

public class BST<T extends Comparable<T>> implements BSTreeInterface<T> {

    private NodeTree<T> root = null;
    private int size = 0;
    private Traversal traversal = Traversal.BYLEVELS;

    @Override
    public void insert(T myData) {
        if (myData == null) throw new IllegalArgumentException();
        root = insertRec(root, myData);
    }

    private NodeTree<T> insertRec(NodeTree<T> current, T data) {
        if (current == null) return new NodeTree<>(data);
        if (data.compareTo(current.getData()) >= 0) {
            current.setRight(insertRec(current.getRight(), data));
        } else {
            current.setLeft(insertRec(current.getLeft(), data));
        }
        size++;
        return current;
    }

    public void insertNode(T myData) {
        if (myData == null) throw new IllegalArgumentException();
        if (root == null) {
            root = new NodeTree<>(myData);
            return;
        }
        root.insert(myData);
        size++;
    }

    @Override
    public void preOrder() {
        preOrderRec(root);
    }

    private void preOrderRec(NodeTree<T> current) {
        if (current == null) return;
        System.out.println(current.getData());
        preOrderRec(current.getLeft());
        preOrderRec(current.getRight());
    }

    @Override
    public void postOrder() {
        postOrderRec(root);
    }

    private void postOrderRec(NodeTree<T> current) {
        if (current == null) return;
        postOrderRec(current.getLeft());
        postOrderRec(current.getRight());
        System.out.println(current.getData());
    }

    @Override
    public void inOrder() {
        inOrderRec(root);
    }

    private void inOrderRec(NodeTree<T> current) {
        if (current == null) return;
        inOrderRec(current.getLeft());
        System.out.println(current.getData());
        inOrderRec(current.getRight());
    }

    @Override
    public NodeTreeInterface<T> getRoot() {
        return root;
    }

    @Override
    public int getHeight() {
        if (root == null) return -1;
        return getHeightRec(root) - 1;
    }

    @Override
    public boolean contains(T myData) {
        if (myData == null) throw new IllegalArgumentException();

        return containsRec(root, myData);
    }

    private boolean containsRec(NodeTree<T> current, T myData) {
        if (current == null) return false;
        if (current.getData().equals(myData)) return true;
        if (current.getData().compareTo(myData) > 0) {
            return containsRec(current.getLeft(), myData);
        } else {
            return containsRec(current.getRight(), myData);
        }
    }


    @Override
    public T getMax() {
        if (root == null) throw new NoSuchElementException();
        NodeTree<T> aux = root;
        while (aux.getRight() != null) aux = aux.getRight();
        return aux.getData();
    }


    @Override
    public T getMin() {
        if (root == null) throw new NoSuchElementException();
        NodeTree<T> aux = root;
        while (aux.getLeft() != null) aux = aux.getLeft();
        return aux.getData();
    }

    @Override
    public void printByLevels() {
        printRec(0, root);
    }

    @Override
    public void delete(T myData) {
        if (myData == null) throw new IllegalArgumentException();

        deleteRec(root, myData);
    }

    @Override
    public void setTraversal(Traversal traversal) {
        this.traversal = traversal;
    }

    @Override
    public int getOcurrences(T element) {
        if (element == null) throw new IllegalArgumentException();
        int ocurrences = 0;

        NodeTree<T> current = root;

        while (current != null) {
            if (element.equals(current.getData())){
                current = current.getRight();
                ocurrences++;
            }
            else if(element.compareTo(current.getData()) > 0) {
                current = current.getRight();
            }else{
                current = current.getLeft();
            }
        }

        return ocurrences;
    }

    public T kEsimo(int k){
        if(!(k > 0 && k <= size)){
            return null;
        }
        NodeTree<T> node = new NodeTree<>(root.getData());
        kEsimoRec(k,new int[]{0},root,node);
        return node.getData();
    }
    private void kEsimoRec(int k, int[] count,NodeTree<T> current, NodeTree<T> myData) {
        if(current.getLeft()!=null)
            kEsimoRec(k,count,current.getLeft(), myData);

        if(++count[0]==k)
            myData.setData(current.getData());

        if(current.getRight()!=null)
            kEsimoRec(k,count,current.getRight(), myData);
    }

    @Override
    public T getCommonNode(T element1, T element2) {
        if(root!=null) {
            return getCommonNodeRec(root,element1,element2);
        }
        return null;
    }
    public T getCommonNodeRec(NodeTree<T> current, T element1, T element2) {
        if(current.getLeft()!=null && current.getData().compareTo(element1)>0 && current.getData().compareTo(element2)>0) {
            //left puede ser mas cercano
            return getCommonNodeRec(current.getLeft(),element1,element2);
        }
        else if(current.getRight()!=null && current.getData().compareTo(element1)<0 && current.getData().compareTo(element2)<0) {
            //right puede ser mas cercano
            return getCommonNodeRec(current.getRight(),element1,element2);
        }
        else {
            //yo soy el mas cercano
            if(element1.compareTo(element2)!=0 && current.hasChild(element1) && current.hasChild(element2) )
                return current.getData();
            return null;
        }
    }




    @Override
    public T getCommonNodeWithRepeated(T element1, T element2) {
        if(root!=null) {
            return getCommonNodeWithRepeatedeRec(root,element1,element2);
        }
        return null;
    }

    private T getCommonNodeWithRepeatedeRec(NodeTree<T> current, T element1, T element2) {
        if(current.getLeft()!=null && current.getData().compareTo(element1)>0 && current.getData().compareTo(element2)>0) {
            //left puede ser mas cercano
            return getCommonNodeRec(current.getLeft(),element1,element2);
        }
        else if(current.getRight()!=null && current.getData().compareTo(element1)<0 && current.getData().compareTo(element2)<0) {
            //right puede ser mas cercano
            return getCommonNodeRec(current.getRight(),element1,element2);
        }
        else {
            //yo soy el mas cercano
            if(element1.compareTo(element2)!=0 && current.hasChild(element1) && current.hasChild(element2) )
                return current.getData();
            if(element1.compareTo(element2)==0 && getOcurrences(element1)>=2)
                return current.getData();
            return null;
        }
    }


    private NodeTree<T> deleteRec(NodeTree<T> current, T data) {
        if (current == null) return null;
        if (current.getData().equals(data)) {
            if (current.isLeaf()) {
                return null;
            }
            if (current.getLeft() == null) return current.getRight();
            if (current.getRight() == null) return current.getLeft();

            current.setData(current.getMinOfRight());


        } else if (current.getData().compareTo(data) > 0) {
            current.setLeft(deleteRec(current.getLeft(), data));
        } else {
            current.setRight(deleteRec(current.getRight(), data));
        }
        return current;
    }

    private void printRec(int level, NodeTree<T> current) {
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

    /*
    @Override
    public void inOrder() {
        inOrderRec(root);
    }

    private void inOrderRec(NodeTree<T> current) {
        if (current == null) return;
        inOrderRec(current.getLeft());
        System.out.println(current.getData());
        inOrderRec(current.getRight());
    }
     */

    public void printInRange(T minValue, T maxValue){
        printInRangeRec(root,minValue,maxValue);
    }

    private void printInRangeRec(NodeTree<T> current,T minValue, T maxValue){
        if (current == null) return;
        if(current.getData().compareTo(minValue) >= 0) printInRangeRec(current.getLeft(),minValue,maxValue);
        if(current.getData().compareTo(minValue) >= 0 && current.getData().compareTo(maxValue) <= 0) System.out.println(current.getData());
        if(current.getData().compareTo(maxValue) <= 0) printInRangeRec(current.getRight(),minValue,maxValue);
    }

    private int getHeightRec(NodeTree<T> current) {
        int left = 0;
        int right = 0;

        if (current.getLeft() != null)
            left = getHeightRec(current.getLeft());
        if (current.getRight() != null)
            right = getHeightRec(current.getRight());

        return Math.max(left, right) + 1;
    }

    private Iterator<T> byLevelsIterator(){
        Queue<NodeTree<T>> queue = new LinkedList<>();
        if(root != null) queue.add(root);

        return new Iterator<T>() {

            @Override
            public boolean hasNext() {
                return !queue.isEmpty();
            }

            @Override
            public T next() {
                NodeTree<T> toReturn = queue.remove();
                if(toReturn.getLeft() != null)  queue.add(toReturn.getLeft());
                if(toReturn.getRight() != null)  queue.add(toReturn.getRight());
                return toReturn.getData();
            }
        };

    }

    private Iterator<T> inOrderIterator(){
        return new Iterator<T>() {
            Stack<NodeTreeInterface<T>> stack = new Stack<>();
            NodeTreeInterface<T> current = root;
            @Override
            public boolean hasNext() {
                return !stack.isEmpty() || current != null;
            }

            @Override
            public T next() {
                while(current != null) {
                    stack.push(current);
                    current= current.getLeft();
                }
                NodeTreeInterface<T> elementToProcess= stack.pop();
                current= elementToProcess.getRight();
                return elementToProcess.getData();
            }
        };
    }

    @Override
    public Iterator<T> iterator() {
        return switch (traversal) {
            case BYLEVELS -> byLevelsIterator();
            case INORDER -> inOrderIterator();
            default -> throw new IllegalArgumentException();
        };
    }


    public static void main(String[] args) {
//        BST<Integer> bst = new BST<Integer>();
//        bst.insertNode(10);
//        bst.insertNode(20);
//        bst.insertNode(3);
//        bst.insertNode(14);
//        bst.insertNode(10);
//
//        System.out.println("Preorder: ");
//        bst.preOrder();
//        System.out.println("");
//        System.out.println("Postorder");
//        bst.postOrder();
//        System.out.println("");
//        System.out.println("Inorder");
//        bst.inOrder();
        BST<Integer> myTree = new BST<Integer>();
        myTree.insertNode(80);
        myTree.insert(40);
        myTree.insert(150);
        myTree.insert(20);
        myTree.insert(70);
        myTree.insert(200);
        myTree.insert(50);
        myTree.insert(170);
        myTree.insert(60);
        myTree.insert(170);
        myTree.insert(190);

        myTree.printInRange(35,170);
        System.out.println("-------------------");


    }

}
