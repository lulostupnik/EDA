package testing;

import core_service.*;
import core.*;
import core_service.GraphService.*;
import use.EmptyEdgeProp;
import use.WeightedEdge;


public class Main {
    public static void main(String[] args) {
        //airplane(args);
        djstraMain(args);
        /*GraphService<Integer, EmptyEdgeProp> g = new GraphBuilder<Integer, EmptyEdgeProp>().withMultiplicity(Multiplicity.SIMPLE).withDirected(EdgeMode.DIRECTED).withAcceptSelfLoop(SelfLoop.YES).withAcceptWeight(Weight.NO).withStorage(Storage.SPARSE).build();
        g.addVertex(8);
        g.addEdge(2, 2, new EmptyEdgeProp());
        g.addVertex(2);
        g.addEdge(2, 9, new EmptyEdgeProp());
        g.addEdge(2, 9, new EmptyEdgeProp());
        g.dump();*/
    }

    public static void airplane(String[] args) {
        GraphService<String, WeightedEdge> g = GraphFactory.create(Multiplicity.SIMPLE, EdgeMode.UNDIRECTED, SelfLoop.YES, Weight.YES, Storage.SPARSE);

        g.addEdge("HNL", "LAX", new WeightedEdge(2555));
        g.addEdge("LAX", "DFW", new WeightedEdge(1233));
        g.addEdge("DFW", "MIA", new WeightedEdge(1120));
        g.addEdge("MIA", "PVD", new WeightedEdge(1205));
        g.addEdge("PVD", "ORD", new WeightedEdge(849));
        g.addEdge("ORD", "SFO", new WeightedEdge(1843));
        g.addEdge("SFO", "LAX", new WeightedEdge(337));
        g.addEdge("LAX", "ORD", new WeightedEdge(1743));
        g.addEdge("ORD", "DFW", new WeightedEdge(802));
        g.addEdge("DFW", "LGA", new WeightedEdge(1387));
        g.addEdge("PVD", "LGA", new WeightedEdge(142));
        g.addEdge("MIA", "LGA", new WeightedEdge(1099));
        g.dump();

        DijkstraPath<String, WeightedEdge> pathRta = g.dijsktra("HNL");
        System.out.println(pathRta);
    }

    public static void djstraMain(String[] args) {
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

        g4.printBFS('B');
        System.out.println();
        g4.printDFS('A');
        System.out.println();
        g4.getBFS('A').forEach(System.out::println);
    }


    public static void main4(String[] args) {
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
        System.out.println(g2.outDegree('U')); // 2
        System.out.println(g2.inDegree('U')); // 0
        //g2.removeVertex('G');
        System.out.println("Num of edges: " + g2.numberOfEdges());

        g2.dump();

        g2.removeEdge('F','F', new WeightedEdge(3));
        System.out.println("Num of edges: " + g2.numberOfEdges());
        System.out.println(g2.inDegree('F')); // 2
        System.out.println(g2.outDegree('F')); // 1
        g2.dump();

        System.out.println("----------");
        g2.addEdge('F', 'B', new WeightedEdge(2));
        g2.dump();
        System.out.println("Edges: "+ g2.numberOfEdges());
        g2.removeVertex('F');
        g2.dump();
        System.out.println("Edges" + g2.numberOfEdges());
    }

    public static void main3(String[] args) {

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
        System.out.println(g2.outDegree('U')); // 2
        System.out.println(g2.inDegree('U')); // 0
        //g2.removeVertex('G');
        System.out.println("Num of edges: " + g2.numberOfEdges());
    }
    public static void main2(String[] args) {

        GraphBuilder<Character, EmptyEdgeProp> builder1 = new GraphBuilder<Character, EmptyEdgeProp>();
        builder1.withAcceptSelfLoop(GraphService.SelfLoop.YES);
        builder1.withAcceptWeight(GraphService.Weight.NO);
        builder1.withDirected(GraphService.EdgeMode.UNDIRECTED);
        builder1.withMultiplicity(Multiplicity.SIMPLE);

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

        g1.addEdge('F', 'F', new EmptyEdgeProp());
        System.out.println( g1.numberOfEdges() );  // 10


        boolean b = g1.removeEdge('A', 'F');
        System.out.println("Se removio el edge A, F: " + b + " Quedan: " + g1.numberOfEdges() );  // 9

        b = g1.removeEdge('F', 'F');
        System.out.println("Se removio el edge F, F: " + b + " Quedan: " + g1.numberOfEdges() );

       /* GraphService<Character, EmptyEdgeProp> g3 = new GraphBuilder().withAcceptSelfLoop(GraphService.SelfLoop.YES).withAcceptWeight(GraphService.Weight.NO).withDirected(GraphService.EdgeMode.UNDIRECTED).withStorage(GraphService.Storage.SPARSE).withMultiplicity(GraphService.Multiplicity.SIMPLE).build();

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



        */
}



}