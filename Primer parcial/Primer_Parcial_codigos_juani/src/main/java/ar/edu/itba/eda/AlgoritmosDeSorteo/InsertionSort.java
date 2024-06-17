package ar.edu.itba.eda.AlgoritmosDeSorteo;

public class InsertionSort {

    public static void main(String[] args) {
        int[] vec= {2,8,5,3,9,4,9,5};
        for(int num: vec){
            System.out.printf("%d",num);
        }
        System.out.println();
        insertionSort(vec);
        for(int num: vec){
            System.out.printf("%d",num);
        }
    }
// 2 8 5 3 9 4
    public static void insertionSort(int[] array) {
        int aux;
        int j;
        for(int i = 1;i < array.length;i++){
            aux = array[i];
            for(j = i-1;j >= 0 && array[j] > aux;j--){
                array[j+1] = array[j];
                array[j] = aux;
            }
        }
    }



}
