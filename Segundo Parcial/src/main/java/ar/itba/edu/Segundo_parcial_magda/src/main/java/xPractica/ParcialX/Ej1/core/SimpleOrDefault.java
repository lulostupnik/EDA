package xPractica.ParcialX.Ej1.core;


import java.util.Collection;

public class SimpleOrDefault<V, E> extends AdjacencyListGraph<V, E> {

    protected SimpleOrDefault(boolean isDirected, boolean acceptSelfLoops, boolean isWeighted) {
        super(true, isDirected, acceptSelfLoops, isWeighted);

    }


    @Override
    public void addEdge(V aVertex, V otherVertex, E theEdge) {

        // validación y creación de vertices si fuera necesario
        super.addEdge(aVertex, otherVertex, theEdge);

        Collection<InternalEdge> adjListSrc = getAdjacencyList().get(aVertex);

        // busca la lista de adyacencia del vértice, y chequea si aparece el otro
        for (InternalEdge internalEdge : adjListSrc) {
            /*
             * cada interalEdge tiene un target al que apunta
             * el equals chequea si ese target es el mismo que otherVertex
             * si es asi, es porque ya existe una arista entre los dos vertices
             */
            if (internalEdge.equals(otherVertex))
                throw new IllegalArgumentException(String.format(Messages.getString("addEdgeSimpleOrDefaultError"), aVertex, otherVertex));
        }

        adjListSrc.add(new InternalEdge(theEdge, otherVertex));

        if (!isDirected) {
            Collection<AdjacencyListGraph<V, E>.InternalEdge> adjListDst = getAdjacencyList().get(otherVertex);
            adjListDst.add(new InternalEdge(theEdge, aVertex));
        }

    }


}
