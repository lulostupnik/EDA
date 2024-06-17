package Parcial2023_2C;

import Parcial2023_2C.AdjacencyListGraph;

public class TestEj2 {
    public static void main(String[] args) {
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

        undirectedMultipleLoopNoWeight.addEdge('2','5',new EmptyEdgeProp());
        undirectedMultipleLoopNoWeight.addEdge('1','2',new EmptyEdgeProp());
        undirectedMultipleLoopNoWeight.addEdge('3','4',new EmptyEdgeProp());
        undirectedMultipleLoopNoWeight.addEdge('8','9',new EmptyEdgeProp());
        System.out.println("----------------------------------------------------------------");
        System.out.println(undirectedMultipleLoopNoWeight.connectedComponentsQty());
    }
}
