package BTree;

public class Test {

    public static void main(String[] args) {
        BTree<Integer> bTree = new BTree<Integer>();
        bTree.add(12);
        bTree.add(32);
        bTree.add(33);
        System.out.println(bTree);
    }
}
