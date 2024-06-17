package Parcial.Ej1.core;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class SimpleOrDefault<V, E> extends AdjacencyListGraph<V, E> {

    protected SimpleOrDefault(boolean isDirected, boolean acceptSelfLoops, boolean isWeighted) {
        super(true, isDirected, acceptSelfLoops, isWeighted);
    }

    @Override
    public boolean hasEulerianPath() {
        if (isDirected || !getIsSimple() || getAcceptSelfLoop())
            throw new RuntimeException("Eulerian Path not implemented in Directed Graph, multi graph or graphs that accepts self loops");
        return checkProp1() && checkProp2();
    }

    @Override
    public boolean checkProp1() {
        if (isDirected || !getIsSimple() || getAcceptSelfLoop())
            throw new RuntimeException("Eulerian Path not implemented in Directed Graph, multi graph or graphs that accepts self loops");
        boolean flag = true;
        Iterator<V> iter = getVertices().iterator();
        V firstVert = null;
        Set<V> visited = new HashSet<>();
        int getOutOfBfs = 0;
        while (iter.hasNext()) {
            firstVert = iter.next();
            getOutOfBfs = 0;
            if (!visited.contains(firstVert)) {
                for (V vert : getBFS(firstVert)) {
                    visited.add(vert);
                    if (degree(vert) >= 1 && getOutOfBfs == 0) {
                        if (flag)
                            flag = false;
                        else
                            return false;
                        getOutOfBfs = 1;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean checkProp2() {
        if (isDirected || !getIsSimple() || getAcceptSelfLoop())
            throw new RuntimeException("Eulerian Path not implemented in Directed Graph, multi graph or graphs that accepts self loops");
        int cant = 0;
        for (V v : getAdjacencyList().keySet()) {
            if (degree(v) % 2 != 0)
                cant++;
        }
        return cant <= 2;
    }

    @Override
    public void addEdge(V aVertex, V otherVertex, E theEdge) {
        // validacion y creacion de vertices si fuera necesario
        super.addEdge(aVertex, otherVertex, theEdge);
        Collection<InternalEdge> adjListSrc = getAdjacencyList().get(aVertex);
        // if exists edge with same target...
        for (InternalEdge internalEdge : adjListSrc) {
            if (internalEdge.target.equals(otherVertex))
                throw new IllegalArgumentException(
                        String.format("simpleOrDefault graph cannot contain several edges with same source %s and target %s ",
                                aVertex, otherVertex));
        }
        // creacion de ejes
        adjListSrc.add(new InternalEdge(theEdge, otherVertex));
        if (!isDirected) {
            Collection<AdjacencyListGraph<V, E>.InternalEdge> adjListDst = getAdjacencyList().get(otherVertex);
            adjListDst.add(new InternalEdge(theEdge, aVertex));
        }
    }
}
