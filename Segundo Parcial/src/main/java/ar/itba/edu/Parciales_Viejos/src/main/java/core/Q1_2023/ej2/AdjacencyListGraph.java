package core.Q1_2023.ej2;

import java.util.*;
import org.junit.jupiter.api.Assertions;


public class AdjacencyListGraph<V, E> implements GraphService<V, E> {

    private boolean isSimple;
    protected boolean isDirected;
    private boolean acceptSelfLoop;
    private boolean isWeighted;
    private Map<V, Collection<InternalEdge>> adjacencyList = new HashMap<>();

    protected Map<V, Collection<InternalEdge>> getAdjacencyList() {
        return adjacencyList;
    }

    public Collection<V> getVertices() {
        return getAdjacencyList().keySet();
    }

    protected AdjacencyListGraph(boolean isSimple, boolean isDirected, boolean acceptSelfLoop, boolean isWeighted) {
        this.isSimple = isSimple;
        this.isDirected = isDirected;
        this.acceptSelfLoop = acceptSelfLoop;
        this.isWeighted = isWeighted;
    }


    public static void main(String[] args) {
        test();
    }
    public static void test() {
        GraphService<Character, EmptyEdgeProp> undirectedMultipleLoopNoWeight;
        // GraphService<Character, EmptyEdgeProp> undirectedSimpleLoopNoWeight;
        /*undirectedSimpleLoopNoWeight = new GraphBuilder<Character, EmptyEdgeProp>()
                .withStorage(GraphService.Storage.SPARSE)
                .withAcceptSelfLoop(GraphService.SelfLoop.YES)
                .withAcceptWeight(GraphService.Weight.NO)
                .withDirected(GraphService.EdgeMode.UNDIRECTED)
                .withMultiplicity(GraphService.Multiplicity.SIMPLE)
                .build();*/
        undirectedMultipleLoopNoWeight = new GraphBuilder<Character, EmptyEdgeProp>()
                .withStorage(GraphService.Storage.SPARSE)
                .withAcceptSelfLoop(GraphService.SelfLoop.YES)
                .withAcceptWeight(GraphService.Weight.NO)
                .withDirected(GraphService.EdgeMode.UNDIRECTED)
                .withMultiplicity(GraphService.Multiplicity.MULTIPLE)
                .build();

        undirectedMultipleLoopNoWeight.addEdge('1','2',new EmptyEdgeProp());
        undirectedMultipleLoopNoWeight.addEdge('1','3',new EmptyEdgeProp());
        undirectedMultipleLoopNoWeight.addEdge('3','4',new EmptyEdgeProp());
        undirectedMultipleLoopNoWeight.addEdge('2','3',new EmptyEdgeProp());
        System.out.println("----------------------------------------------------------------");
        System.out.println(undirectedMultipleLoopNoWeight.connectedComponentsQty());
        Assertions.assertEquals(undirectedMultipleLoopNoWeight.connectedComponentsQty(),1);

    }

    public int connectedComponentsQty() {
        if(isDirected) throw new IllegalArgumentException();
        Set<V> set = new HashSet<>();
        int amount = 0;
        Queue<V> queue = new LinkedList<>();

        for(V v: getVertices()) {
            if(!set.contains(v)) {
                amount++;
                queue.add(v);
                V currentVertex = v;

                while (!queue.isEmpty()){
                    currentVertex = queue.remove();
                    for (InternalEdge e : adjacencyList.get(currentVertex)) {
                        if(!set.contains(e.target))
                            queue.add(e.target);
                    }
                    set.add(currentVertex);
                }

            }
        }
        return amount;
    }

    public void addVertex(V aVertex) {

        if (aVertex == null)
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
                throw new RuntimeException("Graph is weighted but the method double getWeighed() is not declared in theEdge");
            }
        }

        if (!acceptSelfLoop && aVertex.equals(otherVertex)) {
            throw new RuntimeException(String.format("Graph does not accept self loops between %s and %s",
                    aVertex, otherVertex));
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
