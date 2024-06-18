package core;


import java.util.*;
import java.util.List;

import core_service.GraphService;

abstract public class AdjacencyListGraph<V, E> implements GraphService<V, E> {

    protected boolean isSimple;
    protected boolean isDirected;
    protected boolean acceptSelfLoop;
    protected boolean isWeighted;
    protected String type;

    // HashMap no respeta el orden de insercion. En el testing considerar eso
    private Map<V, Collection<InternalEdge>> adjacencyList = new HashMap<>();


    // respeta el orden de llegada y facilita el testing
    //	private Map<V,Collection<InternalEdge>> adjacencyList= new LinkedHashMap<>();

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


    protected void addEdgeToList(V aVertex, V otherVertex, E theEdge){
        Collection<InternalEdge> adjListSrc = getAdjacencyList().get(aVertex);
        adjListSrc.add(new InternalEdge(theEdge, otherVertex));
        if (!isDirected) {
            Collection<AdjacencyListGraph<V, E>.InternalEdge> adjListDst = getAdjacencyList().get(otherVertex);
            adjListDst.add(new InternalEdge(theEdge, aVertex));
        }
    }

    @Override
    public void addEdge(V aVertex, V otherVertex, E theEdge) {

        // validation!!!!
        if (aVertex == null || otherVertex == null || theEdge == null)
            throw new IllegalArgumentException(Messages.getString("addEdgeParamCannotBeNull"));

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


/*
* @TODo testear
 */
    @Override
    public boolean removeVertex(V aVertex) {
        if (!getAdjacencyList().containsKey(aVertex))
            return false;

        Collection<InternalEdge> removedList = getAdjacencyList().get(aVertex);

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

    /*
    * @TODO testear caso dirigido, caso dos vertices iguales.
     */
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
        System.out.println(type); // por ser un MAP hay que recorrer TODO para saber donde estan los buckets // habilitados (nodos) // o sea, es peor que O(N) // con ArrayList hubiera sigo O(N), pero habria que navegar para las operaciones System.out.println(type);
        System.out.println("Vertices:");
        for (V aV : getAdjacencyList().keySet()) System.out.print(String.format("(%s) ", aV));
        System.out.println();
        System.out.println("Edges:");
        for (Map.Entry<V, ? extends Collection<InternalEdge>> entry : getAdjacencyList().entrySet()) {
            Collection<AdjacencyListGraph<V, E>.InternalEdge> auxi = entry.getValue();
            for (InternalEdge internalE : auxi) {
                if (isDirected)
                    System.out.println(String.format("(%s) -%s-> (%s)", entry.getKey(), internalE.edge == null ? "" : internalE.edge, internalE.target));
                else { // pero lo va a imprimir 2 veces... Aca no hay simetria como en matrices
                     System.out.println(String.format("(%s) -%s- (%s)", entry.getKey(), internalE.edge == null ? "" : internalE.edge, internalE.target));
                }
            }
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

    protected boolean existsVertex(V aVertex) {
        return getAdjacencyList().containsKey(aVertex); }


    private void printBFSIter(V source){
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
    @Override
    public void printBFS(V source) {
        Iterator<V> it = new IteratorBFS(source);
        while (it.hasNext()) {
            System.out.print(it.next() + " ");
        }
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
    @Override
    public void printDFS(V source) {
        if (source == null || !adjacencyList.containsKey(source))
            throw new IllegalArgumentException(Messages.getString("vertexParamError"));

        Set<V> visited = new HashSet<>();

        // printDFSIter(source, visited);

        printDFSRec(source, visited);
    }

    public Iterable<V> getBFS(V source) {
        if (source == null || !adjacencyList.containsKey(source))
            throw new IllegalArgumentException(Messages.getString("vertexParamError"));

        return () -> new IteratorBFS(source);
    }

    public Iterable<V> getDFS(V source) {
        if (source == null || !adjacencyList.containsKey(source))
            throw new IllegalArgumentException(Messages.getString("vertexParamError"));

        return () -> new IteratorDFS(source);
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

    /*
    * @TODO leer con detenimiento hasCycle y testear
     */
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



    class InternalEdge {
        E edge;
        V target;

        InternalEdge(E propEdge, V target) {
            this.target = target;
            this.edge = propEdge;
        }

        @Override
        public boolean equals(Object obj) {

            if (this == obj){return true;}
            if (obj == null || getClass() != obj.getClass()){return false;}


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

/*
* @TODO no deberia guardar una copia de la lista. Sino, si se agrega algo, va a cambiar el itertador.
 */
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

    enum Color {
        RED, BLUE;

        Color opposite() {
            return this == RED ? BLUE : RED;

        }
    }



}
