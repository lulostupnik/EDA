package ar.edu.itba.eda.Index;

import ar.edu.itba.eda.Index.Index;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class GenericIndexWithDuplicates<T extends Comparable<? super T>> implements Index<T> {

    private static final int CHUNK_SIZE = 10;
    private T[] vec;
    private int size=0;

    public GenericIndexWithDuplicates(Class<T> clazz) {
        vec= (T[]) Array.newInstance(clazz,CHUNK_SIZE);
    }

    public void initialize(T[] elements){
        if(elements==null) throw new IllegalArgumentException();
        Arrays.sort(elements);
        vec=elements;
        size=elements.length;
    }
    @Override
    public void delete(T key) {
        int index = getClosestPosition(0, size-1, key);
        int ocu = occurrences(key);

        if (ocu == 0) return;

        while (index >= 0 && vec[index] == key) {
            index--;
        }
        index++;

        int remaining = size - index;
        if (remaining > 0) {
            System.arraycopy(vec, index + ocu, vec, index, remaining);
        }

        size -= ocu;
    }


    @Override
    public void insert(T key) {
        if(key==null) throw new IllegalArgumentException();
        if(size+1>=vec.length){
            vec=Arrays.copyOf(vec,vec.length+CHUNK_SIZE);
        }
        if(size==0){
            vec[0]=key;
            size=1;
            return;
        }
        int index=getClosestPosition(0,size-1,key);
        T aux=key;
        for(int i=index; i<=size;i++){
            T next=vec[i];
            vec[i]=aux;
            aux=next;
        }

        size++;
    }

    @Override
    public int occurrences(T key) {
        int i=0;
        int index=getClosestPosition(0,size-1,key);
        int aux=index;
        //if(aux == size) return 0;
        while(aux<size && key.equals(vec[aux])){
            i++;
            aux++;
        }
        index--;
        while(index>=0 && key==vec[index]){
            index--;
            i++;
        }

        return i;
    }

    @Override
    public boolean search(T key) {
        int index = getClosestPosition(0,size-1,key);
        return key.equals(vec[index]);
    }

    @Override
    public T getMax() {
        if(size==0) throw new NoSuchElementException();
        return vec[size-1];
    }

    @Override
    public T getMin() {
        if(size==0) throw new NoSuchElementException();
        return vec[0];
    }
    @SuppressWarnings("unchecked")
    @Override
    public T[] range(T leftKey, T rightKey, boolean includeLeft, boolean includeRight) {
        int leftIndex=getClosestPosition(0,size-1,leftKey);
        int rightIndex=getClosestPosition(0,size-1,rightKey);

        leftIndex = Math.min(vec.length-1,leftIndex);
        rightIndex = Math.min(vec.length-1,rightIndex);

        if(includeLeft){
            while(leftIndex>=0 && vec[leftIndex].equals(leftKey)) leftIndex--;
            if(vec[leftIndex+1]==leftKey)
                leftIndex++;
        }else{
            while(leftIndex<size && vec[leftIndex].equals(leftKey)) leftIndex++; //puede que se vaya del vector pero en el if que compara los extremos sale
        }
        if(includeRight){
            while(rightIndex<size && vec[rightIndex].equals(rightKey)) rightIndex++;
            if(vec[rightIndex-1]==rightKey)
                rightIndex--;
        }else{
            while(rightIndex>=0 && vec[rightIndex].equals(rightKey)) rightIndex--;

        }

        if(leftIndex>rightIndex  || rightIndex>=size) return null;
        return Arrays.copyOfRange(vec,leftIndex,rightIndex+1); //El extremo derecho no esta incluido
    }

    public T[] rangeSanti(T leftKey, T rightKey, boolean includeLeft, boolean includeRight) {
        int leftIndex=getClosestPosition(0,size-1,leftKey);
        int rightIndex=getClosestPosition(0,size-1,rightKey);

        leftIndex = Math.min(size-1,leftIndex);
        rightIndex = Math.min(size-1,rightIndex);

        if(includeLeft){
            while(leftIndex>=0 && vec[leftIndex].equals(leftKey)) leftIndex--;
            if(leftIndex+1<= size-1 && vec[leftIndex+1]==leftKey)
                leftIndex++;
        }else{
            while(leftIndex<size && vec[leftIndex].equals(leftKey)) leftIndex++; //puede que se vaya del vector pero en el if que compara los extremos sale

        }
        if(includeRight){
            while(rightIndex<size && vec[rightIndex].equals(rightKey)) rightIndex++;
            if(vec[rightIndex-1]==rightKey)
                rightIndex--;
        }else{
            while(rightIndex>=0 && vec[rightIndex].equals(rightKey)) rightIndex--;

        }

        if( leftIndex>rightIndex  || leftIndex>=size || leftKey.compareTo(vec[leftIndex])>0) return null;

        return Arrays.copyOfRange(vec,leftIndex,rightIndex+1); //El extremo derecho no esta incluido
    }



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


    @Override
    public void sortedPrint(){
        for(int i=0;i<size;i++){
            System.out.println(vec[i]);
        }
        System.out.println("");
    }


}
