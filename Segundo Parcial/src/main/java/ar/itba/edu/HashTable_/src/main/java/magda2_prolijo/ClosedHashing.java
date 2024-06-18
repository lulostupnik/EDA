package magda2_prolijo;

import java.io.*;
import java.util.Scanner;
import java.util.function.Function;

public class ClosedHashing<K, V> implements IndexParametricService<K, V> {
    final private int initiallookupSize = 10;
    final double threshold = 0.75;
    int size = 0;

    // est�tica. No crece. Espacio suficiente...
    @SuppressWarnings({"unchecked"})
    private Slot<K, V>[] lookup = (Slot<K, V>[]) new Slot[initiallookupSize];
    private Function<? super K, Integer> prehash;

    public ClosedHashing(Function<? super K, Integer> mappingFn) {
        if (mappingFn == null)
            throw new RuntimeException("fn not provided");
        prehash = mappingFn;
    }

    // ajuste al tama�o de la tabla
    private int hash(K key) {
        if (key == null)
            throw new IllegalArgumentException("key cannot be null");
        return prehash.apply(key) % lookup.length;
    }

    public void insertOrUpdate(K key, V data) {
        if (key == null || data == null) {
            String msg = String.format("inserting or updating (%s,%s). ", key, data);
            if (key == null)
                msg += "Key cannot be null. ";
            if (data == null)
                msg += "Data cannot be null.";
            throw new IllegalArgumentException(msg);
        }

        int i = hash(key);
        int count = 0;
        int potencialInsert = -1;

        if (loadFactor()) //si tengo que agrandar
            grow(); //agrando

        while (lookup[i % lookup.length] != null) {
            if (lookup[i % lookup.length].key.equals(key)) {
                lookup[i % lookup.length].value = data;
                return;
            }
            if (lookup[i % lookup.length].status && potencialInsert == -1)
                potencialInsert = i % lookup.length;
            i++;
            count++;
        }
        if (potencialInsert != -1)
            lookup[potencialInsert] = new Slot<K, V>(key, data);
        else
            lookup[i % lookup.length] = new Slot<K, V>(key, data);
        size++;
    }

    private boolean loadFactor() {
        return (double) size / lookup.length > threshold;
    }

    @SuppressWarnings("unchecked")
    private void grow() { //agrando la tabla
        Slot<K, V>[] oldlookup = lookup; //guardo la tabla vieja
        lookup = (Slot<K, V>[]) new Slot[lookup.length * 2]; //creo una nueva tabla del doble de tamaño
        size = 0; //reseteo el tamaño
        for (int i = 0; i < oldlookup.length; i++) { //recorro la tabla vieja
            if (oldlookup[i] != null) //si el slot no es null
                insertOrUpdate(oldlookup[i].key, oldlookup[i].value); //lo inserto en la nueva tabla
        }
    }

    // find or get
    public V find(K key) {
        if (key == null)
            return null;
        Slot<K, V> entry = lookup[hash(key)];
        if (entry == null)
            return null;
        return entry.value;
    }

    public boolean remove(K key) {
        if (key == null) //si la key es null
            return false; //devuelvo false

        int i = hash(key); //guardo el hash de la key
        int count = 0;
        while (lookup[i % lookup.length] != null && count < lookup.length) { //mientras el slot no sea null (baja fisica) y no haya recorrido toda la tabla
            if (lookup[i % lookup.length].key.equals(key) && !lookup[i % lookup.length].status) { //si la key del slot es igual a la key que quiero borrar
                if (lookup[i + 1 % lookup.length] == null) //si el siguiente slot es baja fisica
                    lookup[i % lookup.length] = null; //lo borro con baja fisica
                else
                    lookup[i % lookup.length].status = true; //si no, lo marco como baja logica
                size--;
                return true;
            }
            i++;
            count++;
        }
        return false;
    }

    public void dump() {
        for (int rec = 0; rec < lookup.length; rec++) {
            if (lookup[rec] == null || lookup[rec].status)
                System.out.println(String.format("slot %d is empty : %s", rec, lookup[rec] == null ? "null" : "deleted"));
            else
                System.out.println(String.format("slot %d contains %s %s", rec, lookup[rec], lookup[rec].status ? "deleted" : ""));
        }
    }

    public int size() {
        return size;
    }

    static private final class Slot<K, V> {
        private final K key;
        private V value;
        private boolean status; // true: baja fisica, false: baja logica

        private Slot(K theKey, V theValue) {
            key = theKey;
            value = theValue;
            status = false;
        }

        public String toString() {
            return String.format("(key=%s, value=%s)", key, value);
        }
    }

    public static void main(String[] args) throws IOException {
        ClosedHashing<Integer, String> myHash = new ClosedHashing<>(f -> f);
        myHash.insertOrUpdate(55, "Ana");
        myHash.insertOrUpdate(44, "Juan");
        myHash.insertOrUpdate(18, "Paula");
        myHash.insertOrUpdate(19, "Lucas");
        myHash.insertOrUpdate(21, "Sol");
        //myHash.dump();


        ClosedHashing<Integer, String> myHash2 = new ClosedHashing<>(f -> f);
        myHash2.insertOrUpdate(55, "Ana");
        myHash2.insertOrUpdate(29, "Victor");
        myHash2.insertOrUpdate(25, "Tomas");
        myHash2.insertOrUpdate(19, "Lucas");
        myHash2.insertOrUpdate(21, "Sol");
        //myHash2.dump();

        ClosedHashing<String, String> myHash3 = new ClosedHashing<>(
                s -> {
                    int sum = 0;
                    for (int i = 0; i < s.length(); i++) {
                        sum = sum + s.charAt(i);
                    }
                    return sum;
                });

        String fileName = "amazon-categories30.txt";
        InputStream is = ClosedHashing.class.getClassLoader().getResourceAsStream(fileName);
        Reader in = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(in);
        String line;

        while ((line = br.readLine()) != null) {
            Scanner scanner = new Scanner(line).useDelimiter("#");
            String title = scanner.next();
            myHash3.insertOrUpdate(title, line);
        }
        myHash3.dump();

        ClosedHashing<Integer, String> myLookUp = new ClosedHashing<>(f -> f);
        myLookUp.insertOrUpdate(3, "Dick");
        myLookUp.insertOrUpdate(23, "Joe");
        myLookUp.insertOrUpdate(4, "Sue");
        myLookUp.insertOrUpdate(15, "Meg");
        myLookUp.remove(23);//Joe
        myLookUp.remove(15);//Meg
        myLookUp.insertOrUpdate(4, "Sue");
        myLookUp.insertOrUpdate(43, "Paul");
        myLookUp.remove(43);
        myLookUp.remove(4);
        //myLookUp.dump();
    }
}