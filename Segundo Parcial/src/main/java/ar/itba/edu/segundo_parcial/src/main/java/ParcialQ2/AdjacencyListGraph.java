package ParcialQ2;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

abstract public class AdjacencyListGraph<V, E> implements GraphService<V, E> {

    private boolean isSimple;
    protected boolean isDirected;
    private boolean acceptSelfLoop;
    private boolean isWeighted;
    protected String type;

    // HashMap no respeta el orden de insercion. En el testing considerar eso
    private Map<V,Collection<InternalEdge>> adjacencyList= new HashMap<>();

    // respeta el orden de llegada y facilita el testing
    //	private Map<V,Collection<InternalEdge>> adjacencyList= new LinkedHashMap<>();

    protected   Map<V,  Collection<InternalEdge>> getAdjacencyList() {
        return adjacencyList;
    }

    protected AdjacencyListGraph(boolean isSimple, boolean isDirected, boolean acceptSelfLoop, boolean isWeighted) {
        this.isSimple = isSimple;
        this.isDirected = isDirected;
        this.acceptSelfLoop= acceptSelfLoop;
        this.isWeighted = isWeighted;

        this.type = String.format("%s %sWeighted %sGraph with %sSelfLoop",
                isSimple ? "Simple" : "Multi", isWeighted ? "" : "Non-",
                isDirected ? "Di" : "", acceptSelfLoop? "":"No ");
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void addVertex(V aVertex) {

        if (aVertex == null )
            throw new IllegalArgumentException("addVertex parameters cannot be null");

        // no edges yet
        getAdjacencyList().putIfAbsent(aVertex, new ArrayList<InternalEdge>());
    }

    @Override
    public Collection<V> getVertices() {
        return getAdjacencyList().keySet() ;
    }

    @Override
    public void addEdge(V aVertex, V otherVertex, E theEdge) {

        // validation!!!!
        if (aVertex == null || otherVertex == null || theEdge == null)
            throw new IllegalArgumentException("addEdge parameters cannot be null");

        // es con peso? debe tener implementado el metodo double getWeight()
        if (isWeighted) {
            // reflection
            Class<? extends Object> c = theEdge.getClass();
            try {
                c.getDeclaredMethod("getWeight");
            } catch (NoSuchMethodException | SecurityException e) {
                throw new RuntimeException(
                        type + " is weighted but the method double getWeighed() is not declared in theEdge");
            }
        }

        if (! acceptSelfLoop && aVertex.equals(otherVertex)) {
            throw new RuntimeException(String.format("%s does not accept self loops between %s and %s" ,
                    type, aVertex, otherVertex) );
        }

        // if any of the vertex is not presented, the node is created automatically
        addVertex(aVertex);
        addVertex(otherVertex);
    }


    @Override
    public boolean hasEdge(V fromVertex, V toVertex, E edge) {
        if (fromVertex == null || toVertex == null || edge == null) {
            throw new RuntimeException("hasEdges called with at least one null parameter");
        }

        Collection<InternalEdge> edges = adjacencyList.get(fromVertex);
        if (edges == null) {
            return false;
        }

        for (InternalEdge e : edges) {
            if (e.target.equals(toVertex) && e.edge.equals(edge)) {
                return true;
            }
        }

        return false;
    }

    //Debo recorrer el grafo, sumar las aritas entrantes en los vertices que son canciones
    //y anadir a mi grafo saliente aquellos vertices que cumplan las condicion con sus
    //aristas y vertices (personas) adyacentes.



    @Override
    public GraphService<V, E> popularSubgraph(Integer popularThreshold) {
        if(!isDirected || !isWeighted) throw new IllegalArgumentException();
        Map<V,Integer> map = new HashMap<>();
        GraphService<V, E> graph = GraphFactory.create(Multiplicity.SIMPLE, EdgeMode.DIRECTED, SelfLoop.NO, Weight.YES, Storage.SPARSE);
        for(V v : getVertices()){
            for (InternalEdge edge : adjacencyList.get(v)){
                try {
                    Method fn = edge.edge.getClass().getMethod("getWeight");
                    if(map.containsKey(edge.target)){
                        map.put(edge.target, map.get(edge.target) + (int) fn.invoke(edge.edge));
                    } else {
                        map.put(edge.target,(int) fn.invoke(edge.edge));
                    }
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        for(V vertex : getVertices()){
            for (InternalEdge edge : getAdjacencyList().get(vertex)){
                if(map.get(edge.target) >= popularThreshold){
                    if(!graph.getVertices().contains(edge.target)){
                        graph.addVertex(edge.target);
                    }
                    if(!graph.getVertices().contains(vertex)){
                        graph.addVertex(vertex);
                    }
                    graph.addEdge(vertex, edge.target, edge.edge);
                }
            }
        }
        /*
        for(V vertex : getVertices()){
            for (InternalEdge edge : getAdjacencyList().get(vertex)){
                if(countN(edge.target) >= popularThreshold){
                    if(!graph.getVertices().contains(edge.target)){
                        graph.addVertex(edge.target);
                    }
                    if(!graph.getVertices().contains(vertex)){
                        graph.addVertex(vertex);
                    }
                    graph.addEdge(vertex, edge.target, edge.edge);
                }
            }
        }
         */
        return graph;
    }

    //Como el grafo es dirigido y bipartito, se que las personas solo van a estar apuntando a canciones
    //Debe saber de que tipo son vertices --> usamos reflection
    //Modificamos el inDegree visto en clase para poder sumar los pesos de las aristas entrantes
    //a los vertices de las canciones.
    private int countN(V vertex) {
        //vertex es una cancion
        int count = 0;
        for(V v : getVertices()){
            for (InternalEdge edge : getAdjacencyList().get(v)){
                if(edge.target.equals(vertex)){
                    try {
                        Method fn = edge.edge.getClass().getMethod("getWeight");
                        count += (int) fn.invoke(edge.edge);
                    }
                    catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
            }
        }
        return count;
    }

    class InternalEdge {
        E edge;
        V target;

        InternalEdge(E propEdge, V target) {
            this.target = target;
            this.edge = propEdge;
        }

        @Override
        public int hashCode() {
            return target.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            @SuppressWarnings("unchecked")
            InternalEdge aux = (InternalEdge) obj;

            return ((edge == null && aux.edge == null) || (edge != null && edge.equals(aux.edge)))
                    && target.equals(aux.target);
        }

        @Override
        public String toString() {
            return String.format("-[%s]-(%s)", edge, target);
        }
    }


    public static void main(String[] args) {

    }


}