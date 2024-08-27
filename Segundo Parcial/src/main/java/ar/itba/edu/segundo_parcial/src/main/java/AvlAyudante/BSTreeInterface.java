package AvlAyudante;

import BST.NodeTreeInterface;
public interface BSTreeInterface<T extends Comparable<? super T>> {

    void insert(T myData);

    NodeTreeInterface<T> getRoot();

    int getHeight();

    T getMax();
    T getMin();

    void printByLevels();

    boolean contains (T myData);

    void delete(T myData);

}