package ar.itba.edu.ej2;

import java.util.Arrays;

/**
 * @author dpenaloza
 *
 */

public class IndexWithDuplicates  {

    final static private int chunksize= 5;

    private int[] indexedData;
    private int cantElems;


    public IndexWithDuplicates() {
        indexedData= new int[chunksize];
        cantElems= 0;
    }

    public void initialize(int[] unsortedElements) {

        if (unsortedElements == null)
            throw new RuntimeException("Problem: null data collection");

        indexedData= unsortedElements;
        Arrays.sort(indexedData);
        cantElems= indexedData.length;
    }


    public int[] getIndexedData() {
        return indexedData;
    }

    public void print() {
        System.out.print("[");
        for (int i : indexedData)
            System.out.print(i + " ") ;
        System.out.println("]");

    }

    public static void main(String[] args) {
        IndexWithDuplicates index1 = new IndexWithDuplicates();
        index1.initialize(new int[] {1, 3, 5, 7});
        IndexWithDuplicates index2 = new IndexWithDuplicates();
        index2.initialize(new int[] {2, 4, 6, 8});
        index1.merge(index2);
    }



    public void merge(IndexWithDuplicates other) {
        int aux_cant_elems = this.cantElems + other.cantElems; // creo
        int[] aux = new int[aux_cant_elems];


        int index_arr1 =  0, index_arr2=0, aux_index=0;
        while (index_arr1 < cantElems && index_arr2 < other.cantElems) {
            if (indexedData[index_arr1] <= other.indexedData[index_arr2]) {
                aux[aux_index++] = indexedData[index_arr1++];
            } else {
                aux[aux_index++] = other.indexedData[index_arr2++];
            }
        }

        while (index_arr1 < cantElems) {
            aux[aux_index++] = indexedData[index_arr1++];
        }

        while (index_arr2 < other.cantElems) {
            aux[aux_index++] = other.indexedData[index_arr2++];
        }

        indexedData = aux;
        cantElems = aux_cant_elems;
    }


}

