package ar.edu.itba.eda.AlgoritmosDeSorteo;

public class QuickSort {
    public static void main(String[] args) {
        int[] vec= {5,2,1,6,4,3};
        for(int num: vec){
            System.out.printf("%d",num);
        }
        System.out.println("");
        quickSortHelper(vec,0,vec.length-1);
        for(int num: vec){
            System.out.printf("%d",num);
        }
    }
    private static void quickSortHelper(int[] unsorted, int leftPos, int rightPos) {
        if (rightPos <= leftPos)
            return;

        int pivotValue = unsorted[leftPos];
        swap(unsorted,rightPos,leftPos);

        int pivotPosCalculated = partition(unsorted, leftPos, rightPos - 1, pivotValue);

        swap(unsorted, pivotPosCalculated, rightPos);

        quickSortHelper(unsorted, leftPos, pivotPosCalculated - 1);
        quickSortHelper(unsorted, pivotPosCalculated + 1, rightPos);
    }

    private static int partition(int[] array, int leftPos, int rightPos, int pivoteValue) {
        if (leftPos > rightPos) {
            return leftPos;
        }
        if (array[leftPos] > pivoteValue && array[rightPos] < pivoteValue) {
            swap(array, leftPos, rightPos);
        }
        if (array[leftPos] < pivoteValue) {
            return partition(array, leftPos + 1, rightPos, pivoteValue);
        }
        if (array[rightPos] > pivoteValue) {
            return partition(array, leftPos, rightPos - 1, pivoteValue);
        }
        return partition(array,leftPos+1,rightPos-1,pivoteValue);
    }

    private static void swap(int[] array, int leftPos, int rightPos){
        int aux=array[leftPos];
        array[leftPos]=array[rightPos];
        array[rightPos]=aux;
    }
}