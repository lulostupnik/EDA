package eda;

public class Main {
    public static void main(String[] args) {
        IndexService<Integer> myIndex = new IndexWithDuplicates<>();
        Integer[] arr = new Integer[] {100, 50, 30, 50, 80, 100, 100, 30, 0};
        myIndex.initialize(arr);
        System.out.println(myIndex);
        System.out.println(myIndex.search(20));
        System.out.println(myIndex.search(80));   // true
        System.out.println(myIndex.occurrences(50));  // 2
        myIndex.delete(50);
        System.out.println(myIndex.occurrences(50));  // 1
        System.out.println(myIndex);

        Comparable<Integer>[] ans = myIndex.range(30, 31, true, true);
        printArr(ans);

    }

    private static void printArr(Object[] arr) {
        for (Object s : arr) {
            System.out.print(s.toString() + ", ");
        }
        System.out.println();
    }

}
