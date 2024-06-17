package eda;

import static eda.ArraysUtilities.quicksort;

public class Main {
    public static void main(String[] args) {
        int[] unsorted = new int[] {34, 10, 8, 60, 21, 17, 28, 30, 2, 70, 50, 15, 62, 40};
        quicksort( unsorted) ;

        for (int i : unsorted) {
            System.out.print(i + " ");
        }
    }

}

/*
Quicksort: Opera in-place, es decir que opera sobre el mismo arreglo (no necesita de otro). Aplica la técnica Divide & Conquer y puede implementarse recursivamente o iterativamente. Lo que hace es particionar en sub arreglos.
Elije un elemento que funciona como pivote (puede ser el primero). Va a modificar el arreglo para saber en qué lado va el pivote y garantizar que a la izquierda van a estar los menores y a la derecha los mayores.
Dicho formalmente: En cada sub-arreglo elige un pivot y ordenada para que todos los elementos a la izquierda del pivot sean menores que él y los de la derecha sean mayores que él => el pivot está en la posición correcta. Si un sub-arreglo tiene  0 o 1 elemento, está ya ordenado (no continua) => fin de la recurrencia.



 */