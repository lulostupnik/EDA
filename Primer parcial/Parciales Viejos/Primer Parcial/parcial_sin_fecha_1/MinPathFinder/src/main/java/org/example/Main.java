package org.example;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        int[][] v = new int [][]
                {{2, 8, 32, 30},
                        {12, 6, 18, 19},
                        {1, 2, 4, 8}};
        MinPathFinder minPathFinder = new MinPathFinder();
        int ans = minPathFinder.getMinPath(v);
        System.out.println(ans);
    }
}
