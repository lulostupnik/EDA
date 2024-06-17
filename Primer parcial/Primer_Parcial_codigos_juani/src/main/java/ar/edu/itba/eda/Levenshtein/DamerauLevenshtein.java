package ar.edu.itba.eda.Levenshtein;

public class DamerauLevenshtein {

    private String str1;
    private String str2;


    public DamerauLevenshtein(String str1, String str2) {
        this.str1 = str1;
        this.str2 = str2;
    }
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
                    int d = a;
                    if(i >= 2 && j >= 2 && str2.charAt(j-1) == str1.charAt(i-2) && str2.charAt(j-2) == str1.charAt(i-1)) {
                        d = matrix[i - 2][j - 2] + 1;
                    }
                    matrix[i][j] = Math.min(Math.min(a,d),Math.min(b,c));
                }
            }
        }
        for(int i = 0;i<str1.length()+1;i++){
            for(int j=0;j<str2.length()+1;j++){
                System.out.printf("%d ",matrix[i][j]);
            }
            System.out.println();
        }

        return matrix[str1.length()][str2.length()];
    }

}
