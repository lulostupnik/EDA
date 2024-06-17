package eda;


public class MergeSortMagda {
    // Aclaraciones de complejidades y de porqué no es inPlace en el archivo aclaracionesEj2.txt adjuntado.
    public static void mergeSort(String[] a) {
        mergeSort(a, a.length);
    }

    public static void mergeSort(String[] a, int N) {
        String[] aux = new String[a.length];
        System.arraycopy(a, 0, aux, 0, a.length);
        partition(aux, 0, N - 1, a);
    }

    public static void partition(String[] unsorted, int left, int right, String[] aux) {
        if (right == left)
            return;
        int mid = (left + right) / 2;
        partition(aux, left, mid, unsorted);
        partition(aux, mid + 1, right, unsorted);
        mergesortHelper(unsorted, left, mid, right, aux);
    }

    public static void mergesortHelper(String[] unsorted, int left, int mid, int right, String[] aux) {
        int posLeft = left;
        int posRight = mid + 1;
        for (int i = left; i <= right; i++) {
            if (posLeft <= mid && (posRight > right || unsorted[posLeft].compareTo(unsorted[posRight]) <= 0))
                aux[i] = unsorted[posLeft++];
            else
                aux[i] = unsorted[posRight++];
        }
    }
}

/*

Explicación de porqué no es inPlace (inSitu):

La implementación que realicé no es inPlace (inSitu) porque en la misma creo
un array auxiliar de tamaño n.

El código implementado posee complejidad temporal O(n*log(n)) y complejidad
espacial O(n).

Explicación complejidad temporal:

Partition tiene complejidad temporal O(n*log(n)), esto se puede ver por el teorema
maestro, ya que la recurrencia es T(n) = 2*T(n/2) + O(n), y por lo tanto a = 2,
b = 2 y d = 1, y como a = b^d pues 2 = 2^1 entonces la complejidad temporal es
O(n^d*log(n))=O(n*log(n)).
El T(n) posee un termino sumando O(n) que proviene de la llamada a mergesortHelper
que es O(n) porque tiene un for que va desde left hasta right, o sea, depende de n.
Finalmente, volviendo al método original, mergesort, vemos que la complejidad
temporal es O(n*log(n)) + O(n) = O(n*log(n)). Donde el O(n*log(n)) proviene de
partition y el O(n) proviene de hacer un arraycopy.

Explicación complejidad espacial:

La complejidad espacial es O(n) porque en el método mergesort se crea un array de
tamaño n. Los métodos mergesortHelper y partition no agregan complejidad espacial
porque tienen ambos complejidad espacial O(1).



 */