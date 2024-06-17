package org.example;

import java.util.ArrayList;
public class Levenshtein {
    private int[][] matrix;
    private char[] horizontalWord;
    private char[] verticalWord;
    public Levenshtein(char[] horizontalWord, char[] verticalWord) {
        this.horizontalWord= horizontalWord;
        this.verticalWord= verticalWord;
        matrix = new int[ verticalWord.length+1][ horizontalWord.length + 1];
        for(int col = 1; col <= horizontalWord.length; col++)
        {
            matrix[0][col] = col;
        }
        for(int row = 1; row <= verticalWord.length; row++)
        {
            matrix[row][0] = row;
        }
    }
    public int distance() {
        // no se ha calculado antes
        if (verticalWord != null) {
            // calcular
            for(int row=1; row<= verticalWord.length; row++)
            {
                for(int col=1; col<= horizontalWord.length; col++) {
                    matrix[row][col] = Math.min(matrix[row-1][col-1] + (( verticalWord[row-1]== horizontalWord[col-1])?0:1) ,
                            Math.min( matrix[row-1][col]+1, matrix[row][col-1]+1 ));
                }
            }
            // no las necesito mas. Las destruyo
            verticalWord= null;
            horizontalWord= null;
        }
        return matrix [ matrix.length-1][ matrix[0].length-1] ;
    }
    public ArrayList<Character> getOperations() {
        if(matrix == null || matrix[0] == null){
            throw new IllegalArgumentException();
        }
        distance();
        int rows = matrix.length-1;
        int cols = matrix[0].length-1;
        //En el caso que el que vengo es el minimo:
        //izq: borro
        //arriba: insert
        //arriba izq: subs
        //mismo num q la diagonal: dejo igual
        ArrayList<Character> ansInverted = new ArrayList<>();
        while(rows >= 1 && cols >= 1){
            int min = Math.min(Math.min(matrix[rows-1][cols-1],matrix[rows][cols-1]), matrix[rows-1][cols]);
            if(min == matrix[rows-1][cols-1]){
                if(min == matrix[rows][cols]){
                    ansInverted.add('-');
                }else{
                    ansInverted.add('S');
                }
                rows--;
                cols--;
            }else if(min == matrix[rows-1][cols]){
                ansInverted.add('I');
                rows--;
            }else if (min == matrix[rows][cols-1]){
                ansInverted.add('D');
                cols--;
            }
        }

        ArrayList<Character> ans = new ArrayList<>();
        int j=0;
        for(int i=ansInverted.size()-1; i>= 0; i--){
            ans.add(ansInverted.get(i));
        }
        return ans;
    }
    public static void main(String[] args) {
        String p1;
        String p2;
        Levenshtein l;
        p1= "exkusa";
        p2= "ex-amigo";
        l = new Levenshtein(p1.toCharArray(), p2.toCharArray());
        System.out.println(String.format("las operaciones a realizar para transformar \"%s\" en \"%s\" son:", p1, p2 ) );
        System.out.println( l.getOperations() );
    }
}
