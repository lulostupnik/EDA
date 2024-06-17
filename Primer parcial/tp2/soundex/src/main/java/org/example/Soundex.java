package org.example;

/*
    Tabla de valores:
    * A,E,I,O,U,Y,W,H --> 0 (no se codifica)
    * B,F,P,V         --> 1
    * C,G,J,K,Q,S,X,Z --> 2
    * D,T             --> 3
    * L               --> 4
    * M,N             --> 5
    * R               --> 6

Paso 1 (opcional): Pasar a mayúsculas y dejar sólo las letras (dígitos, símbolos de puntuación, espacios, etc. se eliminan).
Paso 2: Colocar OUT[0]=IN[0].
Paso 3: Se calcula vble. last como el peso fonético de IN[0]
Paso 4: Para cada letra iter siguiente en IN y hasta completar 3 dígitos o terminar de procesar IN, hacer
3.1) calcular vble current con peso fonético de iter. Si es diferente a 0 y no coincide con last, appendear current en OUT.
3.2) independiente del paso anterior, tapar last = current.
Paso 5: si hace falta completar con ‘0’s y devolver OUT.


 */
public class Soundex {
    public static String representation(String s) {
        s = s.toUpperCase();
        char[] IN = s.toCharArray();
        char[] OUT = {'0', '0', '0', '0'};
        OUT[0] = IN[0];
        int count = 1;
        int current, last = getMapping(IN[0]);
        for (int i = 1; i < IN.length && count < 4; i++) {
            char iter = IN[i];
            current = getMapping(iter);
            if (current != '0' && current != last) {
                OUT[count++] = (char)(current);
                last = current;
            }
        }
        return new String(OUT);
    }


    public static double metric(String s1, String s2){
        s1 = representation(s1);
        s2 = representation(s2);
        double ans = 0;
        for(int i=0; i<4;i++){
            if(s1.charAt(i) == s2.charAt(i)){
                ans++;
            }
        }
        ans = ans/4;
        return  ans;
    }
    public static char getMapping(char input){
        char[] mapping = {'0', '1', '2', '3', '0', '1', '2', '0', '0', '2', '2', '4', '5',
                '5', '0', '1', '2', '6', '2', '3', '0', '1', '0', '2', '0', '2'};
        return mapping[Character.toUpperCase(input) - 'A'];
    }
}
