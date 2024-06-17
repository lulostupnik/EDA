package xPractica.Parcial2020_1C.Ej2;

import xPractica.Parcial2020_1C.Ej2.core_service.GraphService;
import xPractica.Parcial2020_1C.Ej2.core_service.GraphBuilder;
import xPractica.Parcial2020_1C.Ej2.use.*;

public class Test {
    public static void main(String[] args) {
        GraphBuilder<Character, EmptyEdgeProp> f= new GraphBuilder<>();
        GraphService<Character,EmptyEdgeProp> g= f.withAcceptSelfLoop(GraphService.SelfLoop.YES).withAcceptWeight(GraphService.Weight.NO)
                .withDirected(GraphService.EdgeMode.UNDIRECTED).withMultiplicity(GraphService.Multiplicity.SIMPLE).build();

        g.addEdge('B', 'A',new EmptyEdgeProp());
        g.addEdge('A', 'C',new EmptyEdgeProp());
        g.addEdge('B', 'E',new EmptyEdgeProp());
        g.addEdge('C', 'E',new EmptyEdgeProp());
        g.addEdge('H', 'E',new EmptyEdgeProp());
        g.addEdge('E', 'F',new EmptyEdgeProp());
        g.addEdge('F', 'H',new EmptyEdgeProp());
        g.addEdge('H', 'G',new EmptyEdgeProp());
        g.addEdge('C', 'H',new EmptyEdgeProp());
        g.addEdge('D', 'T',new EmptyEdgeProp());
        g.addVertex('X');
        g.addEdge('B', 'X', new EmptyEdgeProp());
        g.addEdge('F', 'X', new EmptyEdgeProp());
        //g.addEdge('X', 'X', new EmptyEdgeProp());
        g.addEdge('X', 'X', new EmptyEdgeProp());
        g.addVertex('M'); // isolated
        g.addVertex('F');
        g.removeEdge('C', 'E');
        g.removeEdge('F', 'X');
        g.removeEdge('E', 'B');

        System.out.println(g.connectedComponents());

        g.removeEdge('A', 'B');
        g.removeEdge('F', 'E');

        System.out.println(g.connectedComponents());

        g.removeEdge('X', 'B');
        System.out.println("#cc: " + g.connectedComponents());

        g.removeVertex('X');
        System.out.println("#cc: " + g.connectedComponents());
    }
}
