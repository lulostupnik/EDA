package xPractica.ParcialXX.Ej2;

import java.util.*;

public class Amigos {
    public List<String> amigos(String alumno, HashMap<String, List<String>> amigos, Integer grado) {
        if (grado < 0)
            return null;
        //A -> B, C
        //B -> D, F, G
        //C -> empty
        //D -> A, B
        //F -> A, D
        //G -> B, C, H
        //H -> empty

        //si me piden amigos de grado 3 que tiene A tengo que hacer:
        //amigos de grado 1: -------B-------------C
        //grado 2:            D_____F______G----empty
        //grado 3:           A,B---A,D---B,C,H
        //-> el A no me interesa porque es el alumno, el B y C no me interesa porque es amigo 1, el D no me interesa
        //   porque es amigo de grado 2. Solo me sirve el H porque es amigo de grado 3.
        /*
        List<String> amigosGradoMenor = new ArrayList<>();
        List<String> potencialesAmigosN = amigos.values();
        for (int i = 0; i < grado; i++) {
            for (List<String> listaAmigos : amigos.values()) {
                for (String amigo : listaAmigos) {
                    if (!amigosGradoMenor.contains(amigo) && !potencialesAmigosN.contains(amigo) && !amigo.equals(alumno))
                        potencialesAmigosN.add(amigo);
                }
            }
            amigosGradoMenor.addAll(potencialesAmigosN);
            potencialesAmigosN.clear();
        }
        return amigosGradoMenor;
         */
        return null;
    }

    public static void main(String[] args) {
        Amigos a = new Amigos();
        HashMap<String, List<String>> amigos = new HashMap<>();
        amigos.put("A", Arrays.asList("B", "C"));
        amigos.put("B", Arrays.asList("D", "F", "G"));
        amigos.put("C", Collections.emptyList());
        amigos.put("D", Arrays.asList("A", "B"));
        amigos.put("F", Arrays.asList("A", "D"));
        amigos.put("G", Arrays.asList("B", "C", "H"));
        for (int i = 0; i < 5; i++) {
            System.out.println(String.format("Amigos grado %d:", i));
            for (String al : a.amigos("A", amigos, i))
                System.out.println(al);
            System.out.println("");
        }
    }
}
