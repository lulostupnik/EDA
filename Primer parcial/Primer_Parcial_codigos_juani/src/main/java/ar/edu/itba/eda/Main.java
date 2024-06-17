package ar.edu.itba.eda;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    static public int[] vec = {1,2,5,5,5,5,5,5,9};
    static int size = vec.length;

    private static int getClosestPosition(int izq,int der,int element){
        if(izq>der) return izq;
        int mid=(der+izq)/2;
        int midElement=vec[mid];
        if(element == midElement) return mid;
        if(element-midElement<0){
            return getClosestPosition(izq,mid-1,element);
        }
        return getClosestPosition(mid+1,der,element);
    }

    public static void main(String[] args) {
        System.out.println(getClosestPosition(0,8,-5));
    }
}