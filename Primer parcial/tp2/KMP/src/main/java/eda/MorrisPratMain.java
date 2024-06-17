package eda;

/*
Prefijos, Sufijos y Substrings

Dados un alfabeto sigma  y  los strings  x perteneciente a sigma*,  w perteneciente a sigma* ,  z perten a sigma* .
Sea  p=xwz. Se dice que x es un prefijo de p. Se dice que w es un substring de p. Se dice que z es un sufijo de p.


Dado un string: "ABCD":
Sigma = { 0, 1, 2, 3, 4, 5}

Sea s = “01230”
¿Cuáles son los prefijos de s?
Rta: “”, “0”, “01”, “012”, “0123”, s

¿Cuáles son los sufijos de s?
Rta: “”, “0”, “30”, “230”, “1230”, s   (arranco a la derecha y le agrego uno a la izquierda)

¿Cuáles son los borders de s?
Rta: “”, s, “0”.     Como mínimo  hay 2 borders: “” y s

¿Cuáles son los substrings de s?
Rta: “”, “0”, “01”, “012”, “0123”, s, “30”, “230”, “1230”, “1”, “12”, “123”, “2”, “23”, “3”, “30”, “0”


KMP:
--> Se completa la tabla NEXT con la MAXIMA longitud de un BORDE PROPIO (no cuenta vacio o s) del subtring hasta la pos i
ej: s = "BCBC"
next[0] = 0 (siempre)
next[1]: Se usa el substring "BC"
--> Prefijos: "", "B", "BC"
--> Sufijos:  "", "C", "BC"
--> next[1]= 0 pues no tiene bordes propios
next[2]: Se usa el subtring "BCB"
--> Prefijos: "", "B", "BC", "BCB"
--> Sufijos:  "", "B", "CB", "BCB"
--> "B" es el borde propio mas largo --> next[2]=1
next[3]: Se usa el subtring "BCBC"
--> Prefijos: "", "B", "BC", "BCB", "BCBC"
--> Sufijos:  "", "C", "BC", "CBC", "BCBC"
--> "BC" es el borde mas largo --> next[3]=2
------------------------------------------------------------------------------

Se cumple:
N[i ] <= N[i - 1] + 1 Pero puede ser que N[i]= 0

El algoritmo que calcula next tiene

Complejidad especial:  O(m)
Complejidad temporal: O(m)


Complejidad KMP (incluyendo next) segun google O(M+N)


 */



import static eda.MorrisPrat.indexOf;

public class MorrisPratMain {
    public static void main(String[] args) {
        String query, text;
        int pos;

        // debe dar 3
        query= "ABXABU";
        text= "ABXABXABUF";

        pos= indexOf(query.toCharArray(), text.toCharArray());
        System.out.println(String.format("%s in %s at pos %d\n", query, text, pos));


        // debe dar 5
        query= "ABAB";
        text= "SABASABABA";
        pos= indexOf(query.toCharArray(), text.toCharArray());
        System.out.println(String.format("%s in %s at pos %d\n", query, text, pos));


        // debe dar 0
        query= "ABAB";
        text= "ABABYYYY";
        pos= indexOf(query.toCharArray(), text.toCharArray());
        System.out.println(String.format("%s in %s at pos %d\n", query, text, pos));


        // debe dar -1
        query= "ABAB";
        text= "ABAYYYA";
        pos= indexOf(query.toCharArray(), text.toCharArray());
        System.out.println(String.format("%s in %s at pos %d\n", query, text, pos));
    }
}


