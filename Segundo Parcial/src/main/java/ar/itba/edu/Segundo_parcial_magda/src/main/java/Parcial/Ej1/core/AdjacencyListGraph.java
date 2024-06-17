package Parcial.Ej1.core;

import java.util.*;
import java.util.Map.Entry;

abstract public class AdjacencyListGraph<V, E> implements GraphService<V, E> {

    private boolean isSimple;
    protected boolean isDirected;
    private boolean acceptSelfLoop;
    private boolean isWeighted;
    protected String type;

    // HashMap no respeta el orden de insercion. En el testing considerar eso
    private Map<V, Collection<InternalEdge>> adjacencyList = new HashMap<>();

    // respeta el orden de llegada y facilita el testing
    //	private Map<V,Collection<InternalEdge>> adjacencyList= new LinkedHashMap<>();

    protected Map<V, Collection<AdjacencyListGraph<V, E>.InternalEdge>> getAdjacencyList() {
        return adjacencyList;
    }

    protected AdjacencyListGraph(boolean isSimple, boolean isDirected, boolean acceptSelfLoop, boolean isWeighted) {
        this.isSimple = isSimple;
        this.isDirected = isDirected;
        this.acceptSelfLoop = acceptSelfLoop;
        this.isWeighted = isWeighted;
        this.type = String.format("%s %sWeighted %sGraph with %sSelfLoop",
                isSimple ? "Simple" : "Multi", isWeighted ? "" : "Non-",
                isDirected ? "Di" : "", acceptSelfLoop ? "" : "No ");
    }

    public boolean getAcceptSelfLoop() {
        return acceptSelfLoop;
    }

    public boolean getIsSimple() {
        return isSimple;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void addVertex(V aVertex) {
        if (aVertex == null)
            throw new IllegalArgumentException("addVertex parameter cannot be null");
        // no edges yet
        getAdjacencyList().putIfAbsent(aVertex,
                new ArrayList<InternalEdge>());
    }

    @Override
    public int numberOfVertices() {
        return getVertices().size();
    }

    @Override
    public Collection<V> getVertices() {
        return getAdjacencyList().keySet();
    }

    @Override
    public int numberOfEdges() {
        int rta = 0;
        for (Entry<V, ? extends Collection<InternalEdge>> entry : getAdjacencyList().entrySet())
            rta += entry.getValue().size();
        if (!isDirected)
            return rta / 2;
        return rta;
    }

    private Collection<UnpackedInternalEdge<V, E>> getEdges() {
        ArrayList<UnpackedInternalEdge<V, E>> e = new ArrayList<>();
        for (Entry<V, ? extends Collection<InternalEdge>> entry : getAdjacencyList().entrySet()) {
            Collection<InternalEdge> auxi = entry.getValue();
            for (InternalEdge internalE : auxi)
                e.add(new UnpackedInternalEdge<V, E>(entry.getKey(), internalE.edge, internalE.target));
        }
        return e;
    }

    protected boolean existsVertex(V aVertex) {
        return getAdjacencyList().containsKey(aVertex);
    }

    @Override
    public void addEdge(V aVertex, V otherVertex, E theEdge) {
        // validation!!!!
        if (aVertex == null || otherVertex == null || theEdge == null)
            throw new IllegalArgumentException("addVertex parameter cannot be null");
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

        if (!acceptSelfLoop && aVertex.equals(otherVertex)) {
            throw new RuntimeException(String.format("%s does not accept self loops between %s and %s",
                    type, aVertex, otherVertex));
        }

        // if any of the vertex is not presented, the node is created automatically
        addVertex(aVertex);
        addVertex(otherVertex);


    }


    @Override
    public int degree(V aVertex) {
        if (aVertex == null || !existsVertex(aVertex))
            throw new IllegalArgumentException("vertex parameter does not exist");
        if (isDirected)
            throw new IllegalArgumentException("degree expects an undirected graph");
        Collection<AdjacencyListGraph<V, E>.InternalEdge> adjList = getAdjacencyList().get(aVertex);
        return adjList.size();
    }


    @Override
    public int inDegree(V aVertex) {
        if (aVertex == null || !existsVertex(aVertex))
            throw new IllegalArgumentException("vertex parameter does not exist");
        if (!isDirected)
            throw new IllegalArgumentException("inDegree expects a directed graph");
        // ir por todos los nodos que existan para ver si alguno lo apunta a el r
        int rta = 0;
        for (V otherV : getAdjacencyList().keySet()) {
            Collection<InternalEdge> adjListOther = getAdjacencyList().get(otherV);
            for (InternalEdge internalEdge : adjListOther) {
                if (internalEdge.target.equals(aVertex))
                    rta++;
            }
        }
        return rta;
    }


    @Override
    public int outDegree(V aVertex) {
        if (aVertex == null || !existsVertex(aVertex))
            throw new IllegalArgumentException("vertex parameter does not exist");
        if (!isDirected)
            throw new IllegalArgumentException("outDegree expects a directed graph");
        Collection<InternalEdge> adjList = getAdjacencyList().get(aVertex);
        return adjList.size();
    }

    public Iterable<V> getBFS(V startNode) {
        if (startNode == null || !existsVertex(startNode))
            throw new IllegalArgumentException("vertex parameter does not exist");
        return new Iterable<V>() {
            @Override
            public Iterator<V> iterator() {
                return new myIteratorBFS(startNode);
            }
        };
    }

    // auxiliar
    class myIteratorBFS implements Iterator<V> {
        Set<V> visited = new HashSet<V>();
        Queue<V> theQueue = new LinkedList<>();

        myIteratorBFS(V startNode) {
            theQueue.add(startNode);
        }

        @Override
        public boolean hasNext() {
            while (!theQueue.isEmpty()) {
                V current = theQueue.peek();  // NO LO SACO!!!
                if (!visited.contains(current))
                    return true;
                // sino, buscar otro
                theQueue.poll();   // descartado
            }
            return false;  // no hubo manera
        }

        @Override
        public V next() {
            V current = theQueue.poll();
            visited.add(current);
            Collection<InternalEdge> adjListOther = getAdjacencyList().get(current);
            for (InternalEdge internalEdge : adjListOther) {
                if (!visited.contains(internalEdge.target))
                    theQueue.add(internalEdge.target);
            }
            return current;
        }
    }

    @Override
    public Iterable<V> getDFS(V startNode) {
        if (startNode == null || !existsVertex(startNode))
            throw new IllegalArgumentException("vertex parameter does not exists");
        return new Iterable<V>() {
            @Override
            public Iterator<V> iterator() {
                return new myIteratorDFS(startNode);
            }
        };
    }


    // auxiliar
    class myIteratorDFS implements Iterator<V> {
        Set<V> visited = new HashSet<V>();
        Stack<V> theStack = new Stack<>();

        myIteratorDFS(V startNode) {
            theStack.add(startNode);
        }

        @Override
        public boolean hasNext() {
            while (!theStack.isEmpty()) {
                V current = theStack.peek();  // NO LO SACO!!!
                if (!visited.contains(current))
                    return true;
                // sino, buscar otro
                theStack.pop();   // descartado
            }
            return false;  // no hubo manera
        }

        @Override
        public V next() {
            V current = theStack.pop();
            visited.add(current);
            Collection<InternalEdge> adjListOther = getAdjacencyList().get(current);
            for (InternalEdge internalEdge : adjListOther) {
                if (!visited.contains(internalEdge.target))
                    theStack.add(internalEdge.target);
            }
            return current;
        }
    }

    class InternalEdge {
        E edge;
        V target;

        InternalEdge(E propEdge, V target) {
            this.target = target;
            this.edge = propEdge;
        }

        @Override
        public boolean equals(Object obj) {
            @SuppressWarnings("unchecked")
            InternalEdge aux = (InternalEdge) obj;
            return ((edge == null && aux.edge == null) || (edge != null && edge.equals(aux.edge)))
                    && target.equals(aux.target);
        }

        @Override
        public int hashCode() {
            return target.hashCode();
        }

        @Override
        public String toString() {
            return String.format("-[%s]-(%s)", edge, target);
        }
    }

}
