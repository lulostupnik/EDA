package xPractica.Parcial2020_1C.Ej2.use;

public class EmptyEdgeProp {


    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof EmptyEdgeProp))
            return false;

        return true;
    }

    @Override
    public String toString() {
        return "[]";
    }


}
