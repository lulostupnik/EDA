package core.Q1_2023.ej1;

import java.util.ArrayList;
import java.util.List;

/*
**  Obs: se podria haber implementado sin el getMatrixLenghtRec, y solo usando una variable static al inicializar la matrix.
*        Lo hago asi para practicar recurrencia.
 */


public class Quadtree {
    private QTNode root;

     //private int m_l;

    public Quadtree(Integer[][] matrix) {
        if (!checkDimIsSquareAndEven(matrix))
            throw new RuntimeException("Invalid Dim");
       // m_l = matrix.length;
        root = constructorHelper(matrix, matrix.length, 0, 0);

    }

    private QTNode constructorHelper(Integer[][] matrix, int dim, int start_i, int start_j) {
        QTNode ans = new QTNode();

        if (dim == 0) {
            return null;
        }

        if (isOneNode(matrix, dim, start_i, start_j)) {
            ans.data = matrix[start_i][start_j];
            ans.dimension = dim;
        } else {
            dim = dim / 2;
            ans.upperLeft = constructorHelper(matrix, dim, start_i, start_j);
            ans.upperRight = constructorHelper(matrix, dim, start_i, start_j + dim);
            ans.lowerLeft = constructorHelper(matrix, dim, start_i + dim, start_j);
            ans.lowerRight = constructorHelper(matrix, dim, start_i + dim, start_j + dim);

        }
        return ans;
    }

    private void validateIndex(int index, Integer[][] matrix) {
        if (index >= matrix.length) {
            throw new IllegalArgumentException();
        }
    }

    private void validateRow(int index, Integer[][] matrix) {
        if (matrix[index] == null) {
            throw new IllegalArgumentException();
        }
    }

    private boolean isOneNode(Integer[][] matrix, int dim, int start_i, int start_j) {
        if (dim <= 0 || matrix == null) {
            throw new IllegalArgumentException();
        }
        int first = matrix[start_i][start_j];
        for (int i = start_i; i < dim + start_i; i++) {
            validateIndex(i, matrix);
            validateRow(i, matrix);
            for (int j = start_j; j < start_j + dim; j++) {
                validateIndex(j, matrix);
                if (matrix[i][j] != first) {
                    return false;
                }
            }
        }
        return true;
    }


    private int getMatrixLenghtRec(QTNode node) {
        if (node == null) {
            return 0;
        }
        if(node.data != null){
            return node.dimension;
        }
        return (getMatrixLenghtRec(node.lowerLeft) + getMatrixLenghtRec(node.lowerRight));
    }

    private int getMatrixLenght() {
        //return m_l;
        return getMatrixLenghtRec(root);
    }


    private int addToMatrix(int start_i, int start_j, QTNode node, Integer[][] matrix){
        if (node == null) {
            return 0;
        }
        if (node.data == null) {
            int added_dim = 0;
            added_dim = addToMatrix(start_i, start_j, node.upperLeft, matrix);
            addToMatrix(start_i, start_j + added_dim, node.upperRight, matrix);
            addToMatrix(start_i + added_dim, start_j, node.lowerLeft, matrix);
            addToMatrix(start_i + added_dim, start_j + added_dim, node.lowerRight, matrix);

            return added_dim*2; // @TODO xq por dos??
        }
        int i=0 ,j = 0;
        for (i = start_i; i < start_i + node.dimension; i++) {
           validateIndex(i, matrix);
           validateRow(i, matrix);
            for (j = start_j; j < start_j + node.dimension; j++) {
                validateIndex(j, matrix);
                matrix[i][j] = node.data;
            }
        }
       return node.dimension;
    }

    public Integer[][] getMatrix() {
        int matrix_lenght = getMatrixLenght();
        Integer[][] ans = new Integer[matrix_lenght][matrix_lenght];
        addToMatrix(0, 0, root, ans);
        return ans;

    }
    private static boolean checkDimIsSquareAndEven(Integer[][] matrix) {
        if (matrix == null)
            return false;

        int dim = matrix.length;

        // es par?
        if (dim % 2 == 1)
            return false;

        // todas las filas tienen la misma dimension?
        for (int rec = 0; rec < dim; rec++) {
            if (matrix[rec].length != dim)
                return false;
        }
        return true;
    }


    public String toString() {
        Integer[][] m = getMatrix();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < m.length; ++i) {
            for (int j = 0; j < m.length; ++j) {
                sb.append(m[i][j]);
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }


    private class QTNode {
        private Integer data;
        private int dimension;

        public int getDim() {
            return dimension;
        }


        private QTNode upperLeft;
        private QTNode upperRight;
        private QTNode lowerLeft;
        private QTNode lowerRight;
    }

    public static void main(String[] args) {

//        // caso de uso A
        Integer[][] matrix1 = new Integer[][]{
                {1, 2},
                {3, 1}
        };

        Quadtree qt1 = new Quadtree(matrix1);
        System.out.println(qt1);

        // caso de uso B
        Integer[][] matrix2 = new Integer[][]{
                {1, 1},
                {1, 1}
        };

        Quadtree qt2 = new Quadtree(matrix2);
        System.out.println(qt2);


 //caso de uso C


        Integer[][] matrix3 = new Integer[][]{
                {1, 1, 3, 3},
                {1, 2, 3, 3},
                {3, 1, 4, 4},
                {2, 1, 4, 4}
        };

        Quadtree qt3 = new Quadtree(matrix3);
        System.out.println(qt3);


// caso de uso D
        Integer[][] matrix4 = new Integer[][]{
                {1, 1, 3, 3, 8, 8, 8, 8},
                {1, 1, 3, 3, 8, 8, 8, 8},
                {3, 1, 4, 4, 8, 8, 8, 8},
                {2, 1, 4, 4, 8, 8, 8, 8},
                {1, 1, 1, 1, 7, 7, 7, 7},
                {1, 1, 1, 1, 7, 7, 7, 7},
                {1, 1, 1, 1, 7, 7, 7, 7},
                {1, 1, 1, 1, 7, 7, 7, 7},
        };

        Quadtree qt4 = new Quadtree(matrix4);
        System.out.println(qt4);

    }


}
