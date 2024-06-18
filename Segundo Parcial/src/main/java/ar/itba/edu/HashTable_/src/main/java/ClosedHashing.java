import java.util.function.Function;

public class ClosedHashing<K, V> implements IndexParametricService<K, V> {
    final private int initialLookupSize= 10;
    final private int CHUNK_SIZE = 10;
    final private double threshoald = 0.75;
    private int size = 0;

    // estatica. No crece. Espacio suficiente...
    @SuppressWarnings({"unchecked"})
    private Slot<K,V>[] Lookup= (Slot<K,V>[]) new Slot[initialLookupSize];

    private Function<? super K, Integer> prehash;

    public ClosedHashing( Function<? super K, Integer> mappingFn) {
        if (mappingFn == null)
            throw new RuntimeException("fn not provided");

        prehash= mappingFn;
    }

    // ajuste al tama�o de la tabla
    private int hash(K key) {
        if (key == null)
            throw new IllegalArgumentException("key cannot be null");

        return prehash.apply(key) % Lookup.length;
    }


    private void insertInGenericArray(Slot<K,V> slot, Slot<K,V>[] arr){
        if(arr[hash(slot.key)] == null){
            arr[hash(slot.key)] = slot;
            return;
        }

        if(arr[hash(slot.key)].key == slot.key){
            arr[hash(slot.key)] = slot;
        }else{
            throw new IllegalArgumentException("Key already exists");
        }
    }
    private void resize(){
        Slot<K,V>[] aux= (Slot<K,V>[]) new Slot[size+CHUNK_SIZE];
        for(Slot<K,V> slot: Lookup){
            if(slot != null){
                insertInGenericArray(slot, aux);
            }
        }
        Lookup=aux;
    }

    private void checkAndResize(){
        if(Double.compare(size,Lookup.length * threshoald ) >= 0){
            resize();
        }
    }

    private void insertInArray(Slot<K,V> slot){
        checkAndResize();
        insertInGenericArray(slot, Lookup);
        size++;
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
        insertInArray(new Slot<>(key, data));
        //Lookup[ hash( key) ] = new Slot<K, V>(key, data);
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
        if (Lookup[ hash( key) ] == null)
            return false;

        Lookup[ hash( key) ] = null;
        size--;
        return true;
    }


    public void dump()  {
        for(int rec= 0; rec < Lookup.length; rec++) {
            if (Lookup[rec] == null)
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

        private Slot(K theKey, V theValue){
            key= theKey;
            value= theValue;
        }


        public String toString() {
            return String.format("(key=%s, value=%s)", key, value );
        }
    }


    public static void main(String[] args) {
        ClosedHashing<Integer, String> myHash= new ClosedHashing<>(f->f);
        myHash.insertOrUpdate(55, "Ana");
        myHash.insertOrUpdate(44, "Juan");
        myHash.insertOrUpdate(18, "Paula");
        myHash.insertOrUpdate(19, "Lucas");
        myHash.insertOrUpdate(21, "Sol");
        myHash.dump();
    }

/*
	public static void main(String[] args) {
		ClosedHashing<Integer, String> myHash= new ClosedHashing<>(f->f);
		myHash.insertOrUpdate(55, "Ana");
		myHash.insertOrUpdate(29, "Victor");
		myHash.insertOrUpdate(25, "Tomas");
		myHash.insertOrUpdate(19, "Lucas");
		myHash.insertOrUpdate(21, "Sol");
		myHash.dump();
	}
*/
}