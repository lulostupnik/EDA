package eda.not_comparable;

import java.util.Arrays;

public class IndexServiceImpl implements IndexService{
    private int [] array;
    private static int CHUNKSIZE = 10;
    private int dim;
    IndexServiceImpl(){
        array = new int[CHUNKSIZE];
        dim = 0;
    }

    private boolean hasSpace(){
        return array.length > dim;
    }
    private void resize(){
        array = Arrays.copyOf(array, dim+CHUNKSIZE);
    }
    private void checkAndResize(){
        if(!hasSpace()){
            resize();
        }
    }
    private void checkAndResize(int[] arr, int dimension){
        if(!(arr.length > dimension)){
            arr = Arrays.copyOf(arr, dimension+CHUNKSIZE);
        }
    }


@Override
public void initialize(int [] elements){
        if(elements == null){
            throw new IllegalArgumentException();
        }
        array = new int[elements.length];
        dim = 0;
        for(int elem : elements){
            insert(elem);
        }
    }

    // busca una key en el índice, O(log2 N)
    @Override
    public boolean search(int key){
        int pos = getClosestPosition(key, 0 , dim-1);
        if(array[pos] == key){
            return true;
        }
        return false;

    }

    // inserta el key en pos correcta. Crece automáticamente de a chunks
    @Override
    public void insert(int key){
        int pos = getClosestPosition(key, 0, dim);
        dim++;
        checkAndResize();
        for(int i=dim-1; i>=pos; i--){
            array[i+1] = array[i];
        }
        array[pos] = key;

    }

    public int getMax(){
        if(dim < 1){
            throw new RuntimeException();
        }
        return array[dim-1];
    }
    public int getMin(){
        if(dim<1){
            throw new RuntimeException();
        }
        return array[0];
    }

    private int getIndexMaxExcluded(int max, int i_max, int i_min){
        for(int i=i_max; i>i_min; i--){
            if(array[i] < max){
                return i;
            }
        }
        throw new IllegalArgumentException();
    }
    private int getIndexMinExcluded(int min, int i_min, int i_max){
        for(int i=i_min; i<i_max; i++){
            if(array[i] > min){
                return i;
            }
        }
        throw new IllegalArgumentException();
    }

    //check
    @Override
   public  int[] range(int min, int max, boolean minIncluded, boolean maxIncluded){
        if(min >= max){
            throw new IllegalArgumentException();
        }

        int min_i = getClosestPosition(min, 0, dim-1);
        int max_i = getClosestPosition(max, 0,dim-1);

        if(!maxIncluded){
            try{
                max_i = getIndexMaxExcluded(max, max_i, min_i);
            }catch (IllegalArgumentException e){
                return new int[0];   // sino puedo dejar que lanze excepcion. Pero me parece correcto que si no hay ningun elemento en el rango se devuelva un vector vacio.
            }
        }

        for(int j=max_i; j<dim && array[j] == max ; j++){
            max_i++;
        }

        if(!minIncluded){
            try{
                min_i=getIndexMinExcluded(min, min_i, max_i);
            }catch (IllegalArgumentException e){
                return new int[0];
            }

        }
        return Arrays.copyOfRange(array, min_i, max_i+1);
    }


    // log2(n)
    private int getClosestPosition(int key, int izq, int der){
        if(izq >= der){
            return izq;
        }
        int mid = (der+izq)/2;
        if(key < array[mid]){
            return getClosestPosition(key, izq, mid-1);
        }
        if(key > array[mid]){
            return getClosestPosition(key, mid+1, der);
        }
        return mid;

    }




    // borra el key si lo hay, sino lo ignora.
    // decrece automáticamente de a chunks
    @Override
    public void delete(int key){
        int pos = getClosestPosition(key, 0, dim-1);
        if(array[pos] == key){
            dim--;
            for(int i=pos ; i< dim; i++){
                array[i] = array[i+1];
            }
        }
    }

    // devuelve la cantidad de apariciones de la clave especificada
    @Override
    public int occurrences(int key){
        int ans = 0;
        int pos = getClosestPosition(key, 0, dim-1);
        for(int i=pos; i<dim && array[i] == key; i++){
            ans++;
        }
        for(int i=pos-1 ; i>0 && array[i]==key ; i--){
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
