package ar.edu.itba.eda.Parcial2023;

import java.util.Random;

public class Baraja {

    private Carta first;
    private Carta last;

    private int qty;

    public Baraja(String[] palos, int maxNumero ){
        qty = palos.length * maxNumero;
        for ( String p : palos ){
            for ( int i = 1; i <= maxNumero; ++i ){
                Carta c = new Carta( p, i );
                if (first == null){
                    first = c;
                    last = c;
                } else {
                    last.next = c;
                    c.prev = last;
                    last = c;
                }
            }
        }
    }

    private int randP = 0;

    private Integer[] getRandomPair(){
        Integer retValue[] = new Integer[2];

        Random r = new Random(randP);

        retValue[0] = r.nextInt( qty );
        retValue[1] = r.nextInt( qty );

        System.out.println( " {" + randP + "} [" + retValue[0].toString() + "," + retValue[1] + "]" );
        ++randP;
        return retValue;
    }

    public void Mezclar( int numMezclas ){

        Integer[] nums = new Integer[2];
        int min,max;
        Carta current;
        Carta carta1;
        Carta carta2;
        Carta prev;
        Carta next;
        for (int i = 0;i<numMezclas;i++){
            nums = getRandomPair();
            min = Math.min(nums[0],nums[1]);
            max = Math.max(nums[0],nums[1]);
            if(!nums[0].equals(nums[1])){
                current = first;

                if(min <= (qty-1-max)){ //0 1 2 3 4 5 6 7 8 9
                    current = first;
                    int j = 0;
                    for(;j<min;j++) current = current.next;
                    carta1 = current;
                    if(max-min <= (qty-1-max)){
                        for(;j<max;j++) current = current.next;
                        carta2 = current;
                    }
                    else {
                        current = last;
                        for(j = qty-1;j>max;j--) current = current.prev;
                        carta2 = current;
                    }
                }
                else {
                    current = last;
                    int j = qty-1;
                    for(;j>max;j--) current = current.prev;
                    carta2 = current;
                    if(max-min <= min){
                        for(;j>min;j--) current = current.prev;
                        carta1 = current;
                    }
                    else {
                        current = first;
                        for(j = 0;j<min;j++) current = current.next;
                        carta1 = current;
                    }
                }


                if(min == 0) first = carta2;
                if(max == qty-1) last = carta1;

                if(max-min > 1) {
                    if (carta1.prev != null) carta1.prev.next = carta2;
                    if (carta1.next != null) carta1.next.prev = carta2;

                    if (carta2.prev != null) carta2.prev.next = carta1;
                    if (carta2.next != null) carta2.next.prev = carta1;
                    prev = carta1.prev;
                    next = carta1.next;

                    carta1.next = carta2.next;
                    carta1.prev = carta2.prev;

                    carta2.next = next;
                    carta2.prev = prev;
                } else {
                    if (carta1.prev != null) carta1.prev.next = carta2; // 1 2 3 4 5 6 7 8
                    if (carta2.next != null) carta2.next.prev = carta1; // 2 next     prev 5
                    // prev 3 next   prev 4 next
                    //    carta1        carta2
                    prev = carta1.prev;

                    carta1.prev = carta2;
                    carta1.next = carta2.next;

                    carta2.prev = prev;
                    carta2.next = carta1;
                }


            }




        }



    }

    public void dump(){
        Integer i = 0;
        Carta c = first;
        while( c != null ){
            System.out.println( "[" + i.toString() + "] " + c.toString() );
            c = c.next;
            ++i;
        }
    }

    private final class Carta {
        private final String palo;
        private final Integer numero;
        private Carta next = null;
        private Carta prev = null;

        public Carta(String palo, Integer numero) {
            this.palo = palo;
            this.numero = numero;
        }

        public String toString(){
            return palo + " " + numero.toString();
        }
    }


    public static void main(String[] args) {
        Baraja b = new Baraja( new String[]{"Oro", "Copa", "Espada", "Basto"}, 5 );

        b.dump();
        System.out.println();
        b.Mezclar( 1 );
        System.out.println();
        b.dump();
    }
}
