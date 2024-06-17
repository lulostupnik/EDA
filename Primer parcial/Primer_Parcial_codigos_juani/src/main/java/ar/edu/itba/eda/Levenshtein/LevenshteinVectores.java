package ar.edu.itba.eda.Levenshtein;

import java.util.Arrays;

public class LevenshteinVectores {

    private String str1;
    private String str2;

    public LevenshteinVectores(String str1, String str2) {
        this.str1 = str1;
        this.str2 = str2;
    }

    public int distance() {
        String string1,string2;

        if(str1.length() > str2.length()){
            string1 = str1;
            string2 = str2;
        } else{
            string1 = str2;
            string2 = str1;
        }
        int[] vec1 = new int[string1.length()+1];
        int[] vec2 = new int[string1.length()+1];
        for(int i = 0;i<string1.length()+1;i++) vec1[i] = i;
        for(int i = 1;i<string2.length()+1;i++) {
            for (int j = 0; j < string1.length() + 1; j++) {
                if (j == 0) vec2[j] = vec1[j] + 1;
                else {
                    int a = vec1[j] + 1;
                    int b = vec2[j - 1] + 1;
                    int c = vec1[j - 1] + (string1.charAt(j - 1) != string2.charAt(i - 1) ? 1 : 0);
                    vec2[j] = Math.min(a, Math.min(b, c));
                }
            }
            vec1 = Arrays.copyOf(vec2,vec2.length);
        }

        return vec2[string1.length()];
    }

    public static void main(String[] args) {
        String p1= "abc";  //exkusa
        String p2= "abcd";  // ex-amigo

        LevenshteinVectores l= new LevenshteinVectores(p1, p2);
        System.out.println(l.distance());
    }




}
