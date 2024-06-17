package ar.edu.itba.eda.P1_2022_1C;

public class URLfy {

    public void reemplazarEspacios(char [] arregloParam) {

        int j = arregloParam.length-1;
        char c;
        for(int i = arregloParam.length-1; i >= 0; i--){  //n+1
            c = arregloParam[i];
            // '%' '2' '0'
            if(c == ' '){               // 1 por ciclo (total 1 * n)
                arregloParam[j--] = '0';
                arregloParam[j--] = '2';
                arregloParam[j--] = '%';
            }
            else if(c != '\0'){      // si no hay ningun espacio entra 1 por ciclo (total 1 * n)
                arregloParam[j--] = c;
            }
        }
        //complejidad (n+1) + n + n = 3n + 1 => O(n)

    }



    public static void main(String[] args) {
        URLfy urlfy = new URLfy();
        char [] arreglo = new char[] { 'e', 's', ' ', 'u', 'n', ' ', 'e', 'j', 'e', 'm', 'p', 'l', 'o', '\0', '\0', '\0', '\0'};
        urlfy.reemplazarEspacios(arreglo);
        for(int i = 0; i<arreglo.length;i++){
            System.out.printf("%c ",arreglo[i]);
        }
        System.out.println(" ");

        arreglo= new char [] {'a', ' ', 'b', ' ', 'c', ' ', 'd', ' ', 'e', ' ', 'f', ' ', 'g', ' ', 'h', 'o', 'l', 'a', '\0', '\0',
                '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0'};
        urlfy.reemplazarEspacios(arreglo);
        for(int i = 0; i<arreglo.length;i++){
            System.out.printf("%c ",arreglo[i]);
        }
        System.out.println(" ");

        arreglo= new char [] {' ', ' ', 'e', 's', 'p', 'a', 'c', 'i', 'o', 's', ' ', ' ', '\0', '\0', '\0', '\0', '\0', '\0',
                '\0', '\0'};
        urlfy.reemplazarEspacios(arreglo);
        for(int i = 0; i<arreglo.length;i++){
            System.out.printf("%c ",arreglo[i]);
        }
        System.out.println(" ");
    }

}

