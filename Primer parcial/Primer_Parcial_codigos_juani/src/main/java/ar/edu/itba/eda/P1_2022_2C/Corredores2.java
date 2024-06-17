package ar.edu.itba.eda.P1_2022_2C;

import java.lang.reflect.Array;

public class Corredores2 {

    //Use lo de marengo y funciono

    public int range(int leftKey, int rightKey,int[] vec) {
        if(vec.length==0)
            return 0;
        int leftIndex, rightIndex;
        leftIndex = firstOccurrence(leftKey,vec);

        rightIndex = lastOccurrence(rightKey,vec);


        int from = Math.max(0,leftIndex), to = Math.min(vec.length-1, rightIndex);
        if(from>to)
            return 0;

        return to-from+1;
    }

    public int binarySearch(int key, int izq, int der,int[] vec) {
        if(izq >= der) {
            if((vec[izq])>key)
                return izq-1;
            if((vec[izq])<key)
                return izq+1;
            return izq;
        }

        int pivot = (der + izq)/2;

        if((vec[pivot])>key)
            return binarySearch(key, izq, pivot-1,vec);
        if((vec[pivot])<key)
            return binarySearch(key, pivot+1, der,vec);

        return pivot;
    }

    public int firstOccurrence(int key,int[] vec) {
        int index = binarySearch(key, 0, vec.length-1,vec);
        if(index>0 && index< vec.length && (vec[index])==key ) {
            for(int i=index-1; i>=0; i--) {
                if((vec[i])!=key)
                    return i+1;
            }
            return 0;
        }
        return index;
    }

    public int lastOccurrence(int key,int[] vec) {
        int index = binarySearch(key, 0, vec.length-1,vec);
        if(index>0 && index<vec.length && (vec[index])==key) {
            for(int i=index+1; i<vec.length; i++) {
                if((vec[i])!=0)
                    return i-1;
            }
            return vec.length-1;
        }
        return index;
    }


    public int[] tiemposEntre(int[] tiempos, Corredores.Pedido[] pedidos){
        int[] toReturn = new int[pedidos.length];
        for (int i = 0;i< pedidos.length;i++ ){
            toReturn[i] = range(pedidos[i].desde,pedidos[i].hasta,tiempos);
        }
        return toReturn;
    }

    public static class Pedido {
        public int desde;
        public int hasta;
        public Pedido(int desde, int hasta) {
            this.desde = desde;
            this.hasta = hasta;
        }
    }

    public static void main(String[] args) {
        Corredores c = new Corredores();
        Corredores.Pedido[] pedidos = new Corredores.Pedido[] {
                //new Pedido(50,70),
                //new Pedido(192,192),
                new Corredores.Pedido(200, 240),
                new Corredores.Pedido(180, 210),
                new Corredores.Pedido(220, 280),
                new Corredores.Pedido(0, 200),
                new Corredores.Pedido(290, 10000)
        };
        int[] tiempos = new int[] {
                192,
                200,
                210,
                221,
                229,
                232,
                240,
                240,
                243,
                247,
                280,
                285
        };
        int [] respuestas = c.tiemposEntre(tiempos, pedidos);
        for(int i=0; i< respuestas.length; i++) {
            System.out.println(respuestas[i]);
        }
    }
}
