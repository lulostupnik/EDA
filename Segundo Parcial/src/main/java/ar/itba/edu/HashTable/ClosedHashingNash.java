package ar.itba.edu.HashTable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Function;

public class ClosedHashingNash<K, V> implements IndexParametricService<K, V> {

    private final int initialLookupSize = 10;
    private final double threshold = 0.75;
    private int usedKeys = 0;
    private int size = initialLookupSize;

    // estática. No crece. Espacio suficiente...
    @SuppressWarnings({"unchecked"})
    private Slot<K,V>[] Lookup = (Slot<K,V>[]) new Slot[initialLookupSize];

    private Function<? super K, Integer> prehash;

    public ClosedHashingNash(Function<? super K, Integer> mappingFn) {
        if (mappingFn == null)
            throw new RuntimeException("function not provided");

        prehash = mappingFn;
    }

    // ajuste al tamaño de la tabla
    private int hash(K key) {
        if (key == null)
            throw new IllegalArgumentException("key cannot be null");

        return prehash.apply(key) % size;
    }

    private void checkData(K key, V data){
        if (key == null || data == null) {
            String msg= String.format("inserting or updating (%s,%s). ", key, data);
            if (key==null)
                msg+= "Key cannot be null. ";

            if (data==null)
                msg+= "Data cannot be null.";

            throw new IllegalArgumentException(msg);
        }
        return;
    }

    @SuppressWarnings("unchecked")
    private void checkSpace(){
        if(((double) (usedKeys + 1) / size) >= threshold){
            size += initialLookupSize;
            Slot<K,V>[] aux = (Slot<K,V>[]) new Slot[size];
            for(int i = 0; i < Lookup.length; i++){
                if(Lookup[i] != null){
                    //Revisar
                    aux[hash(Lookup[i].key)] = new Slot<K,V>(Lookup[i].key, Lookup[i].value);
                }
            }
            Lookup = aux;
        }
    }

    public void insertOrUpdate(K key, V data) {
        checkData(key, data);

        checkSpace();

        int newKey = hash(key);
        if(Lookup[newKey] != null){
            if(Lookup[newKey].key.equals(key))
                Lookup[newKey].value = data;
            else
                throw new RuntimeException("Colision in key: %d".formatted(newKey));

        }else {
            Lookup[hash(key)] = new Slot<K, V>(key, data);
            usedKeys++;
        }
    }


    public void insertOrUpdateClosedHashingNash(K key, V data) {
        checkData(key, data);
        checkSpace();

        int newKey = hash(key);
        int length = Lookup.length;	//Para no hacerlo tantas veces
        if(Lookup[newKey] != null){
            int i, firstPos = -1;
            boolean set = false;
            for(i = newKey; Lookup[i] != null && !set; i++){
                if(i >= length){
                    i = i % length;
                }
                if(Lookup[i].key.equals(key)) {
                    Lookup[i].value = data;
                    Lookup[i].setOccupied();
                    set = true;
                }else{
                    if(firstPos == -1){
                        firstPos = i;
                    }
                }
            }
            if(!set){
                Lookup[i] = new Slot<K, V>(key, data);
            }
        }else {
            Lookup[hash(key)] = new Slot<K, V>(key, data);
            usedKeys++;
        }
    }

    // find or get
    public V find(K key) {
        if (key == null)
            return null;

        Slot<K, V> entry = Lookup[hash(key)];
        if (entry == null)
            return null;

        return entry.value;
    }

    public boolean remove(K key) {
        if (key == null)
            return false;

        // lo encontre?
        if (Lookup[hash(key)] == null)
            return false;

        Lookup[hash(key)] = null;
        usedKeys--;
        return true;
    }

    public boolean removeClosedHashingNash(K key) {
        if (key == null)
            return false;

        // lo encontre?
        int i = hash(key);
        int length = Lookup.length;
        if (Lookup[i] == null)
            return false;

        boolean deleted = false;
        while(Lookup[i] != null && !deleted){
            if(i >= length){
                i = i % length;
            }
            if(Lookup[i].key.equals(key)) {
                if (Lookup[(i + 1) % length] != null) {
                    Lookup[i].setNotOccupied();
                } else {
                    Lookup[i] = null;
                }
                usedKeys--;
                deleted = true;
            }
            System.out.println(Lookup[i]);
            i++;
        }
        return deleted;
    }

    public void dump()  {
        for(int rec = 0; rec < size; rec++) {
            if (Lookup[rec] == null )
                System.out.println(String.format("slot %d is empty", rec));
            else if(!Lookup[rec].isOccupied) {
                System.out.println(String.format("slot %d is logically empty", rec));
            } else
                System.out.println(String.format("slot %d contains %s",rec, Lookup[rec]));
        }
    }

    public int size() {
        // todavia no esta implementado
        return 0;
    }

    static private final class Slot<K, V>	{
        private final K key;
        private V value;
        private boolean isOccupied;

        private Slot(K theKey, V theValue){
            key= theKey;
            value= theValue;
            isOccupied = true;
        }

        public String toString() {
            return String.format("(key=%s, value=%s)", key, value );
        }

        public void setOccupied(){
            this.isOccupied = true;
        }

        public void setNotOccupied(){
            this.isOccupied = false;
        }
    }


    public static void main(String[] args) throws IOException {
		/* Primer Test
		ClosedHashingNash<Integer, String> myHash= new ClosedHashingNash<>(f->f);
		myHash.insertOrUpdate(55, "Ana");
		myHash.insertOrUpdate(44, "Juan");
		myHash.insertOrUpdate(18, "Paula");
		myHash.insertOrUpdate(19, "Lucas");
		myHash.insertOrUpdate(21, "Sol");
		//myTest
		myHash.insertOrUpdate(33, "Guido");
		myHash.insertOrUpdate(22, "Edwin");
		myHash.insertOrUpdate(6, "Zaid");
		myHash.dump();
		myHash.insertOrUpdate(15, "Eros");
		 */

        // Test ClosedHashingNash --> Falta testear cuando hace resize
        ClosedHashingNash<Integer, String> myHash= new ClosedHashingNash<>(f->f);
        myHash.insertOrUpdateClosedHashingNash(3, "Dick");
        myHash.insertOrUpdateClosedHashingNash(23, "Joe");
        myHash.insertOrUpdateClosedHashingNash(4, "Sue");
        myHash.insertOrUpdateClosedHashingNash(15, "Meg");
        myHash.insertOrUpdateClosedHashingNash(6, "Dick");
        myHash.insertOrUpdateClosedHashingNash(7, "Joe");
        myHash.insertOrUpdateClosedHashingNash(8, "Sue");
        myHash.insertOrUpdateClosedHashingNash(15, "Meg");
        //myHash.dump();
        myHash.removeClosedHashingNash(23);
        myHash.removeClosedHashingNash(15);
        myHash.dump();
        // Este caso no anda Bien


		/*
		//Con strings como clave
		//FALTA VER COLISIONES PARA QUE FUNCIONE ESTO
		System.out.println("STRING COMO CLAVE");

		Function<String, Integer> function = s -> {
			int sum = 0;
			for (int i = 0; i < s.length(); i++) {
				sum += (int) s.charAt(i); // Sumar el valor ASCII de cada carácter
			}
			return sum;
		};
		ClosedHashingNash<String, String> myHash2= new ClosedHashingNash<>(function);

		String fileName = "amazon-categories30.txt";
		InputStream is = ClosedHashingNash.class.getClassLoader().getResourceAsStream(fileName);

		InputStreamReader in = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(in);
		String line;
		while(((line = br.readLine()) != null)){
			String[] partes = line.split("#");
			//CAMBIAR
			String title = partes[0].trim(); // Parte antes de "#"
			String value = partes[1].trim(); // Parte después de "#"
			myHash2.insertOrUpdate(title, value);
		}
		myHash2.dump();
		 */
    }
	
/*	
	public static void main(String[] args) {
		ClosedHashingNash<Integer, String> myHash= new ClosedHashingNash<>(f->f);
		myHash.insertOrUpdate(55, "Ana");
		myHash.insertOrUpdate(29, "Victor");
		myHash.insertOrUpdate(25, "Tomas");
		myHash.insertOrUpdate(19, "Lucas");
		myHash.insertOrUpdate(21, "Sol");
		myHash.dump();
	}
*/
}