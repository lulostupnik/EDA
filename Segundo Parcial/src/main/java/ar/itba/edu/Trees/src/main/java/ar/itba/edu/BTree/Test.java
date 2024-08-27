package ar.itba.edu.BTree;

public class Test {

    /*
    * @TODO leer la clase a ver como se hace el lexi diferente.
     */

    public static void main(String[] args) {

          Integer[] toAddArr = {220, 60, 170, 410, 660, 40, 50, 100, 130, 180, 190, 300, 340, 440, 540, 719, 800};
          Integer[] toRemoveArr = {220};
          //addAndDelete(toAddArr, toRemoveArr, 1, REMOVE_PREFERENCE.LEFT_BROTHER_HIGHEST_LEXI);
          addAndDelete(toAddArr, toRemoveArr, 4, REMOVE_PREFERENCE.RIGHT_BROTHER_LOWEST_LEXI);


    }

    public static void addAndDelete(Integer[] toAddArr,  Integer[] toRemoveArr, Integer order, REMOVE_PREFERENCE lexi){

        if(toRemoveArr == null){
            toRemoveArr = new Integer[0];
        }
        if(toAddArr == null){
            toAddArr = new Integer[0];
        }


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