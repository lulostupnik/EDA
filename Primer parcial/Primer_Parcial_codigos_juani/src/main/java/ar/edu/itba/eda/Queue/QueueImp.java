package ar.edu.itba.eda.Queue;

import java.lang.reflect.Array;
import java.util.NoSuchElementException;

public class QueueImp<T> implements Queue<T>{

    private T[] queue;
    private int first=1, last=0;
    private int size=0;

    public QueueImp(Class<T> clazz, int dim) {
        queue = (T[]) Array.newInstance(clazz,dim);
    }

    @Override
    public void queue(T element) {
        int index = next(last);
        if(queue[index]!=null){
            throw new RuntimeException();
        }
        last=index;
        queue[last]=element;
        size++;
    }

    private int next(int idx){
        return (idx+1) % queue.length;
    }

    @Override
    public T dequeue() {
        if(size == 0 ) throw new RuntimeException();
        T aux = queue[first];
        queue[first]=null;
        first = next(first);
        size--;
        return aux;
    }

    public void print(){
        int i = first;
        int count = 0;
        StringBuilder sb = new StringBuilder();
        while (count < size){
            sb.append(queue[i]);
            sb.append(" ");
            i = next(i);
            count++;
        }
        System.out.println(sb);
    }

    @Override
    public T peek() {
        if(size==0) throw new RuntimeException();
        return queue[first];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }
}
