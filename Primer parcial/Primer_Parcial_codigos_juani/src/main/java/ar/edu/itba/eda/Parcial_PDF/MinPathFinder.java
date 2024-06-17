package ar.edu.itba.eda.Parcial_PDF;

public class MinPathFinder {

    int findMinPath(int[][] weightMatrix){
        int[][] min = new int[weightMatrix.length][weightMatrix[0].length];
        min[0][0]= weightMatrix[0][0];
        for(int i=1;i<weightMatrix[0].length;i++) min[0][i] = min[0][i-1] + weightMatrix[0][i];
        for(int i=1;i<weightMatrix.length;i++){
            for (int j=0;j<weightMatrix[0].length;j++){
                if(j == 0) min[i][j] = min[i-1][j] + weightMatrix[i][j];
                else min[i][j] = weightMatrix[i][j] + Math.min(min[i][j-1],min[i-1][j]);
            }
        }
        return min[weightMatrix.length-1][weightMatrix[0].length-1];
    }


    public static void main(String[] args) {
        /*
        int[][] v = new int [][]
                {{2, 8, 32, 30},
                        {12, 6, 18, 19},
                        {1, 2, 4, 8},
                        {1, 31, 1, 16}};
        MinPathFinder minPathFinder = new MinPathFinder();
        int ans = minPathFinder.findMinPath(v);
        System.out.println(ans);

         */
        int [][] v = new int [][]
                {{2, 8, 32, 30},
                        {12, 6, 18, 19},
                        {1, 2, 4, 8}};
        MinPathFinder minPathFinder = new MinPathFinder();
        int ans = minPathFinder.findMinPath(v);
        System.out.println(ans);
    }

}
