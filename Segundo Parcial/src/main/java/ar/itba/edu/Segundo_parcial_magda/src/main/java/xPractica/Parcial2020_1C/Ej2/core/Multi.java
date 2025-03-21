package xPractica.Parcial2020_1C.Ej2.core;


import java.util.Collection;

public class Multi<V, E> extends AdjacencyListGraph<V, E> {

    protected Multi(boolean isDirected, boolean acceptSelfLoops, boolean isWeighted) {
        super(false, isDirected, acceptSelfLoops, isWeighted);

    }


    @Override
    public void addEdge(V aVertex, V otherVertex, E theEdge) {

        // validación y creación de vertices si fuera necesario
        super.addEdge(aVertex, otherVertex, theEdge);

        Collection<InternalEdge> adjListSrc = getAdjacencyList().get(aVertex);

        adjListSrc.add(new InternalEdge(theEdge, otherVertex));

        if (!isDirected) {
            Collection<AdjacencyListGraph<V, E>.InternalEdge> adjListDst = getAdjacencyList().get(otherVertex);
            adjListDst.add(new InternalEdge(theEdge, aVertex));
        }
    }


}
