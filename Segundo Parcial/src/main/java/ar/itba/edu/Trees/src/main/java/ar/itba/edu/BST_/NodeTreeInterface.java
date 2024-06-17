package ar.itba.edu.BST_;

public interface NodeTreeInterface<T extends Comparable<? super T>> {

    T getData();

    NodeTreeInterface<T> getLeft();

    NodeTreeInterface<T> getRight();

}