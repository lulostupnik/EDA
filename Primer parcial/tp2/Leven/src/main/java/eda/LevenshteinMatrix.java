package eda;



/*
    Procedimiento:



 */
public class LevenshteinMatrix {
    public static int[][] getMatrix(String s1, String s2){
        int n = s1.length()+1;
        int m = s2.length()+1;
        int[][] matrix = getStartingMatrix(n, m);
        fillMatrix(matrix, s1,s2);
        return matrix;
    }
    public static int getMatrixRows(String s1, String s2){
        return s1.length()+1;
    }
    public static int getMatrixCols(String s1, String s2){
        return s2.length()+1;
    }

    private static int[][] getStartingMatrix(int n, int m){
        int[][] matrix = new int[n][m];
        for(int i=0; i<n; i++){
            matrix[i][0] = i;
        }
        for(int j=0; j<m; j++){
            matrix[0][j] = j;
        }
        return matrix;
    }
    private static void fillMatrix(int[][] matrix, String s1, String s2){
        for(int i=1; i<s1.length() + 1 ; i++){
            for(int j=1; j<s2.length() + 1 ; j++){
                matrix[i][j] = getValue(matrix, s1, s2, i, j);
            }
        }
    }

    private static int getValue(int[][] matrix, String s1, String s2, int i, int j){
        if(i==0 || j==0){
            throw new IllegalArgumentException();
        }
        int diagonal = matrix[i-1][j-1] + isEqual(s1, s2, i, j);
        return lower(diagonal, matrix[i-1][j] + 1, matrix[i][j-1]+1);
    }

    private static int lower(int x, int y, int z){
        return x<y ? (x<z ? x:z):(y<z ? y:z);
    }
    private static int isEqual(String s1, String s2, int i, int j) {
        return (s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1;
    }

    public static void printMatrix(int[][] matrix, int n, int m){
        for(int i=0; i<n; i++){
            for (int j=0; j<m;j++){
                System.out.print(matrix[i][j]);
                if(j != m-1){
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

}
