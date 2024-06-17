package ar.edu.itba.eda.AlgoritmosDeSorteo;

public class MergeSort {
    public static void main(String[] args) {
        int[] vec = {1, 8, 5, 2, 3, 16};

        mergeSort(vec, 0, vec.length - 1);

        for (int num : vec) {
            System.out.printf("%d ", num);
        }
    }

    public static void mergeSort(int[] array, int left, int right) {
        if (left < right) {
            int middle = (left + right) / 2;

            mergeSort(array, left, middle);
            mergeSort(array, middle + 1, right);

            merge(array, left, middle, right);
        }
    }

    public static void merge(int[] array, int left, int middle, int right) {
        int[] aux = new int[right - left + 1];

        int i = left, j = middle + 1, k = 0;

        while (i <= middle && j <= right) {
            if (array[i] <= array[j]) {
                aux[k++] = array[i++];
            } else {
                aux[k++] = array[j++];
            }
        }

        while (i <= middle) {
            aux[k++] = array[i++];
        }

        while (j <= right) {
            aux[k++] = array[j++];
        }

        System.arraycopy(aux,0,array,left,aux.length);
    }
}