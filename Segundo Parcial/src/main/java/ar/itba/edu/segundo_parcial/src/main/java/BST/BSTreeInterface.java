package BST;

public interface BSTreeInterface<T extends Comparable<? super T>> extends Iterable<T> {
    void insert(T myData);
    void preOrder();
    void postOrder();
    void inOrder();
    NodeTreeInterface<T> getRoot();
    int getHeight();
    boolean contains(T myData);
    T getMax();
    T getMin();
    void printByLevels();
    void delete(T myData);
    enum Traversal { BYLEVELS, INORDER }
    void setTraversal(Traversal traversal);
    int getOcurrences(T element);
    T kEsimo(int k);
    T getCommonNode(T element1, T element2);
    T getCommonNodeWithRepeated(T element1, T element2);
}