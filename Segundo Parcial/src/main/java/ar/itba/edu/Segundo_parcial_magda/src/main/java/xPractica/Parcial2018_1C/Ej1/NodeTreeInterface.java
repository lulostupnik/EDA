package xPractica.Parcial2018_1C.Ej1;

public interface NodeTreeInterface<T extends Comparable<? super T>> {

    T getData();

    NodeTreeInterface<T> getLeft();

    NodeTreeInterface<T> getRight();

}