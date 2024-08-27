package lstupnik.ej2;
import lstupnik.ej2.GraphService.*;


public class Test {
    public static void main(String[] args) {
        GraphService<Character, EmptyEdgeProp> g1 =
                new GraphBuilder<Character,
                        EmptyEdgeProp>().withAcceptSelfLoop(SelfLoop.YES)
                        .withAcceptWeight(Weight.NO).withDirected(EdgeMode.UNDIRECTED)
                        .withStorage(Storage.SPARSE).withMultiplicity(Multiplicity.SIMPLE)
                        .build();
        try{
            g1.getNRegular();// Runtime exception
        }catch (RuntimeException e){
            System.out.println(e);
        }

        GraphService<Character, EmptyEdgeProp> g3 =
                new GraphBuilder<Character,
                        EmptyEdgeProp>().withAcceptSelfLoop(SelfLoop.NO)
                        .withAcceptWeight(Weight.NO).withDirected(EdgeMode.DIRECTED)
                        .withStorage(Storage.SPARSE).withMultiplicity(Multiplicity.SIMPLE)
                        .build();
        try{
            g3.getNRegular();// Runtime exception
        }catch (RuntimeException e){
            System.out.println(e);
        }


        GraphService<Character, EmptyEdgeProp> g2 =
                new GraphBuilder<Character,
                        EmptyEdgeProp>().withAcceptSelfLoop(SelfLoop.NO)
                        .withAcceptWeight(Weight.NO).withDirected(EdgeMode.UNDIRECTED)
                        .withStorage(Storage.SPARSE).withMultiplicity(Multiplicity.SIMPLE)
                        .build();
        g2.addVertex('a');
        g2.addVertex('e');
        g2.addVertex('x');
        g2.addVertex('q');
        g2.addVertex('p');
        g2.addVertex('u');
        g2.addEdge('a','e', new EmptyEdgeProp());
        g2.addEdge('x','e', new EmptyEdgeProp());
        g2.addEdge('a', 'x', new EmptyEdgeProp());
        g2.addEdge('x', 'q', new EmptyEdgeProp());
        g2.addEdge('q', 'p', new EmptyEdgeProp());;
        g2.addEdge('p', 'u', new EmptyEdgeProp());
        g2.addEdge('e', 'p', new EmptyEdgeProp());
        g2.addEdge('u', 'q', new EmptyEdgeProp());
        System.out.println(g2.getNRegular() + " should be -1");

        g2.addEdge('a', 'u', new EmptyEdgeProp());
        System.out.println(g2.getNRegular() + " should be 3");

        GraphService<Character, EmptyEdgeProp> g =
                new GraphBuilder<Character,
                        EmptyEdgeProp>().withAcceptSelfLoop(SelfLoop.NO)
                        .withAcceptWeight(Weight.NO).withDirected(EdgeMode.UNDIRECTED)
                        .withStorage(Storage.SPARSE).withMultiplicity(Multiplicity.SIMPLE)
                        .build();

        g.addVertex('a');
        g.addVertex('b');

        g.addVertex('c');
        g.addVertex('d');

        g.addVertex('e');
        g.addVertex('f');

        g.addEdge('a', 'b', new EmptyEdgeProp());
        g.addEdge('c', 'd', new EmptyEdgeProp());
        g.addEdge('e', 'f', new EmptyEdgeProp());
        System.out.println(g.getNRegular() + " should be 1");

        g.addVertex('z');
        System.out.println(g.getNRegular() + " should be -1");

    }
}
