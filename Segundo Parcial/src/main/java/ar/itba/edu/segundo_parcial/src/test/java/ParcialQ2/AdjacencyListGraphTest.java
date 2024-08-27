package ar.itba.edu.segundo_parcial.src.test.java.ParcialQ2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdjacencyListGraphTest {

    @Test
    void popularSubgraph() {

        GraphService<String, WeightedEdge> g4 = GraphFactory.create(GraphService.Multiplicity.SIMPLE, GraphService.EdgeMode.DIRECTED, GraphService.SelfLoop.NO, GraphService.Weight.YES, GraphService.Storage.SPARSE);
        g4.addEdge("Juan", "Leti", new WeightedEdge(2));
        g4.addEdge("Juan", "Pink", new WeightedEdge(2));
        g4.addEdge("Ale", "Leti", new WeightedEdge(4));
        g4.addEdge("Ale", "Call Me", new WeightedEdge(1));
        g4.addEdge("Mer", "Leti", new WeightedEdge(1));
        g4.addEdge("Mer", "Call Me", new WeightedEdge(9));
        g4.addEdge("Mer", "Pink", new WeightedEdge(2));

        GraphService<String, WeightedEdge> result = g4.popularSubgraph(8);

    }
}