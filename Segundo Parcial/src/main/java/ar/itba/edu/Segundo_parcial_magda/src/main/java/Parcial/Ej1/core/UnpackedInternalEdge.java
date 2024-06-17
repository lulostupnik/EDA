package Parcial.Ej1.core;

import java.util.Objects;

// clase completa que representa un eje. Descompacta la informacion de internalEdge

public class UnpackedInternalEdge<V, E> {
    private V source;
    private E edgeProps;
    private V target;

    public UnpackedInternalEdge(V source, E edgeProps, V target) {
        this.source = source;
        this.target = target;
        this.edgeProps = edgeProps;
    }

    @Override
    public boolean equals(Object obj) {
        @SuppressWarnings("unchecked")
        UnpackedInternalEdge aux = (UnpackedInternalEdge) obj;
        return ((edgeProps == null && aux.edgeProps == null) || (edgeProps != null && edgeProps.equals(aux.edgeProps)))
                && source.equals(aux.source) && target.equals(aux.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, edgeProps, target);
    }
}
