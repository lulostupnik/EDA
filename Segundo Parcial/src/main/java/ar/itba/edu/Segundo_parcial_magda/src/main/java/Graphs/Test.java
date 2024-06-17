package Graphs;

import Graphs.core.DijkstraPath;
import Graphs.core.GraphFactory;
import Graphs.core_service.GraphBuilder;
import Graphs.core_service.GraphService;
import Graphs.use.EmptyEdgeProp;
import xPractica.ParcialX.Ej1.use.WeightedEdge;

public class Test {
    public static void main(String[] args) {

        GraphBuilder<Character, EmptyEdgeProp> builder1 = new GraphBuilder<Character, EmptyEdgeProp>();
        builder1.withAcceptSelfLoop(GraphService.SelfLoop.NO);
        builder1.withAcceptWeight(GraphService.Weight.NO);
        builder1.withDirected(GraphService.EdgeMode.UNDIRECTED);
        builder1.withMultiplicity(GraphService.Multiplicity.SIMPLE);

        GraphService<Character, EmptyEdgeProp> g1 = builder1.build();

        g1.addEdge('E', 'B', new EmptyEdgeProp());


        g1.addEdge('A', 'B', new EmptyEdgeProp());
        g1.addEdge('F', 'B', new EmptyEdgeProp());
        g1.addVertex('D');
        g1.addVertex('G');
        g1.addEdge('E', 'F', new EmptyEdgeProp());
        g1.addEdge('F', 'A', new EmptyEdgeProp());
        g1.addEdge('F', 'G', new EmptyEdgeProp());
        g1.addEdge('U', 'G', new EmptyEdgeProp());
        g1.addEdge('T', 'U', new EmptyEdgeProp());
        g1.addEdge('C', 'G', new EmptyEdgeProp());
        System.out.println(g1.numberOfEdges()); // 9

        GraphBuilder<Character, WeightedEdge> builder2 = new GraphBuilder<Character, WeightedEdge>();
        builder2.withAcceptSelfLoop(GraphService.SelfLoop.YES);
        builder2.withAcceptWeight(GraphService.Weight.NO);
        builder2.withDirected(GraphService.EdgeMode.DIRECTED);
        builder2.withStorage(GraphService.Storage.SPARSE);
        builder2.withMultiplicity(GraphService.Multiplicity.MULTIPLE);

        GraphService<Character, WeightedEdge> g2 = builder2.build();

        g2.addVertex('D');
        g2.addVertex('G');
        g2.addEdge('G', 'F', new WeightedEdge(2));
        g2.addEdge('U', 'G', new WeightedEdge(-10));
        g2.addEdge('U', 'G', new WeightedEdge(0));
        g2.addEdge('F', 'F', new WeightedEdge(3));
        g2.addEdge('F', 'F', new WeightedEdge(2));
        System.out.println(g2.inDegree('G')); // 2
        System.out.println(g2.outDegree('G')); // 1
        System.out.println(g2.inDegree('F')); // 3
        System.out.println(g2.outDegree('F')); // 2
        g2.removeVertex('G');

        GraphService<Character, EmptyEdgeProp> g3 = new GraphBuilder().withAcceptSelfLoop(GraphService.SelfLoop.YES).withAcceptWeight(GraphService.Weight.NO).withDirected(GraphService.EdgeMode.UNDIRECTED).withStorage(GraphService.Storage.SPARSE).withMultiplicity(GraphService.Multiplicity.SIMPLE).build();

        g3.addEdge('A', 'B', new EmptyEdgeProp());
        g3.addEdge('A', 'F', new EmptyEdgeProp());
        g3.addEdge('E', 'B', new EmptyEdgeProp());
        g3.addEdge('E', 'F', new EmptyEdgeProp());
        g3.addEdge('B', 'F', new EmptyEdgeProp());
        g3.addVertex('D');
        g3.addEdge('F', 'G', new EmptyEdgeProp());
        g3.addEdge('G', 'U', new EmptyEdgeProp());
        g3.addEdge('U', 'T', new EmptyEdgeProp());
        g3.addEdge('G', 'C', new EmptyEdgeProp());
        System.out.println(String.format("#vertices: %d", g3.numberOfVertices()));
        g3.printBFS('E');
        System.out.println('\n');
        g3.printDFS('E');
        System.out.println('\n');
        g3.printDFS('E');
        for (Character vert : g3.getBFS('E')) {
            System.out.println(vert);
        }
        GraphService<Character, WeightedEdge> g4 = GraphFactory.create(GraphService.Multiplicity.SIMPLE, GraphService.EdgeMode.DIRECTED, GraphService.SelfLoop.NO, GraphService.Weight.YES, GraphService.Storage.SPARSE);
        g4.addEdge('A', 'B', new WeightedEdge(10));
        g4.addEdge('A', 'C', new WeightedEdge(3));
        g4.addEdge('B', 'C', new WeightedEdge(1));
        g4.addEdge('B', 'D', new WeightedEdge(2));
        g4.addEdge('C', 'A', new WeightedEdge(1));
        g4.addEdge('C', 'B', new WeightedEdge(4));
        g4.addEdge('C', 'D', new WeightedEdge(8));
        g4.addEdge('C', 'E', new WeightedEdge(2));
        g4.addEdge('D', 'E', new WeightedEdge(7));
        g4.addEdge('E', 'D', new WeightedEdge(9));
        g4.addEdge('Z', 'K', new WeightedEdge(17));
        g4.addEdge('K', 'A', new WeightedEdge(19));
        g4.dump();
        DijkstraPath<Character, WeightedEdge> pathRta = g4.dijsktra('A');
        System.out.println(pathRta);
    }

}
