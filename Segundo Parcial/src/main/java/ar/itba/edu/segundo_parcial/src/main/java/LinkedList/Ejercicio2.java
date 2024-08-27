package LinkedList;//package ejer2;
//
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//
//
//public class MultiSortedLinkedList<T extends Comparable<? super T>> implements MultiListService<T> {
//    private SuperNode root;
//    private Map<String, SuperNode> listRoots = new HashMap<>();
//
//
//
//    @Override
//    public void insert(String listName, SortedListService<T> list ) {
//        // Validacion
//        Iterator<T> iterator = list.iterator();     // Iterador de la lista
//        if(!iterator.hasNext()) {                   // Verifico que no este vacio.
//            return;
//        }
//
//        if(root == null) {          // Verifico que sea el primer elemento en la lista
//            SuperNode auxFirstNode = new SuperNode(iterator.next(), listName,null,null);
//            root = auxFirstNode;       // Piso el primer nodo con el root
//            listRoots.put(listName, auxFirstNode); // Agrego al mapa
//        }
//
//        SuperNode globalRoot = root;    // Guaro el root para no modificarlo por las dudas
//        if(!listRoots.containsKey(listName)) {  // Chequeo que exista la lista
//            SuperNode auxFirstNode = new SuperNode(iterator.next(),listName,null,null);
//            listRoots.put(listName, auxFirstNode);
//            globalRoot = insertSuperNode(auxFirstNode, globalRoot);   // Lo agrego a la lista grande
//        }
//        else {
//            throw new IllegalArgumentException();
//        }
//
//        SuperNode localRoot = listRoots.get(listName);
//        while(iterator.hasNext()) {     // Hasta el final del iterador, voy crenado e insertando nodos
//            SuperNode myNode = new SuperNode(iterator.next(), listName, null, null);
//            globalRoot = insertSuperNode(myNode, globalRoot);
//            localRoot = insertNode(myNode, localRoot);
//        }
//
//        listRoots.put(listName,localRoot);
//        root = globalRoot;
//    }
//
//
//    private SuperNode insertSuperNode(SuperNode elem, SuperNode globalRoot){
//        SuperNode aux = null;
//        SuperNode current = globalRoot;
//
//        while (current != null && current.data.compareTo(elem.data) < 0) {
//            aux = current;
//            current = current.nextInML;
//        }
//
//        elem.nextInML = current;
//        if (current == globalRoot) {
//            globalRoot = elem;
//        } else {
//            aux.nextInML = elem;
//        }
//        return globalRoot;
//    }
//
//
//    private SuperNode insertNode(SuperNode node, SuperNode localRoot){
//        SuperNode aux = null;
//        SuperNode current = localRoot;
//
//        while((current != null) && (current.data.compareTo(node.data) <= 0)) {
//            aux = current;
//            current = current.nextInList;
//        }
//
//        node.nextInList = current;
//        if (current == localRoot) {
//            localRoot = node;
//        }
//        else {
//            aux.nextInList = node;
//        }
//        return localRoot;
//    }
//
//
//
//
//    private final class SuperNode {
//        private T data;
//        private String listName;
//        private SuperNode nextInML;
//        private SuperNode nextInList;
//
//        private SuperNode(T data) {
//            this.data= data;
//        }
//
//        private SuperNode(T data, String lName, SuperNode theNextInML, SuperNode theNextInlist) {
//            this.data= data;
//            this.listName = lName;
//            this.nextInML = theNextInML;
//            this.nextInList= theNextInlist;
//        }
//
//        public String toString() {
//            return "List: [" +  listName + "] Data: " + data.toString();
//        }
//
//    }
//
//    public void dump() {
//        SuperNode current = root;
//        while( current != null ){
//            System.out.println( current );
//            current = current.nextInML;
//        }
//    }
//
//    public void dump(String listName) {
//        SuperNode current = listRoots.get( listName );
//        while( current != null ){
//            System.out.println( current );
//            current = current.nextInList;
//        }
//    }
//
//}