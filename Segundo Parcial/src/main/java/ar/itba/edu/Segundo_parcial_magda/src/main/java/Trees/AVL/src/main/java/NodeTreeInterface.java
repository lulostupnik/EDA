package Trees.AVL.src.main.java;

public interface NodeTreeInterface<T extends Comparable<? super T>> {

    T getData();

    NodeTreeInterface<T> getLeft();

    NodeTreeInterface<T> getRight();

}