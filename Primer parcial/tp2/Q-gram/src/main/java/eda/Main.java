package eda;

public class Main {
    public static void main(String[] args) {
//        Qgram q = new Qgram(2);
//        q.printTokens("alale");
//        System.out.println();
//        q.printTokens("salesal");
//        System.out.println(q.similarity("salesal", "alale"));
//
//        QGramNash q2 = new QGramNash(2);
//        q2.printTokens("alale");
//        System.out.println();
//        q2.printTokens("salesal");
//        System.out.println(q2.similarity("salesal", "alale"));


        QGramNash q3 = new QGramNash(3);
        q3.printTokens("JHON");
        System.out.println();
        q3.printTokens("JON");
        System.out.println(q3.similarity("JHON", "JON"));



    }
}

/*

Es un algoritmo que consiste en generar los pedazos que componen un string.
La distancia entre 2 strings estará dada por la cantidad de componentes que tengan en común.
Si Q es 1 se generan componentes de longitud 1, si Q es 2 se generan bi-gramas y si Q es 3 se generan tri-gram (los de 3 son de los más usados).

Por ejemplo, para el string “JOHN” si se quiere generar hasta tri-gramas (Q <= 3),
puede completarse al comienzo y al final con Q-1 símbolos especiales (que no pertenezcan al alfabeto)
y deslizando la ventana imaginaria de tamaño Q, se va generando los Q-gramas.

Sea ‘JOHN’ --> Los Q-Grams (n<=3) son {J, O, H, N, #J, JO, OH, HN, N#, ##J, #JO, JOH, OHN, HN#, N##}
Comparamos con “Joe”:
--> Q-GRAMS = {J,O,E,#J,JO,OE,E#,##J,#JO,JOE,OE#,E##}
Vemos que los Q-gramas en común son:
--> {J,O,#J,JO,##J,#JO}
Entonces distancia(John, Joe) = 6
Ahora vamos a necesitar una fórmula para pasarlo a [0,1]. Algunas son:
Q-gram(s1,s2) = (#(TG(s1)+#TG(s2)-#TG_NOT_SHARED)/(#TG(s1)+#TG(s2))

Q-grams(John, joe) = (6+5-7)/(6+5)
En conclusión, Q-Gram de 2 parámetros da “similitud” entre 2 strings: 1 es máxima similitud, 0 es nula.


 */