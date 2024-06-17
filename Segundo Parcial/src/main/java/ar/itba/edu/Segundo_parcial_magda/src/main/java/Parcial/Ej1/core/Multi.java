package Parcial.Ej1.core;

import java.util.Collection;

public class Multi<V, E> extends AdjacencyListGraph<V, E> {
    protected Multi(boolean isDirected, boolean acceptSelfLoops, boolean isWeighted) {
        super(false, isDirected, acceptSelfLoops, isWeighted);
    }
    @Override
    public void addEdge(V aVertex, V otherVertex, E theEdge) {
        // validacion y creacion de vertices si fuera necesario
        super.addEdge(aVertex, otherVertex, theEdge);
        Collection<InternalEdge> adjListSrc = getAdjacencyList().get(aVertex);
        // creacion de ejes sin problema
        adjListSrc.add(new InternalEdge(theEdge, otherVertex));
        if (!isDirected) {
            Collection<AdjacencyListGraph<V, E>.InternalEdge> adjListDst = getAdjacencyList().get(otherVertex);
            adjListDst.add(new InternalEdge(theEdge, aVertex));
        }
    }

    @Override
    public boolean hasEulerianPath() {
        throw new RuntimeException("Eulerian Path not implemeted in Multi graph");
    }

    @Override
    public boolean checkProp1() {
        throw new RuntimeException("Check Prop 1 not implemented in Multi graph");
    }

    @Override
    public boolean checkProp2() {
        throw new RuntimeException("Check Prop 2 not implemented in Multi graph");
    }


}
