package ar.edu.itba.eda.Soundex;

public class Soundex {

    final static int[] coding = {0, 1, 2, 3, 0, 1, 2, 0, 0, 2, 2, 4, 5, 5, 0, 1, 2, 6, 2, 3, 0, 1, 0, 2, 0, 2};
    public static int getMapping(char a) {
        return coding[a - 'A'];
    }

    public static char toChar(int a) {
        return (char) (a + '0');
    }

    public static String representation(String s) {
        s = s.toUpperCase();
        char[] IN = s.toCharArray();
        char[] OUT = {'0', '0', '0', '0'};
        OUT[0] = IN[0];

        int idx = 1;
        int curr, last = getMapping(IN[0]);

        for(int i = 1; i < IN.length && idx < 4; i++) {
            curr = getMapping(IN[i]);
            if(curr != 0 && curr != last) {
                OUT[idx++] = toChar(curr);
            }
            last = curr;
        }
        return new String(OUT);
    }

    public static double similarity(String a, String b) {
        char[] soundexA = representation(a).toCharArray();
        char[] soundexB = representation(b).toCharArray();

        int count = 0;
        for(int i = 0; i < soundexA.length; i++) {
            if(soundexA[i] == soundexB[i]) {
                count++;
            }
        }

        return (double) count / 4.0;
    }
}
