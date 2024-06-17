package Grafos.core;


import Grafos.core_service.GraphService;

import java.util.*;

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
        getAdjacencyList().putIfAbsent(aVertex, new ArrayList<InternalEdge>());
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
        int size = 0;
        for (Collection<InternalEdge> e : getAdjacencyList().values()) {
            size += e.size();
        }
        if (!isDirected) {
            size /= 2;
        }
        return size;
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


    @Override
    public boolean removeVertex(V aVertex) {
        if (aVertex == null) {
            throw new IllegalArgumentException("removeVertexParamCannotBeNull");
        }
        if (getAdjacencyList().get(aVertex) == null) {
            return false;
        }
        if (isDirected) {
            adjacencyList.remove(aVertex);
            for (Map.Entry<V, Collection<InternalEdge>> entry : adjacencyList.entrySet()) {
                entry.getValue().removeIf(edge -> edge.target.equals(aVertex));
            }
        } else {
            for (InternalEdge edge : adjacencyList.get(aVertex)) {
                if (!edge.target.equals(aVertex)) {
                    adjacencyList.get(edge.target).removeIf(otherEdge -> otherEdge.target.equals(aVertex));
                }
            }
            adjacencyList.remove(aVertex);
        }
        return true;
    }

    @Override
    public boolean removeEdge(V aVertex, V otherVertex) {
        if(!isSimple) {
            throw new RuntimeException("this method isn't available for multigraphs");
        }
        if(!adjacencyList.containsKey(aVertex) || !adjacencyList.containsKey(otherVertex)) {
            return false;
        }
        boolean ans = adjacencyList.get(aVertex).removeIf(edge -> edge.target.equals(otherVertex));
        if(!isDirected) {
            adjacencyList.get(otherVertex).removeIf(edge -> edge.target.equals(aVertex));
        }
        return ans;
    }


    @Override
    public boolean removeEdge(V aVertex, V otherVertex, E theEdge) {
        if(!adjacencyList.containsKey(aVertex) || !adjacencyList.containsKey(otherVertex)) {
            return false;
        }
        if(isSimple) {
            return removeEdge(aVertex, otherVertex);
        }
        boolean ans = adjacencyList.get(aVertex).removeIf(edge -> edge.edge.equals(theEdge) && edge.target.equals(otherVertex));
        if(!isDirected) {
            adjacencyList.get(otherVertex).removeIf(edge -> edge.edge.equals(theEdge) && edge.target.equals(aVertex));
        }
        return ans;
    }


    @Override
    public void dump() {
        System.out.println(type); // por ser un MAP hay que recorrer TODO para saber donde estan los buckets
        // habilitados (nodos)
        // o sea, es peor que O(N)
        // con ArrayList hubiera sigo O(N), pero habria que navegar para las operaciones
        System.out.println(type);
        System.out.println("Vertices:");
        for (V aV : getAdjacencyList().keySet())
            System.out.print(String.format("(%s) ", aV));
        System.out.println();
        System.out.println("Edges:");
        for (Map.Entry<V, ? extends Collection<InternalEdge>> entry : getAdjacencyList().entrySet()) {
            Collection<InternalEdge> auxi = entry.getValue();
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
        if (isDirected) {
            throw new IllegalArgumentException("Only non-directed graphs have degree method");
        }
        if (aVertex == null) {
            throw new RuntimeException("vertex cannot be null");
        }
        if (adjacencyList.get(aVertex) == null) {
            throw new RuntimeException("vertex not found");
        }
        //Collection<InternalEdge> col = adjacencyList.get(aVertex);
        return adjacencyList.get(aVertex).size();
    }


    @Override
    public int inDegree(V aVertex) {
        if (!isDirected) {
            throw new IllegalArgumentException("Only directed graphs have inDegree method");
        }
        if (aVertex == null) {
            throw new RuntimeException("vertex cannot be null");
        }
        int size = 0;
        for (Collection<InternalEdge> e : getAdjacencyList().values()) {
            for (InternalEdge ie : e) {
                if (ie.target.equals(aVertex)) {
                    size++;
                }
            }
        }
        return size;
    }



    @Override
    public int outDegree(V aVertex) {
        if (!isDirected) {
            throw new IllegalArgumentException("Only directed graphs have outDegree method");
        }
        if (aVertex == null) {
            throw new RuntimeException("vertex cannot be null");
        }
        if (adjacencyList.get(aVertex) == null) {
            throw new RuntimeException("vertex not found");
        }
        return adjacencyList.get(aVertex).size();
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

    @Override
    public void printBFS(V vertex){
        Queue<V> queue = new LinkedList<>();
        Set<V> visited = new HashSet<>();

        queue.add(vertex);
        V currentVertex = vertex;

        while (!queue.isEmpty()){
            currentVertex = queue.remove();

            for (InternalEdge e : adjacencyList.get(currentVertex)) {
                if(!visited.contains(e.target))
                    queue.add(e.target);
            }

            if(visited.add(currentVertex)){
                System.out.println(currentVertex);
            }

        }

    }
        @Override
        public void printDFS(V vertex){
            Stack<V> stack = new Stack<>();
            Set<V> visited = new HashSet<>();

            stack.push(vertex);
            V currentVertex = vertex;

            while (!stack.isEmpty()){
                currentVertex = stack.pop();

                for (InternalEdge e : adjacencyList.get(currentVertex)) {
                    if(!visited.contains(e.target))
                        stack.push(e.target);
                }

                if(visited.add(currentVertex)){
                    System.out.println(currentVertex);
                }

            }
        }

        Iterator<V> DFSIterator(V vertex){
            Stack<V> stack = new Stack<>();
            Set<V> visited = new HashSet<>();
            stack.push(vertex);

            return new Iterator<V>() {
                V currentVertex;

                @Override
                public boolean hasNext() {
                    return !stack.isEmpty();
                }

                @Override
                public V next() {
                    if(stack.isEmpty()) throw new NoSuchElementException();

                    currentVertex = stack.pop();

                    for (InternalEdge e : adjacencyList.get(currentVertex)) {
                        if(!visited.contains(e.target))
                            stack.push(e.target);
                    }

                    if(visited.add(currentVertex)){
                        return currentVertex;
                    }
                    return next();
                }
            };
        }

        public Iterable<V>  getDFS(V vertex){
            return new Iterable<V>(){
                @Override
                public Iterator<V> iterator() {
                    return DFSIterator(vertex);
                }
            };
        }

    Iterator<V> BFSIterator(V vertex){
        Queue<V> queue = new LinkedList<>();
        Set<V> visited = new HashSet<>();
        queue.add(vertex);

        return new Iterator<V>() {
            V currentVertex;

            @Override
            public boolean hasNext() {
                return !queue.isEmpty();
            }

            @Override
            public V next() {
                if(queue.isEmpty()) throw new NoSuchElementException();

                currentVertex = queue.remove();

                for (InternalEdge e : adjacencyList.get(currentVertex)) {
                    if(!visited.contains(e.target))
                        queue.add(e.target);
                }

                if(visited.add(currentVertex)){
                    return currentVertex;
                }
                return next();
            }
        };
    }
        public Iterable<V>  getBFS(V vertex){

            return new Iterable<V>(){

                public Iterator<V> iterator() {
                    return BFSIterator(vertex);
                }
            };
        }

}


