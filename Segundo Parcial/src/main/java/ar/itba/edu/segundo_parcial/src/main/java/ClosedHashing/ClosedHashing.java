package ClosedHashing;
import java.io.*;
import java.util.Arrays;
import java.util.function.Function;


public class ClosedHashing<K, V> implements IndexParametricService<K, V> {
    private int initialLookupSize= 10;
    private int size = 0;
    private final float loadFactor = 0.75f;

    // estática. No crece. Espacio suficiente...
    @SuppressWarnings({"unchecked"})
    private Slot<K,V>[] Lookup= (Slot<K,V>[]) new Slot[initialLookupSize];

    private Function<? super K, Integer> prehash;

    public ClosedHashing( Function<? super K, Integer> mappingFn) {
        prehash= mappingFn;
    }

    private float chargeFactor(){
        return size/(float)Lookup.length;
    };

    // ajuste al tamaño de la tabla
    private int hash(K key) {
        if (key == null)
            throw new IllegalArgumentException("key cannot be null");

        return prehash.apply(key) % Lookup.length;
    }

    private int next(int index){
        if(index == Lookup.length -1) return 0;
        return index+1;
    }

    private void rehash(){

        for(int i=0; i<Lookup.length/2; i++){
            if(Lookup[i] != null){
                if(Lookup[i].deleted) Lookup[i] = null;
                else{
                    Slot<K,V> aux = Lookup[i];
                    Lookup[i] = null;
                    int index = hash(aux.key);
                    while (Lookup[index] != null && !Lookup[index].deleted) {
                        index = next(index);
                    }
                    Lookup[ index ] = aux;
                }
            }
        }

    }

    public void insertOrUpdate(K key, V data) {
        if (key == null || data == null) {
            String msg= String.format("inserting or updating (%s,%s). ", key, data);
            if (key==null)
                msg+= "Key cannot be null. ";

            if (data==null)
                msg+= "Data cannot be null.";

            throw new IllegalArgumentException(msg);
        }

        int index= hash(key);
        while (Lookup[index] != null && !Lookup[index].deleted && !Lookup[index].key.equals(key)) {
            index = next(index);
        }
        if(!Lookup[index].key.equals(key))
            size++;
        Lookup[ index ] = new Slot<>(key, data, false);

        if(chargeFactor()>=loadFactor){
            Lookup = Arrays.copyOf(Lookup, Lookup.length*2);
            rehash();
        }

    }



    // find or get
    public V find(K key) {
        if (key == null)
            return null;

        int index = hash(key);
        int i =0;
        while(i<Lookup.length && Lookup[index]!= null){
            if(Lookup[index].key.equals(key)){
                return Lookup[index].value;
            }
            i++;
            index= next(index);
        }
        return null;
    }

    public boolean remove(K key) {
        if (key == null)
            return false;

        int index = hash(key);
        int i =0;
        while(i<Lookup.length && Lookup[index]!= null){
            if(Lookup[index].key.equals(key)){
                if(Lookup[next(index)] == null) Lookup[index] = null;
                else Lookup[index].deleted = true;
                return true;
            }
            i++;
            index= next(index);
        }

        return false;
    }


    public void dump()  {
        for(int rec= 0; rec < Lookup.length; rec++) {
            if (Lookup[rec] == null || Lookup[rec].deleted)
                System.out.println(String.format("slot %d is empty", rec));
            else
                System.out.println(String.format("slot %d contains %s",rec, Lookup[rec]));
        }
    }


    public int size() {
        return size;
    }



    static private final class Slot<K, V>	{
        private final K key;
        private V value;
        private boolean deleted;

        private Slot(K theKey, V theValue, boolean deleted){
            key= theKey;
            value= theValue;
            this.deleted= deleted;
        }


        public String toString() {
            return String.format("(key=%s, value=%s)", key, value );
        }
    }


//    public static void main(String[] args) throws IOException {
//       //ClosedHashing<Integer, String> myHash= new ClosedHashing<>(f->f);
////        myHash.insertOrUpdate(55, "Ana");
////        myHash.insertOrUpdate(44, "Juan");
////        myHash.insertOrUpdate(18, "Paula");
////        myHash.insertOrUpdate(19, "Lucas");
////        myHash.insertOrUpdate(21, "Sol");
////        myHash.dump();
//
//        //ClosedHashing<String, String> myHash= new ClosedHashing<>(str -> (int)str.charAt(0));
//        ClosedHashing<String, String> myHash= new ClosedHashing<>(s -> {
//            int i = 0;
//            for(int j=0; j < s.length(); j++){
//                i+= Character.getNumericValue(s.charAt(j));
//            }
//            return i;
//        });
//
//
//        String fileName = "amazon-categories30-v2.txt";
//        InputStream is = ClosedHashing.class.getClassLoader().getResourceAsStream(fileName);
//        if(is == null){
//            throw new RuntimeException("File " + fileName + " not found");
//        }
//        Reader in = new InputStreamReader(is);
//        BufferedReader br = new BufferedReader(in);
//
//        String line;
//        while ((line=br.readLine()) != null){
//            try {
//                myHash.insertOrUpdate(line.split("#")[0], line);
//            }
//            catch (Exception e){
//                System.out.println("repetido");
//            }
//
//        }
//        myHash.dump();
//    }
//
}
