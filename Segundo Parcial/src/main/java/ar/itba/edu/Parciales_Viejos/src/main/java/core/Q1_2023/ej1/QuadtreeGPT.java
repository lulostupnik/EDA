package core.Q1_2023.ej1;

// Path: src/main/java/QuadtreeGPT.java

public class QuadtreeGPT {
    private QTNode root;

    public QuadtreeGPT(Integer[][] matrix) {
        if (!checkDimIsSquareAndEven(matrix)) {
            throw new RuntimeException("Invalid Dimension");
        }
        root = buildQuadtreeGPT(matrix, 0, 0, matrix.length);
    }

    public Integer[][] toMatrix() {
        int dimension = root.getDimension();
        Integer[][] matrix = new Integer[dimension][dimension];
        fillMatrix(root, matrix, 0, 0);
        return matrix;
    }

    private QTNode buildQuadtreeGPT(Integer[][] matrix, int startX, int startY, int dimension) {
        if (allValuesSame(matrix, startX, startY, dimension)) {
            return new QTNode(matrix[startX][startY], dimension);
        }

        int halfDim = dimension / 2;
        QTNode node = new QTNode(null, dimension);
        node.upperLeft = buildQuadtreeGPT(matrix, startX, startY, halfDim);
        node.upperRight = buildQuadtreeGPT(matrix, startX, startY + halfDim, halfDim);
        node.lowerLeft = buildQuadtreeGPT(matrix, startX + halfDim, startY, halfDim);
        node.lowerRight = buildQuadtreeGPT(matrix, startX + halfDim, startY + halfDim, halfDim);

        return node;
    }

    private void fillMatrix(QTNode node, Integer[][] matrix, int startX, int startY) {
        if (node.isLeaf()) {
            for (int i = 0; i < node.getDimension(); i++) {
                for (int j = 0; j < node.getDimension(); j++) {
                    matrix[startX + i][startY + j] = node.getData();
                }
            }
        } else {
            int halfDim = node.getDimension() / 2;
            fillMatrix(node.upperLeft, matrix, startX, startY);
            fillMatrix(node.upperRight, matrix, startX, startY + halfDim);
            fillMatrix(node.lowerLeft, matrix, startX + halfDim, startY);
            fillMatrix(node.lowerRight, matrix, startX + halfDim, startY + halfDim);
        }
    }

    private boolean allValuesSame(Integer[][] matrix, int startX, int startY, int dimension) {
        int value = matrix[startX][startY];
        for (int i = startX; i < startX + dimension; i++) {
            for (int j = startY; j < startY + dimension; j++) {
                if (!matrix[i][j].equals(value)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkDimIsSquareAndEven(Integer[][] matrix) {
        int rows = matrix.length;
        if (rows == 0 || rows % 2 != 0) {
            return false;
        }
        for (Integer[] row : matrix) {
            if (row.length != rows) {
                return false;
            }
        }
        return true;
    }
    public String toString() {
        Integer[][] m = toMatrix();
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


    public static void main(String[] args) {

//        // caso de uso A
        Integer[][] matrix1 = new Integer[][]{
                {1, 2},
                {3, 1}
        };

        QuadtreeGPT qt1 = new QuadtreeGPT(matrix1);
        System.out.println(qt1);

        // caso de uso B
        Integer[][] matrix2 = new Integer[][]{
                {1, 1},
                {1, 1}
        };

        QuadtreeGPT qt2 = new QuadtreeGPT(matrix2);
        System.out.println(qt2);


        //caso de uso C


        Integer[][] matrix3 = new Integer[][]{
                {1, 1, 3, 3},
                {1, 2, 3, 3},
                {3, 1, 4, 4},
                {2, 1, 4, 4}
        };

        QuadtreeGPT qt3 = new QuadtreeGPT(matrix3);
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

        QuadtreeGPT qt4 = new QuadtreeGPT(matrix4);
        System.out.println(qt4);

    }


    private class QTNode {
        private Integer data;
        private int dimension;
        private QTNode upperLeft, upperRight, lowerLeft, lowerRight;

        public QTNode(Integer data, int dimension) {
            this.data = data;
            this.dimension = dimension;
        }

        public boolean isLeaf() {
            return data != null;
        }

        public Integer getData() {
            return data;
        }

        public int getDimension() {
            return dimension;
        }
    }
}
