package core;

public class Quadtree {


    private QTNode root;



    public Quadtree( Integer[][] matrix ){
  if (! checkDimIsSquareAndEven(matrix) )
		throw new RuntimeException("Invalid Dim");

        // TODO: Completar

    }

    public Integer[][] getMatrix( )   {
        // TODO: Completar
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
