package ar.itba.edu.BTree;

public class Test {

    /*
    * @TODO leer la clase a ver como se hace el lexi diferente.
     */

    public static void main(String[] args) {
       // Integer[] toAddArr = {130, 50, 20, 10, 30, 100, 70,120 ,180, 150, 140, 160,220,200,240};
        // Integer[] toDeleteArr = {200,2230,50};

        Integer[] toAddArr = {0, 8, 109, 220, 222, 241, 149, 107, 75, 248, 254, 140, 16, 66, 74, 21, 211, 47, 80,242};
        Integer[] toRemoveArr = {66, 21, 109, 241, 149, 140, 211, 220 ,242};
        addAndDelete(toAddArr, toRemoveArr, 1, REMOVE_PREFERENCE.LEFT_BROTHER_HIGHEST_LEXI);
        addAndDelete(toAddArr, toRemoveArr, 1, REMOVE_PREFERENCE.RIGHT_BROTHER_LOWEST_LEXI);
    }

    public static void addAndDelete(Integer[] toAddArr,  Integer[] toRemoveArr, Integer order, REMOVE_PREFERENCE lexi){

        BTree<Integer> st = new BTree<>(order, lexi);

        for (Integer i : toAddArr) {
            st.add(i);
        }
        System.out.println("El arbol inicial (con " + lexi + "): ");
        System.out.println(st);

        for (Integer i : toRemoveArr) {
            System.out.println("--------------------");
            System.out.println("Eliminando: " + i + "\n");
            st.remove(i);
            System.out.println(st);
        }
    }




}