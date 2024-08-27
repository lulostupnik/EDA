package LinkedList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class MultiSortedLinkedList<T extends Comparable<? super T>> implements MultiListService<T> {
    private SuperNode root;
    private Map<String, SuperNode> listRoots = new HashMap<>();

    @Override
    public void insert(String listName, SortedListService<T> list ) {

        Iterator<T> iterator = list.iterator();
        SuperNode prev = null;
        SuperNode current = root;
        SuperNode prevInList = null;
        // 4 4 8
        // 5 11
        if(!listRoots.containsKey(listName))
            while (iterator.hasNext()){
                T data = iterator.next();
                while (current!=null && current.data.compareTo(data) < 0) {
                    // avanzo
                    prev = current;
                    current = current.nextInML;
                }
                SuperNode aux = new SuperNode(data,listName,current,null);
                if(prevInList == null){
                    prevInList = aux;
                    listRoots.put(listName,aux);
                }
                else{
                    prevInList.nextInList = aux;
                    prevInList = aux;
                }
                // es el lugar para colocarlo
                if (current == root) {
                    root = aux;
                    current = root;
                }
                else {
                    prev.nextInML= aux;
                }
            }
    }


    private final class SuperNode {
        private T data;
        private String listName;
        private SuperNode nextInML;
        private SuperNode nextInList;

        private SuperNode(T data) {
            this.data= data;
        }

        private SuperNode(T data, String lName, SuperNode theNextInML, SuperNode theNextInlist) {
            this.data= data;
            this.listName = lName;
            this.nextInML = theNextInML;
            this.nextInList= theNextInlist;
        }

        public String toString() {
            return "List: [" +  listName + "] Data: " + data.toString();
        }

    }

    public void dump() {
        SuperNode current = root;
        while( current != null ){
            System.out.println( current );
            current = current.nextInML;
        }
    }

    public void dump(String listName) {
        SuperNode current = listRoots.get( listName );
        while( current != null ){
            System.out.println( current );
            current = current.nextInList;
        }
    }


    public static void main(String[] args) {
        SortedLinkedList<Integer> l1 = new SortedLinkedList<>();
        l1.insert(4);
        l1.insert(8);
        l1.insert(4);


        SortedLinkedList<Integer> l2 = new SortedLinkedList<>();
        l2.insert(5);
        l2.insert(11);

        MultiListService<Integer> ml = new MultiSortedLinkedList<>();

        ml.insert( "l1", l1);
        ml.insert( "l2", l2);
        ml.insert( "l2", l2);

        System.out.println("global");
        ml.dump();

        System.out.println("\nsolo l1");
        ml.dump("l1");

        System.out.println("\nsolo l2");
        ml.dump("l2");


    }

}