package AVL;

public interface AVLTreeInterface<T extends Comparable<? super T>> {

    void insert(T data);

    boolean find(T data);

    String toString();
}
