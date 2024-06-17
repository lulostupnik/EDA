package ar.edu.itba.eda.Levenshtein;

import java.util.ArrayList;
import java.util.Collections;

public class Levenshtein {

    private String str1;
    private String str2;

    private int[][] matrix_;


    public Levenshtein(String str1, String str2) {
        this.str1 = str1;
        this.str2 = str2;
        matrix_ = new int[str1.length()+1][str2.length()+1];
    }

    //Para llevar el str1 al str2: Horizontal es 'I' y Vertical 'D'
    //Para llevar el str2 al str1: Horizontal es 'D' y Vertical 'I'
    public int distance() {
        int[][] matrix = new int[str1.length()+1][str2.length()+1];
        for (int i = 0; i < str1.length()+1; i++){
            for (int j = 0; j < str2.length()+1; j++){
                if (i == 0 || j == 0)
                    matrix[i][j] = (i == 0? j : i);
                else{

                    int a = matrix[i-1][j]+1;
                    int b = matrix[i][j-1]+1;
                    int c = matrix[i-1][j-1] + (str1.charAt(i-1) != str2.charAt(j-1)?1:0);
                    matrix[i][j] = Math.min(a,Math.min(b,c));
                }
            }
        }
        for(int i = 0;i<str1.length()+1;i++){
            for(int j=0;j<str2.length()+1;j++){
                System.out.printf("%d ",matrix[i][j]);
            }
            System.out.println();
        }

        this.matrix_ = matrix;
        int aux = str1.length();
        int aux2 = str2.length();
        this.str1 = null;
        this.str2 = null;
        return matrix[aux][aux2];
    }

    public ArrayList<Character> getOperations(){
        int i = matrix_.length-1;
        int j = matrix_[0].length-1;
        int izq,arr,diag,actual;
        ArrayList<Character> toReturn = new ArrayList<>();
        while (i > 0 && j > 0){
            izq = matrix_[i][j-1];
            arr = matrix_[i-1][j];
            diag = matrix_[i-1][j-1];
            actual = matrix_[i][j];
            if (diag < izq && diag < arr) {
                if(actual == diag) toReturn.add('_');
                else toReturn.add('S');
                i--;
                j--;
            } else if (izq < arr) {
                toReturn.add('I');
                j--;
            } else {
                toReturn.add('D');
                i--;
            }
        }
        while (i > 0){
            toReturn.add('D');
            i--;
        }
        while (j > 0){
            toReturn.add('I');
            j--;
        }
        Collections.reverse(toReturn);
        return toReturn;
    }


    public static void main(String[] args) {
        String p1= "abc";  //exkusa
        String p2= "abcd";  // ex-amigo

        Levenshtein l= new Levenshtein(p1, p2);
        l.distance();
        System.out.println(String.format("las operaciones a realizar para transformar \"%s\" en \"%s\" son:", p1, p2 ) );
        System.out.println( l.getOperations() );
    }


}
