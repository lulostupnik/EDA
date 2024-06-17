package org.example;

public class Main {
    public static void main(String[] args) {
        IndexWithDuplicates<Integer> idx = new IndexWithDuplicates<>(Integer.class);
        idx.initialize( new Integer[] {100, 50, 30, 50, 80, 10, 100, 30, 20, 138} );
        SimpleLinkedList<Integer> repeatedLst = new SimpleLinkedList();
        SimpleLinkedList<Integer> singleLst = new SimpleLinkedList();
        SimpleLinkedList<Integer> notIndexedLst = new SimpleLinkedList();
        idx.repeatedValues( new Integer[] { 10, 80, 10, 35, 80, 80 , 1111},
                repeatedLst, singleLst, notIndexedLst );
        System.out.println("Repeated Values");
        repeatedLst.dump();
        System.out.println("Single Values");
        singleLst.dump();
        System.out.println("Non Indexed Values");
        notIndexedLst.dump();
    }
}
