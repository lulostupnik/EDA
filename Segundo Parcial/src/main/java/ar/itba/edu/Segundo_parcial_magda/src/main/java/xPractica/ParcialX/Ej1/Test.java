package xPractica.ParcialX.Ej1;

import xPractica.ParcialX.Ej1.core.GraphFactory;
import xPractica.ParcialX.Ej1.core_service.GraphService;
import xPractica.ParcialX.Ej1.use.WeightedEdge;

import java.lang.reflect.InvocationTargetException;

public class Test {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        GraphService<String, WeightedEdge> g2 = GraphFactory.create(GraphService.Multiplicity.SIMPLE, GraphService.EdgeMode.DIRECTED,
                                                        GraphService.SelfLoop.NO, GraphService.Weight.YES, GraphService.Storage.SPARSE);

        g2.addEdge("Juan", "Pink", new WeightedEdge(2));
        g2.addEdge("Mer", "Pink", new WeightedEdge(2));
        g2.addEdge("Juan", "Eiti Leda", new WeightedEdge(2));
        g2.addEdge("Ale", "Eiti Leda", new WeightedEdge(4));
        g2.addEdge("Mer", "Eiti Leda", new WeightedEdge(1));
        g2.addEdge("Mer", "Call me maybe", new WeightedEdge(9));
        g2.addEdge("Ale", "Call me maybe", new WeightedEdge(1));


        System.out.println("Es bipartito? " + g2.isBipartite());

        /*System.out.println("Indegree de Ale: " + g2.inDegree("Ale"));
        System.out.println("Indegree de Juan: " + g2.inDegree("Juan"));
        System.out.println("Indegree de Mer: " + g2.inDegree("Mer"));
        System.out.println("Outdegree de Pink: " + g2.outDegree("Pink"));
        System.out.println("Outdegree de Eiti Leda: " + g2.outDegree("Eiti Leda"));
        System.out.println("Outdegree de Call me maybe: " + g2.outDegree("Call me maybe"));*/

        System.out.println("Cantidad de vertices: " + g2.numberOfVertices());

        GraphService<String, WeightedEdge> subgraph = g2.popularSubgraph(7);
        //quiero ver el subgrafo
        System.out.println("Cantidad de vertices del subgrafo: " + subgraph.numberOfVertices());
        System.out.println("Cantidad de aristas del subgrafo: " + subgraph.numberOfEdges());
        for (String v : subgraph.getVertices()) {
            System.out.println("Vertice: " + v);
        }

    }

}
