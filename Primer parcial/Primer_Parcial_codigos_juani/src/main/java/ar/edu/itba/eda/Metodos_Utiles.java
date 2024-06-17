package ar.edu.itba.eda;

public class Metodos_Utiles<T extends Comparable<? super T>> {


    private T[] vec;

    //si esta el elemento me devuelve uno de los indices en los que se encuentra
    //si no esta el elemento me da el indice donde se deberia insertar
    //ojo, puede ser que inserte en vec[vec.length]
    private int getClosestPosition(int izq,int der,T element){
        if(izq>der) return izq;
        int mid=(der+izq)/2;
        T midElement=(T)vec[mid];
        if(element.equals(midElement)) return mid;
        if(element.compareTo(midElement)<0){
            return getClosestPosition(izq,mid-1,element);
        }
        return getClosestPosition(mid+1,der,element);
    }

    private int busquedaBinaria(int izq,int der,T element){
        if(izq>der) return -1;
        int mid=(der+izq)/2;
        T midElement=(T)vec[mid];
        if(element.equals(midElement)) return mid;
        if(element.compareTo(midElement)<0){
            return busquedaBinaria(izq,mid-1,element);
        }
        return busquedaBinaria(mid+1,der,element);
    }



    public static void main(String[] args) {

    }



}
