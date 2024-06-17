package Graphs.core;

import java.lang.reflect.Method;
import java.util.*;

import Graphs.core_service.GraphService;
import xPractica.ParcialX.Ej1.use.WeightedEdge;

abstract public class AdjacencyListGraph<V, E> implements GraphService<V, E> {

    private final boolean isSimple;
    protected boolean isDirected;
    private final boolean acceptSelfLoop;
    private final boolean isWeighted;
    protected String type;

    // HashMap no respeta el orden de inserción. En el testing considerar eso
    private final Map<V, Collection<InternalEdge>> adjacencyList = new HashMap<>();

    // respeta el orden de llegada y facilita el testing
    // private Map<V,Collection<InternalEdge>> adjacencyList= new LinkedHashMap<>();

    protected Map<V, Collection<InternalEdge>> getAdjacencyList() {
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


    @Override
    public String getType() {
        return type;
    }

    @Override
    public void addVertex(V aVertex) {

        if (aVertex == null)
            throw new IllegalArgumentException(Messages.getString("addVertexParamCannotBeNull"));

        // no edges yet
        getAdjacencyList().putIfAbsent(aVertex, new ArrayList<>());
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
        return isDirected ? edgeCount() : edgeCount() / 2;
    }

    private int edgeCount() {
        int count = 0;
        for (V vertex : adjacencyList.keySet()) {
            for (InternalEdge ignored : adjacencyList.get(vertex))
                count++;
        }
        return count;
    }


    @Override
    public void addEdge(V aVertex, V otherVertex, E theEdge) {

        // validation!!!!
        if (aVertex == null || otherVertex == null || theEdge == null)
            throw new IllegalArgumentException(Messages.getString("addEdgeParamCannotBeNull"));

        // ¿es con peso? Debe tener implementado el método double getWeight()
        if (isWeighted) {
            // reflection
            Class<?> c = theEdge.getClass();
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
    public boolean removeVertex(V aVertex) {
        if (!getAdjacencyList().containsKey(aVertex))
            return false;
        getAdjacencyList().remove(aVertex);
        for (Collection<InternalEdge> lists : getAdjacencyList().values()) {
            Iterator<InternalEdge> it = lists.iterator();
            while (it.hasNext()) {
                InternalEdge e = it.next();
                if (e.target.equals(aVertex)) {
                    it.remove();
                }
            }
        }
        return true;
    }

    @Override
    public boolean removeEdge(V aVertex, V otherVertex) {
        if (aVertex == null || otherVertex == null)
            throw new IllegalArgumentException(Messages.getString("removeEdgeParamCannotBeNull"));

        if (!isSimple)
            throw new RuntimeException("this method isn't available for multi-graphs");

        if (!adjacencyList.containsKey(aVertex) || !adjacencyList.containsKey(otherVertex))
            return false;

        if (!isDirected)
            adjacencyList.get(otherVertex).removeIf(edge -> edge.target.equals(aVertex));

        return adjacencyList.get(aVertex).removeIf(edge -> edge.target.equals(otherVertex));
    }


    @Override
    public boolean removeEdge(V aVertex, V otherVertex, E theEdge) {
        if (aVertex == null || otherVertex == null || theEdge == null)
            throw new IllegalArgumentException(Messages.getString("removeEdgeParamCannotBeNull"));

        if (!adjacencyList.containsKey(aVertex) || !adjacencyList.containsKey(otherVertex))
            return false;

        if (isSimple) return removeEdge(aVertex, otherVertex);

        if (!isDirected)
            adjacencyList.get(otherVertex).removeIf(edge -> edge.edge.equals(theEdge) && edge.target.equals(aVertex));

        return adjacencyList.get(aVertex).removeIf(edge -> edge.edge.equals(theEdge) && edge.target.equals(otherVertex));
    }


    @Override
    public void dump() {
        for (V vertex : adjacencyList.keySet()) {
            System.out.print(vertex + ": ");
            for (InternalEdge internalEdge : adjacencyList.get(vertex))
                System.out.printf("-> {%s} ", internalEdge);
            System.out.println();
        }
    }


    @Override
    public int degree(V aVertex) {
        if (aVertex == null)
            throw new IllegalArgumentException(Messages.getString("vertexParamError"));

        if (isDirected)
            throw new IllegalArgumentException(Messages.getString("degreeGraphParameterError"));

        return adjacencyList.getOrDefault(aVertex, new ArrayList<>()).size();
    }


    @Override
    public int inDegree(V aVertex) {
        if (aVertex == null)
            throw new IllegalArgumentException(Messages.getString("vertexParamError"));

        if (!isDirected)
            throw new IllegalArgumentException(Messages.getString("inDegreeGraphParameterError"));

        int count = 0;

        for (V vertex : adjacencyList.keySet()) {
            for (InternalEdge internalEdge : adjacencyList.get(vertex)) {
                if (internalEdge.target.equals(aVertex))
                    count++;
            }
        }
        return count;
    }


    @Override
    public int outDegree(V aVertex) {
        if (aVertex == null)
            throw new IllegalArgumentException(Messages.getString("vertexParamError"));

        if (!isDirected)
            throw new IllegalArgumentException(Messages.getString("outDegreeGraphParameterError"));

        return adjacencyList.getOrDefault(aVertex, new ArrayList<>()).size();
    }

    @Override
    public void printAllPaths(V start, V end) {
        if (start == null || !adjacencyList.containsKey(start) || end == null || !adjacencyList.containsKey(end))
            throw new IllegalArgumentException(Messages.getString("vertexParamError"));

        if (acceptSelfLoop || !isSimple)
            throw new IllegalArgumentException("this method isn't available for self-loop graphs or multi-graphs");

        Set<V> visited = new HashSet<>();
        List<V> path = new ArrayList<>();

        printAllPaths(start, end, visited, path);
    }

    private void printAllPaths(V start, V end, Set<V> visited, List<V> path) {
        // proceso el nodo
        path.add(start);
        visited.add(start);

        if (start.equals(end)) {
            System.out.println(path);

            // deshago porque no voy a pasar por flujo normal
            visited.remove(end);
            path.remove(end);
            return;
        }

        for (InternalEdge internalEdge : adjacencyList.get(start)) {
            if (!visited.contains(internalEdge.target)) {
                printAllPaths(internalEdge.target, end, visited, path);
            }
        }

        // lo deshago
        visited.remove(start);
        path.remove(start);
    }

    @Override
    public boolean isBipartite() {
        if (adjacencyList.isEmpty())
            return true;

        Map<V, Color> colors = new HashMap<>();

        for (V vertex : adjacencyList.keySet()) {
            colors.putIfAbsent(vertex, Color.RED);

            for (InternalEdge internalEdge : adjacencyList.get(vertex)) {

                if (!colors.containsKey(internalEdge.target))
                    colors.put(internalEdge.target, colors.get(vertex).opposite());

                else if (colors.get(internalEdge.target) == colors.get(vertex))
                    return false;
            }
        }
        return true;
    }

    @Override
    public boolean hasCycles() {
        Set<V> visited = new HashSet<>();

        for (V vertex : adjacencyList.keySet()) {
            if (!visited.contains(vertex)) {
                if (hasCycles(vertex, visited, null))
                    return true;
            }
        }

        return false;
    }

    private boolean hasCycles(V vertex, Set<V> visited, V parent) {
        visited.add(vertex);

        for (InternalEdge internalEdge : adjacencyList.get(vertex)) {
            V adjacentVertex = internalEdge.target;

            if (!visited.contains(adjacentVertex)) {
                if (hasCycles(adjacentVertex, visited, vertex))
                    return true;
            } else if (!adjacentVertex.equals(parent)) {
                return true;
            }
        }

        return false;
    }

    @Override
    // ¡Dijsktra exige que los pesos sean positivos!
    public DijkstraPath<V, E> dijsktra(V source) {
        PriorityQueue<NodePQ> pq = new PriorityQueue<>();
        //stores shortest distance from source to every vertex
        Map<V, Integer> costo = new HashMap<>();
        Map<V, V> prev = new HashMap<>();
        // empieza vacío
        Set<V> nodesVisited = new HashSet<>();
        // inicialización
        for (V aV : getAdjacencyList().keySet()) {
            if (aV.equals(source)) {
                pq.add(new NodePQ(source, 0));
                costo.put(source, 0);
            } else {
                costo.put(aV, Integer.MAX_VALUE);
            }
            prev.put(aV, null);
        }
        while (!pq.isEmpty()) {
            AdjacencyListGraph<V, E>.NodePQ current = pq.poll(); // el menor
            if (nodesVisited.contains(current.vertex)) // ya lo procesé
                continue;
            // ¡a procesarlo!
            nodesVisited.add(current.vertex);
            // ahora recorrer todos los ejes incidentes a current
            Collection<AdjacencyListGraph<V, E>.InternalEdge> adjList = getAdjacencyList().get(current.vertex);
            for (InternalEdge neighbor : adjList) {
                // Sí fue visitado seguir. Esto también excluye los self loops...
                if (nodesVisited.contains(neighbor.target)) {
                    continue;
                }
                // invocando a getWeight (se ha validado en inserción)
                int weight;
                // ¿peso de ese eje?
                try {
                    Method fn = neighbor.edge.getClass().getMethod("getWeight");
                    weight = (int) fn.invoke(neighbor.edge);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                // verificación
                if (weight < 0)
                    throw new IllegalArgumentException(String.format(Messages.getString("dijkstraWithNegativeWeight"), current.vertex, neighbor.target, weight));
                // ¿cuál sería el costo de neighbor viniendo desde current?
                int newCosto = costo.get(current.vertex) + weight;
                // ¿es una mejora?
                if (newCosto < costo.get(neighbor.target)) {
                    // insertar neighbor con ese valor mejorado
                    costo.put(neighbor.target, newCosto);
                    pq.add(new NodePQ(neighbor.target, newCosto));
                    // armar camino
                    prev.put(neighbor.target, current.vertex);
                }
            }
        }
        return new DijkstraPath<>(costo, prev);
    }

    @Override
    public void printBFS(V source) {
        if (source == null || !adjacencyList.containsKey(source))
            throw new IllegalArgumentException(Messages.getString("vertexParamError"));

        Set<V> visited = new HashSet<>();

        Queue<V> theQueue = new LinkedList<>();
        theQueue.add(source);

        while (!theQueue.isEmpty()) {
            V current = theQueue.poll();

            if (!visited.contains(current)) {
                visited.add(current);
                System.out.print(current + " ");
            }

            for (InternalEdge internalEdge : adjacencyList.get(current)) {
                if (!visited.contains(internalEdge.target))
                    theQueue.add(internalEdge.target);
            }
        }
    }

    public Iterable<V> getBFS(V source) {
        if (source == null || !adjacencyList.containsKey(source))
            throw new IllegalArgumentException(Messages.getString("vertexParamError"));

        return () -> new IteratorBFS(source);
    }

    class IteratorBFS implements Iterator<V> {

        private final Set<V> visited = new HashSet<>();

        private final Queue<V> theQueue = new LinkedList<>();

        private IteratorBFS(V source) {
            theQueue.add(source);
        }

        @Override
        public boolean hasNext() {
            while (!theQueue.isEmpty()) {
                V current = theQueue.peek();

                if (!visited.contains(current)) return true;

                // Sino, buscar otro.
                theQueue.poll(); // Descartado
            }
            return false;
        }

        @Override
        public V next() {
            V current = theQueue.poll();
            visited.add(current);

            for (InternalEdge internalEdge : adjacencyList.get(current)) {
                if (!visited.contains(internalEdge.target))
                    theQueue.add(internalEdge.target);
            }

            return current;
        }
    }

    @Override
    public void printDFS(V source) {
        if (source == null || !adjacencyList.containsKey(source))
            throw new IllegalArgumentException(Messages.getString("vertexParamError"));

        Set<V> visited = new HashSet<>();

        // printDFSIter(source, visited);

        printDFSRec(source, visited);
    }

    private void printDFSIter(V source, Set<V> visited) {
        Stack<V> theStack = new Stack<>();
        theStack.push(source);

        while (!theStack.isEmpty()) {
            V current = theStack.pop();

            if (!visited.contains(current)) {
                visited.add(current);
                System.out.print(current + " ");
            }

            for (InternalEdge internalEdge : adjacencyList.get(current)) {
                if (!visited.contains(internalEdge.target))
                    theStack.push(internalEdge.target);
            }
        }
    }

    private void printDFSRec(V current, Set<V> visited) {
        if (visited.contains(current))
            return;

        visited.add(current);
        System.out.print(current + " ");

        for (InternalEdge internalEdge : adjacencyList.get(current)) {
            if (!visited.contains(internalEdge.target))
                printDFSRec(internalEdge.target, visited);
        }
    }

    public Iterable<V> getDFS(V source) {
        if (source == null || !adjacencyList.containsKey(source))
            throw new IllegalArgumentException(Messages.getString("vertexParamError"));

        return () -> new IteratorDFS(source);
    }

    class IteratorDFS implements Iterator<V> {

        private final Set<V> visited = new HashSet<>();

        private final Stack<V> theStack = new Stack<>();

        private IteratorDFS(V source) {
            theStack.push(source);
        }

        @Override
        public boolean hasNext() {
            while (!theStack.isEmpty()) {
                V current = theStack.peek();

                if (!visited.contains(current)) return true;

                // Sino, buscar otro.
                theStack.pop(); // Descartado
            }
            return false;
        }

        @Override
        public V next() {
            V current = theStack.pop();
            visited.add(current);

            for (InternalEdge internalEdge : adjacencyList.get(current)) {
                if (!visited.contains(internalEdge.target))
                    theStack.push(internalEdge.target);
            }

            return current;
        }
    }

    class NodePQ implements Comparable<NodePQ> {
        V vertex;
        Double distance;

        public NodePQ(V vertex, double distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        @Override
        public int compareTo(NodePQ o2) {
            return Double.compare(distance, o2.distance);
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
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null)
                return false;
            if (getClass() != o.getClass())
                return false;

            @SuppressWarnings("unchecked")
            InternalEdge aux = (InternalEdge) o;

            return ((edge == null && aux.edge == null) || (edge != null && edge.equals(aux.edge))) && target.equals(aux.target);
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

    enum Color {
        RED, BLUE;

        Color opposite() {
            return this == RED ? BLUE : RED;

        }
    }

    public static void main(String[] args) {

        GraphService<String, WeightedEdge> g = GraphFactory.create(Multiplicity.SIMPLE, EdgeMode.UNDIRECTED, SelfLoop.YES, Weight.YES, Storage.SPARSE);

        g.addEdge("HNL", "LAX", new WeightedEdge(2555));
        g.addEdge("LAX", "DFW", new WeightedEdge(1233));
        g.addEdge("DFW", "MIA", new WeightedEdge(1120));
        g.addEdge("MIA", "PVD", new WeightedEdge(1205));
        g.addEdge("PVD", "ORD", new WeightedEdge(849));
        g.addEdge("ORD", "SFO", new WeightedEdge(1843));
        g.addEdge("SFO", "LAX", new WeightedEdge(337));
        g.addEdge("LAX", "ORD", new WeightedEdge(1743));
        g.addEdge("ORD", "DFW", new WeightedEdge(802));
        g.addEdge("DFW", "LGA", new WeightedEdge(1387));
        g.addEdge("PVD", "LGA", new WeightedEdge(142));
        g.addEdge("MIA", "LGA", new WeightedEdge(1099));
    }


}
