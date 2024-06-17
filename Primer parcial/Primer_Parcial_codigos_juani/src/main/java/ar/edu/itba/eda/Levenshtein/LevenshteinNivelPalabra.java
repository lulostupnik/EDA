package ar.edu.itba.eda.Levenshtein;

public class LevenshteinNivelPalabra {

    private String string1;
    private String string2;

    public LevenshteinNivelPalabra(String string1, String string2) {
        this.string1 = string1;
        this.string2 = string2;
    }

    public int distance() {
        String[] strings1 = string1.split("\\s");
        String[] strings2 = string2.split("\\s");
        //for(String s:strings1) System.out.print(s);
        //for(String s:strings2) System.out.print(s);

        int[][] matrix = new int[strings1.length+1][strings2.length+1];
        for (int i = 0; i < strings1.length+1; i++){
            for (int j = 0; j < strings2.length+1; j++){
                if (i == 0 || j == 0)
                    matrix[i][j] = (i == 0? j : i);
                else{

                    int a = matrix[i-1][j]+1;
                    int b = matrix[i][j-1]+1;
                    int c = matrix[i-1][j-1] + (!strings1[i-1].equals(strings2[j-1])?1:0);
                    matrix[i][j] = Math.min(a,Math.min(b,c));
                }
            }
        }
        for(int i = 0;i<strings1.length+1;i++){
            for(int j=0;j<strings2.length+1;j++){
                System.out.printf("%d ",matrix[i][j]);
            }
            System.out.println();
        }
        return matrix[strings1.length][strings2.length];
    }

    public static void main(String[] args) {
        String str1= "estructura de datos y algoritmos";
        String str2= "algoritmos y estructura de datos";
        LevenshteinNivelPalabra levenshteinNivelPalabra = new LevenshteinNivelPalabra(str1,str2);
        System.out.println(levenshteinNivelPalabra.distance());
    }


}
