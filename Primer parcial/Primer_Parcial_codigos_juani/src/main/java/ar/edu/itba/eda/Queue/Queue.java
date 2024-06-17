package ar.edu.itba.eda.Queue;

public interface Queue<T>{
    //agrega un elemento a la colección conviertiéndolo en el más reciente o sea, se convierte en el nuevo LAST.
    void queue(T element);

    //quita el elemento más antiguo de la colección (FIRST) y cambia el FIRST. Es una operación destructiva y solo puede usarse si la colección no está vacía.
    T dequeue();

    //devuelve el element más antiguo de la colección (FIRST) sin removerlo (sin cambiar el FIRST). No es destructiva. Solo puede usarse si la colección no está vacía.
    T peek();

    //devuelve true/false según la colección tenga o no elementos.
    boolean isEmpty();

    //(opcional) devuelve la cantidad de elementos de la colección y es ideal para estimar cuánto hay que esperar por ser atendido

    int size();

}
