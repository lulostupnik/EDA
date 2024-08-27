package Grafos_Marengo;


import Grafos_Marengo.core.AdjacencyListGraph;
import Grafos_Marengo.core_service.GraphService;

public class Test {
    public static void main(String[] args) {
        GraphService<Character, Parcial2023_2C.EmptyEdgeProp> undirectedMultipleLoopNoWeight;
       // GraphService<Character, EmptyEdgeProp> undirectedSimpleLoopNoWeight;
        /*undirectedSimpleLoopNoWeight = new GraphBuilder<Character, EmptyEdgeProp>()
                .withStorage(GraphService.Storage.SPARSE)
                .withAcceptSelfLoop(GraphService.SelfLoop.YES)
                .withAcceptWeight(GraphService.Weight.NO)
                .withDirected(GraphService.EdgeMode.UNDIRECTED)
                .withMultiplicity(GraphService.Multiplicity.SIMPLE)
                .build();*/
        undirectedMultipleLoopNoWeight = new GraphBuilder<Character, Parcial2023_2C.EmptyEdgeProp>()
                .withStorage(GraphService.Storage.SPARSE)
                .withAcceptSelfLoop(GraphService.SelfLoop.YES)
                .withAcceptWeight(GraphService.Weight.NO)
                .withDirected(GraphService.EdgeMode.UNDIRECTED)
                .withMultiplicity(GraphService.Multiplicity.MULTIPLE)
                .build();

        undirectedMultipleLoopNoWeight.addEdge('A','B',new Parcial2023_2C.EmptyEdgeProp());
        undirectedMultipleLoopNoWeight.addEdge('A','C',new Parcial2023_2C.EmptyEdgeProp());
        undirectedMultipleLoopNoWeight.addEdge('A','E',new Parcial2023_2C.EmptyEdgeProp());
        undirectedMultipleLoopNoWeight.addEdge('C','E',new Parcial2023_2C.EmptyEdgeProp());
        System.out.println("----------------------------------------------------------------");
        System.out.println(undirectedMultipleLoopNoWeight.hasCycle());
    }
}
