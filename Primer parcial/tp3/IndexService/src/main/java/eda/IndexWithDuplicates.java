package eda;

import java.util.Arrays;

public class IndexWithDuplicates<T extends Comparable<T>> implements IndexService<T> {
    private T[] array;
    private static int CHUNKSIZE = 10;
    private int dim;

    IndexWithDuplicates() {
        array = (T[]) new Comparable[CHUNKSIZE];
        dim = 0;
    }

    private boolean hasSpace() {
        return array.length > dim;
    }

    private void resize() {
        array = Arrays.copyOf(array, dim + CHUNKSIZE);
    }

    private void checkAndResize() {
        if (!hasSpace()) {
            resize();
        }
    }

    private void checkAndResize(T[] arr, int dimension) {
        if (!(arr.length > dimension)) {
            arr = Arrays.copyOf(arr, dimension + CHUNKSIZE);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initialize(T[] elements) {
        if(elements == null){
            throw new IllegalArgumentException();
        }
        dim = 0;
        array = (T[]) new Comparable[CHUNKSIZE];
        for(T elem : elements){
            insert(elem);
        }
    }

    @Override
    public boolean search(T key) {
        int pos = getClosestPosition(key, 0, dim - 1);
        return array[pos].compareTo(key) == 0;
    }

    @Override
    public void insert(T key) {
        int pos = getClosestPosition(key, 0, dim);
        dim++;
        checkAndResize();
        for (int i = dim - 1; i >= pos; i--) {
            array[i + 1] = array[i];
        }
        array[pos] = key;
    }

    public T getMax() {
        if (dim < 1) {
            throw new RuntimeException();
        }
        return array[dim - 1];
    }

    public T getMin() {
        if (dim < 1) {
            throw new RuntimeException();
        }
        return array[0];
    }

    private int getIndexMaxExcluded(T max, int i_max, int i_min) {
        for (int i = i_max; i > i_min; i--) {
            if (array[i].compareTo(max) < 0) {
                return i;
            }
        }
        throw new IllegalArgumentException();
    }

    private int getIndexMinExcluded(T min, int i_min, int i_max) {
        for (int i = i_min; i < i_max; i++) {
            if (array[i].compareTo(min) > 0) {
                return i;
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public T[] range(T min, T max, boolean minIncluded, boolean maxIncluded) {
        if (min.compareTo(max) >= 0) {
            throw new IllegalArgumentException();
        }

        int min_i = getClosestPosition(min, 0, dim - 1);
        int max_i = getClosestPosition(max, 0, dim - 1);

        if (!maxIncluded) {
            try {
                max_i = getIndexMaxExcluded(max, max_i, min_i);
            } catch (IllegalArgumentException e) {
                return Arrays.copyOfRange(array, 0, 0);
            }
        }

        for (int j = max_i; j < dim && array[j].compareTo(max) == 0; j++) {
            max_i++;
        }

        if (!minIncluded) {
            try {
                min_i = getIndexMinExcluded(min, min_i, max_i);
            } catch (IllegalArgumentException e) {
                return Arrays.copyOfRange(array, 0, 0);
            }
        }
        return Arrays.copyOfRange(array, min_i, max_i + 1);
    }

    private int getClosestPosition(T key, int izq, int der) {
        if (izq >= der) {
            return izq;
        }
        int mid = (der + izq) / 2;
        if (key.compareTo(array[mid]) < 0) {
            return getClosestPosition(key, izq, mid - 1);
        }
        if (key.compareTo(array[mid]) > 0) {
            return getClosestPosition(key, mid + 1, der);
        }
        return mid;
    }

    @Override
    public void delete(T key) {
        int pos = getClosestPosition(key, 0, dim - 1);
        if (array[pos].compareTo(key) == 0) {
            dim--;
            for (int i = pos; i < dim; i++) {
                array[i] = array[i + 1];
            }
        }
    }

    @Override
    public int occurrences(T key) {
        int ans = 0;
        int pos = getClosestPosition(key, 0, dim - 1);
        for (int i = pos; i < dim && array[i].compareTo(key) == 0; i++) {
            ans++;
        }
        for (int i = pos - 1; i > 0 && array[i].compareTo(key) == 0; i--) {
            ans++;
        }
        return ans;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for(int i=0 ; i<dim ; i++){
            sb.append(array[i]);
            sb.append(", ");
        }
        sb.setLength(sb.length()-2);
        sb.append(']');
        return sb.toString();

    }
}
