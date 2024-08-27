package AvlAyudante;

public class Test {

    public static void main(String[] args) {
        AVL<Integer> avl = new AVL<>();
        avl.insert(2);
        avl.insert(3);
        avl.printHierarchy();
    }


}
