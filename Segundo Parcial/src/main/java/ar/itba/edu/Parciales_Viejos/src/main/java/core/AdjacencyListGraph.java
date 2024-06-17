package core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AdjacencyListGraph<V, E> implements GraphService<V, E> {

    private boolean isSimple;
    protected boolean isDirected;
    private boolean acceptSelfLoop;
    private boolean isWeighted;
    private Map<V, Collection<InternalEdge>> adjacencyList= new HashMap<>();

    protected   Map<V,  Collection<InternalEdge>> getAdjacencyList() {
        return adjacencyList;
    }
    public Collection<V> getVertices() {
        return getAdjacencyList().keySet() ;
    }

    protected AdjacencyListGraph(boolean isSimple, boolean isDirected, boolean acceptSelfLoop, boolean isWeighted) {
        this.isSimple = isSimple;
        this.isDirected = isDirected;
        this.acceptSelfLoop= acceptSelfLoop;
        this.isWeighted = isWeighted;
    }

    
public int connectedComponentsQty() {

        // TODO: Completar
    }


    public void addVertex(V aVertex) {

        if (aVertex == null )
            throw new IllegalArgumentException("addVertexParamCannotBeNull");

        // no edges yet
        getAdjacencyList().putIfAbsent(aVertex,
                new ArrayList<InternalEdge>());
    }

    public void addEdge(V aVertex, V otherVertex, E theEdge) {

        // validation!!!!
        if (aVertex == null || otherVertex == null || theEdge == null)
            throw new IllegalArgumentException("addEdgeParamCannotBeNull");

        // es con peso? debe tener implementado el metodo double getWeight()
        if (isWeighted) {
            // reflection
            Class<? extends Object> c = theEdge.getClass();
            try {
                c.getDeclaredMethod("getWeight");
            } catch (NoSuchMethodException | SecurityException e) {
                throw new RuntimeException( "Graph is weighted but the method double getWeighed() is not declared in theEdge");
            }
        }

        if (! acceptSelfLoop && aVertex.equals(otherVertex)) {
            throw new RuntimeException(String.format("Graph does not accept self loops between %s and %s" ,
                    aVertex, otherVertex) );
        }

        // if any of the vertex is not presented, the node is created automatically
        addVertex(aVertex);
        addVertex(otherVertex);
    }

    class InternalEdge {
        E edge;
        V target;

        InternalEdge(E propEdge, V target) {
            this.target = target;
            this.edge = propEdge;
        }
    }
}
