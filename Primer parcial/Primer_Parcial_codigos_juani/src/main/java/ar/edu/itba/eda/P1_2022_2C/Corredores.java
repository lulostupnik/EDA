package ar.edu.itba.eda.P1_2022_2C;

import java.util.Arrays;

public class Corredores {


    private int  range(int leftKey, int rightKey,int[] vec) {
        int leftIndex=getClosestPosition(0,vec.length-1,leftKey,vec);
        int rightIndex=getClosestPosition(0,vec.length-1,rightKey,vec);

        leftIndex = Math.min(vec.length-1,leftIndex);
        rightIndex = Math.min(vec.length-1,rightIndex);


        while(leftIndex >= 0 && vec[leftIndex] == leftKey) leftIndex--;
        if(leftIndex+1<vec.length && vec[leftIndex+1]==leftKey)
            leftIndex++;

        while(rightIndex < vec.length && vec[rightIndex] == rightKey) rightIndex++;
        if(rightIndex-1 >= 0 && vec[rightIndex-1]==rightKey)
            rightIndex--;

        if((leftIndex>=rightIndex && rightKey != leftKey) || rightIndex>=vec.length) return 0;
        return rightIndex-leftIndex+1;
    }

    public int[] tiemposEntre(int[] tiempos,Pedido[] pedidos){
            int[] toReturn = new int[pedidos.length];
            for (int i = 0;i< pedidos.length;i++ ){
                toReturn[i] = range(pedidos[i].desde,pedidos[i].hasta,tiempos);
            }
            return toReturn;
    }

    private int getClosestPosition(int izq,int der,int element,int[] vec){
        if(izq>der) return izq;
        int mid=(der+izq)/2;
        int midElement=vec[mid];
        if(element == midElement) return mid;
        if(element < midElement){
            return getClosestPosition(izq,mid-1,element,vec);
        }
        return getClosestPosition(mid+1,der,element,vec);
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
        Pedido[] pedidos = new Pedido[] {
                //new Pedido(50,70),
                //new Pedido(192,192),
                new Pedido(200, 240),
                new Pedido(180, 210),
                new Pedido(220, 280),
                new Pedido(0, 200),
                new Pedido(290, 10000)
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
