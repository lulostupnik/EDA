package org.example;

import java.lang.reflect.Array;
import java.util.Arrays;


public class IndexWithDuplicates<T extends Comparable<? super T>> implements IndexParametricService<T> {

    private T[] indexes;
    private int size;
    private static int BLOCK = 10;
    private final Class<T> theClass;




    @SuppressWarnings("unchecked")
    public IndexWithDuplicates(Class<T> theClass){
        this.size = 0;
        this.indexes = (T[]) Array.newInstance(theClass, size);
        this.theClass = theClass;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initialize(T[] elements) {
        size = 0;
        int dim = elements.length;
        this.indexes = (T[]) Array.newInstance(theClass, dim);
        for(int i = 0; i < dim; i++) {
            insert(elements[i]);
        }
        size = dim;
    }











    void repeatedValues( T[] values,
                         SimpleLinkedList<T> repeatedLst,
                         SimpleLinkedList<T> singleLst,
                         SimpleLinkedList<T> notIndexedLst ){
        if(repeatedLst == null || singleLst == null || notIndexedLst == null){
            throw new IllegalArgumentException("Null arguments");
        }
        int reps;
        for(T val : values){
            reps = occurrences(val);
            if(reps == 0){
                notIndexedLst.insert(val);
            } else if (reps == 1) {
                singleLst.insert(val);
            }else {
                repeatedLst.insert(val);
            }
        }

    }




    @Override
    public boolean search(T key) {
        int pos = getClosestPosition(key);
        return indexes[pos].equals(key);
    }


    @Override
    public void insert(T key) {
        if (size >= indexes.length) {
            indexes = Arrays.copyOf(indexes, indexes.length + BLOCK);
        }
        int pos = getClosestPosition(key);
        for(int i = size; i > pos; i--) {
            indexes[i] = indexes[i-1];
        }
        size++;
        indexes[pos] = key;

    }


    @Override
    public void delete(T key) {
        if(search(key)) {
            int pos = getClosestPosition(key);
            size--;
            for(int i = pos; i<size; i++) {
                indexes[i] = indexes[i+1];
            }
        }
    }

    @Override
    public int occurrences(T key) {
        int pos = getClosestPosition(key), count = 0;
        boolean dif = false;
        while(!dif && (pos < size)){
            if(!indexes[pos].equals(key)){
                dif = true;
            }else
                count++;
            pos++;
        }

        return count;
    }

    private int getClosestPosition(T key) {
        int l = 0, r = size;
        while(l < r) {
            int mid = l + (r - l) / 2;
            if (indexes[mid].compareTo(key) >= 0) {
                r = mid;
            } else {
                l = mid+1;
            }
        }
        return l;
    }


    @SuppressWarnings("unchecked")
    public T[] range(T leftKey, T rightKey, boolean leftIncluded, boolean rightIncluded){
        T[] tgt = (T[]) Array.newInstance(theClass, size);

        //@TODO: Chequear, parece funcionar
        int posL = leftIncluded ? getClosestPosition(leftKey) : getClosestPosition(leftKey) + occurrences(leftKey);
        int posR = rightIncluded ? getClosestPosition(rightKey) + occurrences(rightKey) : getClosestPosition(rightKey);

        if(posR - posL < 1)
            tgt = Arrays.copyOfRange(indexes, 0, 0);
        else
            tgt = Arrays.copyOfRange(indexes, posL, posR);

        System.out.print("[ ");
        for(int i = 0; i< tgt.length; i++) System.out.print(tgt[i] + " ");
        System.out.println("]");

        return tgt;
    }

    public void sortedPrint(){
        System.out.print("[ ");
        for(int i = 0; i< size; i++) System.out.print(indexes[i] + " ");
        System.out.println("]");
    }

    public T getMax(){
        if(size < 1)
            throw new RuntimeException("No indexes saved");
        return indexes[size - 1];
    }

    public T getMin(){
        if(size < 1)
            throw new RuntimeException("No indexes saved");
        return indexes[0];
    }


    public static void main(String[] args) {
        IndexParametricService<Integer>  myIndex= new IndexWithDuplicates<>(Integer.class);
        Integer[] rta = myIndex.range(10, 50, true, true);

        myIndex.initialize( new Integer[] {100, 50, 30, 50, 80, 50, 50, 30, 30});
        myIndex.sortedPrint();
        rta = myIndex.range(10, 50, true, true);

        rta = myIndex.range(10, 50, true, false);


        IndexParametricService<String>  anIndex=  new  IndexWithDuplicates<>(String.class);
        String[] rta2 = anIndex.range("hola", "tal", true, true);

        anIndex.initialize( new String[] {"hola", "ha", "sii" });
        rta2 = anIndex.range("a", "b", true, true);
        rta2 = anIndex.range("a", "quizas", true, true);

        anIndex.initialize( new String[] {"a", "b", "c", "d" });
        rta2 = anIndex.range("a", "b", true, true);
        rta2 = anIndex.range("a", "c", true, false);

    }


}
