package Parcial2023_2C;

public class Quadtree {


    private QTNode root;


    
    public Quadtree( Integer[][] matrix ){
        if (! checkDimIsSquareAndEven(matrix) )
            throw new RuntimeException("Invalid Dim");
        root = buildGraph(matrix,0,0,matrix[0].length);
    }

    private QTNode buildGraph( Integer[][] matrix, int row, int col, int n){
        QTNode toReturn = new QTNode();
        toReturn.dimension = n;
        if(allEquals(matrix,row,col,n)) {
            toReturn.data = matrix[row][col];
            return toReturn;
        }
        toReturn.upperLeft = buildGraph(matrix,row,col,n/2);
        toReturn.upperRight = buildGraph(matrix,row,col+n/2,n/2);
        toReturn.lowerLeft = buildGraph(matrix,row+n/2,col,n/2);
        toReturn.lowerRight = buildGraph(matrix,row+n/2,col+n/2,n/2);
        return toReturn;
    }

    private boolean allEquals(Integer[][] matrix, int row, int col, int n){
        Integer aux = matrix[row][col];
        for (int i=row; i < row + n; i++){
            for (int j=col; j < col + n; j++){
                if(!aux.equals(matrix[i][j])) return false;
            }
        }
        return true;
    }

    public Integer[][] getMatrix(){
        Integer[][] toReturn = new Integer[root.dimension][root.dimension];
        getMatrixRec(toReturn,0,0,root.dimension,root);
        return toReturn;
    }

    private void getMatrixRec(Integer[][] matrix, int row, int col, int n,QTNode current){
        if(current.data == null){
            getMatrixRec(matrix,row,col,n/2,current.upperLeft);
            getMatrixRec(matrix,row,col+n/2,n/2,current.upperRight);
            getMatrixRec(matrix,row+n/2,col,n/2,current.lowerLeft);
            getMatrixRec(matrix,row+n/2,col+n/2,n/2,current.lowerRight);
        }else{
            fillMatrix(matrix,row,col,n,current.data);
        }
    }

    private void fillMatrix(Integer[][] matrix, int row, int col, int n, Integer value){
        for (int i=row; i < row + n; i++){
            for (int j=col; j < col + n; j++){
                matrix[i][j] = value;
            }
        }
    }


    private static boolean checkDimIsSquareAndEven(Integer[][] matrix) {
        if (matrix == null)
            return false;

        int dim= matrix.length;

        // es par?
        if (dim % 2 == 1)
            return false;


        // todas las filas tienen la misma dimension?
        for (int rec = 0; rec < dim; rec++) {
            if(matrix[rec].length != dim)
                return false;
        }
        return true;
    }


    public String toString( ){
        Integer[][] m = getMatrix();
        StringBuilder sb = new StringBuilder();

        for ( int i = 0; i < m.length; ++i ){
            for ( int j = 0; j < m.length; ++j ){
                sb.append( m[i][j] );
                sb.append( " " );
            }
            sb.append( "\n" );
        }
        return sb.toString();
    }



    private class QTNode{
        private Integer data;
        private int dimension;
        public int getDim(){ return dimension; }
        private QTNode upperLeft;
        private QTNode upperRight;
        private QTNode lowerLeft;
        private QTNode lowerRight;
    }

    public static void main(String[] args) {

        // caso de uso A
        Integer[][] matrix1 = new Integer[][] {
                { 1, 2 },
                { 3, 1 }
        };

        Quadtree qt1 = new Quadtree( matrix1 );
        System.out.println(  qt1 );


// caso de uso B
        Integer[][] matrix2 = new Integer[][] {
                { 1, 1 },
                { 1, 1 }
        };

        Quadtree qt2 = new Quadtree( matrix2 );
        System.out.println(  qt2 );


// caso de uso C
        Integer[][] matrix3 = new Integer[][] {
                { 1, 1, 3, 3 },
                { 1, 2, 3, 3 },
                { 3, 1, 4, 4 },
                { 2, 1, 4, 4 }
        };

        Quadtree qt3 = new Quadtree( matrix3 );
        System.out.println(  qt3 );


// caso de uso D
        Integer[][] matrix4 = new Integer[][] {
                { 1, 1, 3, 3, 8, 8, 8, 8 },
                { 1, 1, 3, 3, 8, 8, 8, 8 },
                { 3, 1, 4, 4, 8, 8, 8, 8 },
                { 2, 1, 4, 4, 8, 8, 8, 8 },
                { 1, 1, 1, 1, 7, 7, 7, 7 },
                { 1, 1, 1, 1, 7, 7, 7, 7 },
                { 1, 1, 1, 1, 7, 7, 7, 7 },
                { 1, 1, 1, 1, 7, 7, 7, 7 },
        };

        Quadtree qt4 = new Quadtree( matrix4 );
        System.out.println(  qt4 );

    }

}
