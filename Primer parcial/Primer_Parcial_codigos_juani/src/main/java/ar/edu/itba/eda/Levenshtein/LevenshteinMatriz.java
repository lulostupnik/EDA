package ar.edu.itba.eda.Levenshtein;

public class LevenshteinMatriz {

    private static final int INF = 1000000;
    public static int distance(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];

        char[] a = s1.toCharArray();
        char[] b = s2.toCharArray();

        for(int i = 0; i < a.length + 1; i++) {
            for(int j = 0; j < b.length + 1; j++) {
                dp[i][j] = INF;
            }
        }
        dp[0][0] = 0;

        for(int i = 0; i < a.length + 1; i++) {
            for(int j = 0; j < b.length + 1; j++) {
                if(i - 1 >= 0) {
                    dp[i][j] = Math.min(dp[i][j], dp[i-1][j] + 1);
                }
                if(j - 1 >= 0) {
                    dp[i][j] = Math.min(dp[i][j], dp[i][j-1] + 1);
                }
                if(i > 0 && j > 0) {
                    dp[i][j] = Math.min(dp[i][j], dp[i-1][j-1]+(a[i-1] == b[j-1] ? 0 : 1));
                }
            }
        }
        for(int i = 0;i<s1.length()+1;i++){
            for(int j=0;j<s2.length()+1;j++){
                System.out.printf("%d ",dp[i][j]);
            }
            System.out.println();
        }
        return dp[a.length][b.length];
    }

    public static double normalizedSimilarity(String s1, String s2) {
        return 1.0 - (double) LevenshteinMatriz.distance(s1, s2) / Math.max(s1.length(), s2.length());
    }

    public static void main(String[] args) {
        System.out.println(LevenshteinMatriz.distance("exkusa","ex-amigo"));
    }



}