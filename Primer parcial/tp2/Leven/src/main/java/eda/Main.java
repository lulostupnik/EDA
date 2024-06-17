package eda;

/*
Como Levenshtein es una métrica de distancia, cumple con propiedades:
    Simetría: Levenshtein(str1, str2) = Levenshtein(str2, str1)
    Desigualdad: Levenshtein(str1, str2) + Levenshtein(str2, str3) >= Levenshtein(str1, str3)

Paso 1: Poner cada string en una fila / columna con un caracter extra y completar
ej:
    #   e   x   -   a   m   i   g   o
#   0   1   2   3   4   5   6   7   8
e   1
x   2
k   3
u   4
s   5
a   6

Luego cada posicion se pone el minimo entre:
1) El numero de arriba +1
2) El numero a la izquierda + 1
3) El numero arriba a la izquierda + --> 0 si las dos letras en las que estoy parado son iguales O 1 si no

 */
public class Main {
    public static void main(String[] args) {
        String s1 = "ex-amigo";
        String s2 = "ex";
        LevenshteinMatrix.printMatrix(LevenshteinMatrix.getMatrix(s1, s2), LevenshteinMatrix.getMatrixRows(s1,s2), LevenshteinMatrix.getMatrixCols(s1,s2));
        System.out.println("Normalized: " + Levenshtein.normalized(s1,s2));
        System.out.println("Distance " + Levenshtein.distance(s1, s2));
    }
}
