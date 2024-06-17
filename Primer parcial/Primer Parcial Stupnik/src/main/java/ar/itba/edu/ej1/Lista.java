package ar.itba.edu.ej1;


import java.util.Random;

public class Lista{

    private Item first;
    private Item last;

    public Lista (){
        first = null;
        last = null;
    }

    public Lista (int maxNumero ){
        if (maxNumero < 1)
            throw new RuntimeException("tope debe ser por lo menos 1");
        last = new Item(1);
        first = last;
        for ( int i = 2; i <= maxNumero; ++i ){
            Item c = new Item( i );
            last.next = c;
            last = c;
        }
    }

    public Lista (int lower, int numberItems, boolean sorpresa ){
        if (numberItems <= 0)
            throw new RuntimeException("cantidad de numeros debe ser mayor que 0");

        // lower bound cableado
        last = new Item(lower);
        first = last;

        while( --numberItems > 0) {
            if (sorpresa)
                lower+= 2;
            else
                lower+= 5;

            sorpresa= !sorpresa;
            Item c = new Item( lower );
            last.next = c;
            last = c;
        }
    }


    private  Lista[] randomSplitListas( Integer nLists ) {

       if(nLists < 1){
           throw new RuntimeException("No se puede separar en menos de una lista");
       }

       Lista[] ans = new Lista[nLists];
       for(int i=0; i<nLists ; i++){
            ans[i] = new Lista();
        }

        Item current = first;
        while(current != null){
            Lista aux = ans[getRandom(nLists)];

            if(aux.last != null){
                aux.last.next = current;
                aux.last = current;
            }else{
                aux.first = current;
                aux.last = current;
            }
            Item aux2 = current;
            current = current.next;
            aux2.next=null;
        }
        last=null;
        first=null;
        //Elimino la lista


        return ans;
    }

    private int randP = 1;
    private Random r = new Random(randP);

    private Integer getRandom(Integer n){
        Integer retVal = r.nextInt( n );
        System.out.println( " {" + randP + "} [" + retVal.toString() + "]" );
        ++randP;
        return retVal;
    }

    private final class Item {
        private final Integer numero;
        private Item next = null;

        public Item(Integer numero) {
            this.numero = numero;
        }

        public String toString(){
            return numero.toString();
        }
    }

    public void dump() {
        String auxi= "";

        Item rec = first;
        while (rec != null) {
            auxi+= String.format("%s->", rec);
            rec= rec.next;
        }
        if (auxi.length() >0   )
            auxi= auxi.substring(0, auxi.length()-2);

        System.out.print(String.format("List with header: first vble points to %s, last vble points to  %s, items: %s", first, last, auxi));


        System.out.println();
    }

    // caso 1 (main1)
    public static void main1(String[] args) {
        Lista l = new Lista( 10 ); // l será: 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> 9 -> 10
        // lista original al principio
        System.out.print("First, the original list is ");
        l.dump();

        // distribuir entre 4
        Lista[] caso = l.randomSplitListas( 4 );

        for(int rec= 0; rec<caso.length; rec++) {
            System.out.print(String.format("list %d is ", rec));
            caso[rec].dump();
        }
        // lista original al final
        System.out.print("Finally, the original list is ");
        l.dump();
    }


    // caso B (main2)
    public static void main2(String[] args) {
        Lista l = new Lista( 5, 7, true ); // l será: 5 -> 7 -> 12 -> 14->19->21->26
        // lista original al principio
        System.out.print("First, the original list is ");
        l.dump();

        Lista[] caso = l.randomSplitListas( 6 );
        for(int rec= 0; rec<caso.length; rec++) {
            System.out.print(String.format("list %d is ", rec));
            caso[rec].dump();
        }
        // lista original al final
        System.out.print("Finally, the original list is ");
        l.dump();
    }



    // caso de uso C (main 3)
    public static void main3(String[] args) {
        Lista l = new Lista( 5, 7, false ); // l será: 5 -> 10 ->12-> 17 -> 19->24->26
        // lista original al principio
        System.out.print("First, the original list is ");
        l.dump();

        Lista[] caso = l.randomSplitListas( 6 );
        for(int rec= 0; rec<caso.length; rec++) {
            System.out.print(String.format("list %d is ", rec));
            caso[rec].dump();
        }

        // lista original al final
        System.out.print("Finally, the original list is ");
        l.dump();
    }



    // caso de uso D (main4)
    public static void main4(String[] args) {
        Lista l = new Lista(); // l tiene 0 items
        // lista original al principio
        System.out.print("First, the original list is ");
        l.dump();
        Lista[] caso = l.randomSplitListas( 4 );

        for(int rec= 0; rec<caso.length; rec++) {
            System.out.print(String.format("list %d is ", rec));
            caso[rec].dump();
        }
        // lista original al final
        System.out.print("Finally, the original list is ");
        l.dump();
    }


}