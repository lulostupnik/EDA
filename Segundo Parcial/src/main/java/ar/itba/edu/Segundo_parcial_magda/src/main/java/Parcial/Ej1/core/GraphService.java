package Parcial.Ej1.core;
import java.util.Collection;

// same interface for graph, digraph, multigraph, weighted graph, etc

// V participa de hashing. Redefinir equals y hashcode para que detecte
// correctamente repetidos segun se desee

// E precisa la redefinicion de equals para que en remove lo encuentre 
// y lo pueda borrar. Actualmente no participa de un hashing. Esta encapsulado
// dentro de un objeto InternalEdge que lo contiene junto con el V destino. 
// Esa clase InternalEdge s� tiene hashcode y equals implementado.

public interface GraphService<V, E> {

    enum Multiplicity {SIMPLE, MULTIPLE};

    enum EdgeMode {UNDIRECTED, DIRECTED};

    enum SelfLoop {NO, YES};

    enum Weight {NO, YES};

    enum Storage {SPARSE, DENSE};

    // devuelve caracteristicas de la forma en que fue creado
    public String getType();

    // if exists lo ignora
    // else generate a new vertex
    public void addVertex(V aVertex);


    public int numberOfVertices();


    public Collection<V> getVertices();


    // parameters cannot be null
    // if any of the vertices is not present, the node is created automatically

    // in the case of a weighted graph, E might implements the method double getWeight()
    // otherwise an exception will be thrown

    // if the graph is not "multi" and there exists other edge with same source and target,
    // throws exception
    public void addEdge(V aVertex, V otherVertex, E theEdge);

    public int numberOfEdges();

    //	public Collection<Edge<V, E>> getEdges();
    // if directed or vertex does not exists: throw exception
    // if self loop contributes twice
    public int degree(V vertex);

    // if undirected or vertex does not exists: throw exception
    // if self loop contributes twice
    public int inDegree(V vertex);

    // if undirected or vertex does not exists: throw exception
    // if self loop contributes twice
    public int outDegree(V vertex);

    public Iterable<V> getBFS(V startVertex);

    public Iterable<V> getDFS(V startVertex);

    // Si el grafo es simple, no dirigido y sin self-loop:
    //  si tiene camino euleriano retorna TRUE, sino FALSE
    //Solo lanza excepcion si se lo invoca con un grafo :
    //   multi, o dirigido o  tiene self-loop
    boolean hasEulerianPath();

    //Si el grafo es simple, no dirigido y sin self-loop:
    //  si verifica Prop1 retorna TRUE, sino FALSE
    //Solo lanza excepcion si se lo invoca con un grafo :
    //   multi, o dirigido o tiene self-loop
    boolean checkProp1();

    //Si el grafo es simple, no dirigido y sin self-loop:
    //  si verifica Prop2 retorna TRUE, sino FALSE
    //Solo lanza excepcion si se lo invoca con un grafo :
    //   multi, o dirigido o tiene self-loop
    boolean checkProp2();
}
