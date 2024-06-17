package ar.edu.itba.eda.Levenshtein;

public class LevenshteinOperations {

    public static int distance(String str1, String str2) {
        int[][] matrix = new int[str1.length()+1][str2.length()+1];
        for (int i = 0; i < str1.length()+1; i++){
            for (int j = 0; j < str2.length()+1; j++){
                if (i == 0 || j == 0)
                    matrix[i][j] = (i == 0? j : i);
                else{
                    int a = matrix[i-1][j]+1;
                    int b = matrix[i][j-1]+1;
                    int c = matrix[i-1][j-1];
                    if (str1.charAt(i-1) != str2.charAt(j-1))
                        c++;
                    int min = a;
                    if (min > b)
                        min = b;
                    if (min > c)
                        min = c;
                    matrix[i][j] = min;
                }
            }
        }
        return matrix[str1.length()][str2.length()];
    }

    public static int getDistance(String str1, String str2) {
        int rows = str1.length()+1;
        int cols = str2.length()+1;
        int[] upperline = new int[rows];
        int[] lowerline = new int[rows];
        for (int i = 0; i < rows; i++) {
            upperline[i] = i;
        }
        for (int j = 0; j < cols; j++) {
            lowerline[j] = j;
            for (int i = 1; i < rows; i++) {
                lowerline[i] = Integer.min(lowerline[i-1] + 1, Integer.min(upperline[i]+1, upperline[i]-1 +
                        ((str1.charAt(i-1) == str1.charAt(j-1)) ? 0:1) ));
            }
            System.arraycopy(lowerline, 0, upperline,0, rows);
        }
        return lowerline[rows-1];
    }

    public static double normalizedSimilarity(String s1, String s2){
        int max = Math.max(s1.length(), s2.length());
        return 1 - ((double)LevenshteinOperations.distance(s1, s2)/max);
    }

    public static void main(String[] args) {
        String p1= "exkusa";
        String p2= "ex-amigo";

        System.out.println(LevenshteinOperations.distance(p1,p2));

        System.out.println(String.format("las operaciones a realizar para transformar \"%s\" en \"%s\" son:", p1, p2 ) );
        System.out.println();
    }

}