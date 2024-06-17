package eda.not_comparable;

public class Main {
    public static void main(String[] args) {

        IndexService myIndex = new IndexServiceImpl();
        int[] arr = new int[] {100, 50, 30, 50, 80, 100, 100, 30,0};
        myIndex.initialize( arr );
        System.out.println(myIndex);
        System.out.println( myIndex.search( 20 ));
        System.out.println( myIndex.search( 80 ));   // se obtiene true
        System.out.println (myIndex.occurrences( 50 ) );  // se obtiene 2
        myIndex.delete( 50 );
        System.out.println (myIndex.occurrences( 50 ) );  // se obtiene 1
        System.out.println(myIndex);

        printArr(myIndex.range(30,31,true,true));

    }

    private static void printArr(int[] arr){
        for(int i=0; i<arr.length ;i++){
            System.out.print(arr[i] + ", ");
        }
        System.out.println();
    }
}
