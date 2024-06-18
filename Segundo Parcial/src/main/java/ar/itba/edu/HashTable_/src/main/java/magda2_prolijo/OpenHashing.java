package magda2_prolijo;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Function;

public class OpenHashing<K, V> implements IndexParametricService<K, V> {
    final private int initialLookupSize = 10;
    final private double threshold = 0.75;
    private int size = 0;

    // est�tica. No crece. Espacio suficiente...
    @SuppressWarnings({"unchecked"})
    private LinkedList<Slot<K, V>>[] lookup = new LinkedList[initialLookupSize];

    private Function<? super K, Integer> prehash;

    public OpenHashing(Function<? super K, Integer> mappingFn) {
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
    @Override
    public V find(K key) {
        if (key == null)
            return null;

        int i = hash(key);

        for (Slot<K, V> slot : lookup[i % lookup.length]) {
            if (slot.key.equals(key))
                return slot.value;
        }

        return null;
    }

    @SuppressWarnings({"unchecked"})
    private void grow() {
        LinkedList<Slot<K,V>>[] aux = lookup;
        lookup = new LinkedList[aux.length*2]; // Duplico el tamaño
        for(int i=0 ; i < aux.length; i++) {
            if(aux[i] != null) {
                for (int j = 0; j < aux[i].size(); j++) {
                    insertOrUpdate(aux[i].get(j).key, aux[i].get(j).value);
                }
            }
        }
    }

    @SuppressWarnings({"unchecked"})
    public void insertOrUpdate(K key, V data) {
        if (key == null || data == null) {
            String msg = String.format("inserting or updating (%s,%s). ", key, data);
            if (key == null)
                msg += "Key cannot be null. ";

            if (data == null)
                msg += "Data cannot be null.";

            throw new IllegalArgumentException(msg);
        }
        if ((double) size / lookup.length > threshold)
            grow();

        int i = hash(key);
        if (lookup[i] == null)
            lookup[i] = new LinkedList<>(); // si no hay lista, creo una
        Slot<K, V> slot = getSlot(key); // busco si ya estaba el nodo
        if (slot != null) {
            slot.value = data; // si estaba, actualizo el valor
        } else {
            lookup[i].addLast(new Slot<>(key, data)); // si no estaba, lo agrego
            size++;
        }
    }
    protected Slot<K, V> getSlot(K key) {
        for (Slot<K, V> node : lookup[hash(key)]) {
            if (node.key.equals(key)) { // Si me coincide las keys, devuelvo el nodo entero
                return node;
            }
        }
        return null;
    }
    public boolean remove(K key) {
        int i = hash(key);
        if (lookup[i] == null)
            return false;
        Iterator<Slot<K,V>> iter = lookup[i].iterator(); // Iterador para recorrer la lista
        while (iter.hasNext()) {
            Slot<K,V> slot = iter.next();
            if (slot.key.equals(key)) {
                iter.remove();
                size--;
                if (lookup[i].isEmpty())
                    lookup[i] = null;
                return true;
            }
        }
        if (lookup[i].isEmpty())
            lookup[i] = null;
        return false;
    }


    public void dump() {
        for (int rec = 0; rec < lookup.length; rec++) {
            if (lookup[rec] == null)
                System.out.printf("slot %d is empty%n", rec);
            else
                System.out.printf("slot %d contains %s%n", rec, lookup[rec]);
        }
    }

    public int size() {
        return size;
    }

    static private final class Slot<K, V> {
        private final K key;
        private V value;

        private Slot(K theKey, V theValue) {
            key = theKey;
            value = theValue;
        }

        public String toString() {
            return String.format("(key=%s, value=%s)", key, value);
        }
    }

    public static void main(String[] args) {
        OpenHashing<Integer, String> hash = new OpenHashing<>(f -> f);
        hash.insertOrUpdate(2, "A");
        hash.insertOrUpdate(3, "B");
        hash.insertOrUpdate(4, "C");
        hash.insertOrUpdate(5, "D");
        hash.remove(3);
        hash.insertOrUpdate(1, "X");
        hash.insertOrUpdate(22, "P");
        hash.dump();
    }
}