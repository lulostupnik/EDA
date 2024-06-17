package ar.edu.itba.eda.Testeos;

import java.util.*;

public class Main {

    public static int maxSubarraySum(int[] arr) {
        int n = arr.length;   // --> 1 espacio reservado
        int maxSum = Integer.MIN_VALUE; // --> 1 espacio reservado
        int currentSum = 0; // --> 1 espacio reservado
        for (int i = 0; i < n; i++) {  // --> 1 espacio reservado  | --> n+1 comparaciones
            currentSum = Math.max(arr[i], currentSum + arr[i]); // --> el metodo max de Math tiene una comparación por ciclo
            maxSum = Math.max(maxSum, currentSum); // // --> el metodo max de Math tiene una comparación por ciclo
        }// el ciclo se repite n veces
        return maxSum;
    }

    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in).useDelimiter("\\n");
        StringBuilder sb = new StringBuilder();
        if(inputScanner.hasNextLine())
            sb.append(inputScanner.nextLine());
        sb.reverse();

        Scanner lineScanner = new Scanner(sb.toString()).useDelimiter("\\s+");

        while (lineScanner.hasNext()){
            System.out.println(lineScanner.next());
        }
    }


}
//Solo contamos comparaciones
// T(maxSubarraySum) = n + 1 + n * (1 + 1) = 3n + 1 => O(n)

//El total de espacios reservados es 4 => la complejidad espacial es O(1)




//(-1) 7 - (-3) 2 * ^ 6 /
// 1 2 3 4 * - +
//
// (1 + (2 - (3*4)))



//Teorema Maestro
//T(n) = a T(n/b) + c n^d
//a-> veces q se llama
//b-> lo que va con la n/b
//c n^d complejidad que no esta en el paso recursivo
//a < b^d   O(N^d)
//a = b^d   O(N^d * log N)
//a > b^d   O(N^(logb a))



//KMP
// Query S U U S U U U S
// Next  0 0 0 1 2 3 0 1

//Next[0] -> 0

//Next[1]
//Prefijos - S SU
//Sufijos  - U SU
//Bordes - SU

//Next[2]
//Pre - S SU SUU
//Suf - U UU SUU
//Borde - SUU

//Next[3]
//Pre - S SU SUU SUUS
//Suf - S US UUS SUUS
//Borde - S SUUS

//Next[4]
//Pre - S SU SUU SUUS SUUSU
//Suf - U SU USU UUSU SUUSU
//Borde - SU SUUSU

//Next[5]
//Pre - S SU SUU SUUS SUUSU SUUSUU
//Suf - U UU SUU USUU UUSUU SUUSUU
//Borde - SUU SUUSUU















