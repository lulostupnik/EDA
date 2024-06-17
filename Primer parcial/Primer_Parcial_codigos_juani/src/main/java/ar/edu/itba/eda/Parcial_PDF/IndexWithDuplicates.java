package ar.edu.itba.eda.Parcial_PDF;

import java.util.Arrays;

public class IndexWithDuplicates<E extends Comparable<E> > {

    private final int chunk_size = 5;
    private E [] m_idx;
    private int m_size;
    @SuppressWarnings("unchecked")
    public IndexWithDuplicates(){
        m_idx = (E[]) new Comparable [chunk_size];
    }

    public void initialize(E[] elements) {
        if (elements == null) {
            throw new IllegalArgumentException("elements cannot be null");
        }
        for ( E e : elements )
            insert(e);
    }

    private void grow(){
        if (m_size < m_idx.length)
            return;
        m_idx = Arrays.copyOf(m_idx, m_idx.length + chunk_size );
    }

    public int occurrences(E key) {
        int i=0;
        int index=getClosestPosition(0,m_size-1,key);
        int aux=index;
        //if(aux == m_size) return 0;
        while(aux<m_size && key.equals(m_idx[aux]) ){
            i++;
            aux++;
        }
        index--;
        while(index>=0 && key==m_idx[index]){
            index--;
            i++;
        }

        return i;
    }

    private int getClosestPosition(int izq,int der,E element){
        if(izq>der) return izq;
        int mid=(der+izq)/2;
        E midElement=(E)m_idx[mid];
        if(element.equals(midElement)) return mid;
        if(element.compareTo(midElement)<0){
            return getClosestPosition(izq,mid-1,element);
        }
        return getClosestPosition(mid+1,der,element);
    }

    public void insert(E key) {
        grow();

        int position = 0;
        for ( position = 0; position < m_size && m_idx[position].compareTo( key ) < 0; ++position);

        for (int i = m_size; i > position; --i)
            m_idx[i] = m_idx[i - 1];
        m_idx[position] = key;
        ++m_size;

    }

    void repeatedValues( E[] values, SimpleLinkedList<E> repeatedLst, SimpleLinkedList<E> singleLst, SimpleLinkedList<E> notIndexedLst )
    {
        int aux;
        for (E value : values) {
            aux = occurrences(value);
            if (aux == 0)
                notIndexedLst.insert(value);
            else if (aux == 1)
                singleLst.insert(value);
            else
                repeatedLst.insert(value);
        }
    }


    public static void main(String[] args) {
        IndexWithDuplicates<Integer> idx = new IndexWithDuplicates<>();
        idx.initialize(  new Integer[] {100, 50, 30, 50, 80, 10, 100, 30, 20, 138} );


        SimpleLinkedList<Integer> repeatedLst = new SimpleLinkedList();
        SimpleLinkedList<Integer> singleLst  = new SimpleLinkedList();
        SimpleLinkedList<Integer> notIndexedLst  = new SimpleLinkedList();
        idx.repeatedValues( new Integer[] { 10, 80, 10, 35, 80, 80 , 1111}, repeatedLst, singleLst, notIndexedLst );
        //{ 10, 80, 10, 35, 80, 80 , 1111}
        System.out.println("Repeated Values");
        repeatedLst.dump();

        System.out.println("Single Values");
        singleLst.dump();

        System.out.println("Non Indexed Values");
        notIndexedLst.dump();

    }


}
