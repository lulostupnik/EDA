package ar.edu.itba.eda.Testeos;

import java.util.Scanner;

public class TeoremaMaestro {
    public static void calcular(){
        System.out.print("Ingrese los parametros separados por un espacio (a b c d):\na = Llamados a la recursion\nb = Divisor del parametro en recursion\nc = Recorridos fuera de la recursion\nd = Orden del recorrido fuera de la recursion\n");
        Scanner inputScanner = new Scanner(System.in).useDelimiter("[,;\\s]+");
        int a = Integer.parseInt(inputScanner.next());
        int b = Integer.parseInt(inputScanner.next());
        int c = Integer.parseInt(inputScanner.next());
        int d = Integer.parseInt(inputScanner.next());
        if(a > Math.pow(b, d)){
            System.out.print("a > b^d -> O(N^(logb(a)))\n");
            System.out.printf("O(N^%f)\n", Math.log(a)/Math.log(b));
        }else if(a == Math.pow(b, d)){
            System.out.print("a == b^d -> O(N^d*log(N))\n");
            System.out.printf("O(N^%d*log(N))\n", d);
        }else{
            System.out.print("a < b^d -> O(N^d)\n");
            System.out.printf("O(N^%d)\n", d);
        }
    }

    public static void main(String[] args){
        calcular();
    }
}
