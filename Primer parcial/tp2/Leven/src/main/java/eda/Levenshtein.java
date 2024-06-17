package eda;

public class Levenshtein {
    public static int distance(String s1, String s2){
        int n = s1.length()+1;
        int m = s2.length()+1;
        int[][] matrix = LevenshteinMatrix.getMatrix(s1,s2);

        return matrix[n-1][m-1];
    }
    public static float normalized(String s1, String s2){

        float max = s1.length() > s2.length() ? s1.length():s2.length();

        return 1 - Levenshtein.distance(s1,s2) / max;
    }


}
