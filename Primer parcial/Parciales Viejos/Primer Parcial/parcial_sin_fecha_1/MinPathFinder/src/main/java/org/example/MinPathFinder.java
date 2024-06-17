package org.example;

public class MinPathFinder {
    public int getMinPath(int [][] matrix){

        if(matrix.length == 0 || matrix[0].length == 0){
            throw new IllegalArgumentException();
        }
        int rows = matrix.length;
        int cols = matrix[0].length;

        int [][] aux = new int[matrix.length][matrix[0].length];
        aux[0][0] = matrix[0][0];
        for(int i=1; i<rows ; i++){
            aux[i][0] = matrix[i][0] + aux[i-1][0];
        }
        for(int j=1; j<cols;j++){
            aux[0][j] = matrix[0][j]+aux[0][j-1];
        }

        for(int i=1; i<rows;i++){
            for (int j=1; j<cols;j++){
                aux[i][j] = Math.min(aux[i-1][j], aux[i][j-1]) + matrix[i][j];
            }
        }

//        for(int i=0; i<rows; i++){
//            for(int j=0; j<cols; j++){
//                System.out.print(aux[i][j]+ " " );
//            }
//            System.out.println();
//        }

        return aux[rows-1][cols-1];
    }

    // Un levenshtein pero solo el de arriba o la izquierda cuentan

    /*
    ej:

    m inicial:
    2   8   4
    3   6   7
    10  7   19
    m aux:
    2   10  14
    5   11  18
    15  18  37

     */


}
