package eda;

public interface IndexService<T extends Comparable<T>> {

    void initialize(T[] elements);
    boolean search(T key);
    void insert(T key);
    void delete(T key);
    int occurrences(T key);
    T[] range(T min, T max, boolean minIncluded, boolean maxIncluded);
    T getMax();
    T getMin();
}
