package ar.edu.itba.eda.KMP;

import java.util.ArrayList;

public class KMP {
    public KMP() {

    }

    private int[] next(char[] query) {
        int[] next = new int[query.length];

        int border = 0;

        int rec = 1;
        while(rec < query.length) {
            if(query[rec] != query[border]) {
                if(border != 0) {
                    border = next[border-1];
                } else {
                    next[rec++] = 0;
                }
            } else {
                border++;
                next[rec] = border;
                rec++;
            }
        }
        return next;
    }

    public int indexOf(char[] target, char[] query) {
        int[] next = next(query);

        int rec = 0;
        int pquery = 0;

        while(rec < target.length) {
            if(pquery == query.length) {
                break;
            }
            if(target[rec] == query[pquery]) {           //S A B A S A B A B A
                rec++;                                   //| | | | | | | | | |
                pquery++;                                //Query A B A B
            } else {                                     //Next  0 0 1 2
                if(pquery == 0) {                        //      | | | | |
                    rec++;
                } else {
                    pquery = next[pquery - 1];
                }
            }
        }
        return (pquery == query.length ? rec - pquery : -1);
    }

    public ArrayList<Integer> findAll2(char[] target, char[] query) {
        char[] newStr = new char[target.length + query.length + 1];
        for(int i = 0; i < query.length; i++) {
            newStr[i] = query[i];
        }
        for(int i = query.length + 1; i < newStr.length; i++) {
            newStr[i] = target[i - query.length - 1];  //A B A B # S A B A S A B A B A
        }
        newStr[query.length] = '#';
        int[] next = next(newStr);
        ArrayList<Integer> ret = new ArrayList<>();

        for(int i = 0; i < newStr.length; i++) {
            if(next[i] == query.length) {
                ret.add(i - 2 * query.length);
            }
        }
        return ret;
    }

    public ArrayList<Integer> findAll(char[] target, char[] query) {
        ArrayList<Integer> toReturn = new ArrayList<>();
        int[] next = next(query);
        int rec = 0;
        int pquery = 0;

        while(rec < target.length) {
            if(pquery == query.length) {
                toReturn.add(rec - pquery);
                pquery = next[pquery - 1];
            }
            if(target[rec] == query[pquery]) {           //S A B A B A B S A
                rec++;                                   //| | | | | | | |
                pquery++;                                //Query A B A B
            } else {                                     //Next  0 0 1 2
                if(pquery == 0) {                        //      | | | | |
                    rec++;
                } else {
                    pquery = next[pquery - 1];
                }
            }
        }

        return toReturn;
    }

    public static void main(String[] args) {
        char[] target = {'S','A','B','A','B','A','B','S','A'};
        char[] query = {'A','B','A','B'};
        KMP kmp = new KMP();
        for (Integer i: kmp.findAll(target,query)) System.out.printf("%d ",i);
        System.out.println();
        for (Integer i: kmp.findAll2(target,query)) System.out.printf("%d ",i);
        System.out.println();
    }



}
