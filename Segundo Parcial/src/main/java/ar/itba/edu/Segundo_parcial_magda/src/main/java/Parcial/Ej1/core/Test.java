package Parcial.Ej1.core;

public class Test {

    // en grafo dirigido se lanza exception
    public static void main01(String[] args) {
        GraphService<Integer, EmptyEdgeProp> g = new GraphBuilder<Integer, EmptyEdgeProp>().
                withMultiplicity(GraphService.Multiplicity.SIMPLE).
                withDirected(GraphService.EdgeMode.UNDIRECTED).
                withAcceptSelfLoop(GraphService.SelfLoop.NO).
                withAcceptWeight(GraphService.Weight.NO).
                withStorage(GraphService.Storage.SPARSE).build();

        g.addEdge(3, 4, new EmptyEdgeProp());
        g.addEdge(3, 0, new EmptyEdgeProp());
        g.addEdge(1, 2, new EmptyEdgeProp());
        g.addEdge(2, 0, new EmptyEdgeProp());
        g.addEdge(0, 1, new EmptyEdgeProp());

        System.out.println("euler path: " + g.hasEulerianPath()); //true
        System.out.println("check prop 1: " + g.checkProp1()); //true
        System.out.println("check prop 2: " + g.checkProp2()); //true

    }


    public static void main02(String[] args) {
        GraphService<Integer, EmptyEdgeProp> g = new GraphBuilder<Integer, EmptyEdgeProp>().
                withMultiplicity(GraphService.Multiplicity.SIMPLE).
                withDirected(GraphService.EdgeMode.UNDIRECTED).
                withAcceptSelfLoop(GraphService.SelfLoop.NO).
                withAcceptWeight(GraphService.Weight.NO).
                withStorage(GraphService.Storage.SPARSE).build();

        g.addVertex(5);
        g.addVertex(6);
        g.addEdge(3, 4, new EmptyEdgeProp());
        g.addEdge(3, 0, new EmptyEdgeProp());
        g.addEdge(1, 2, new EmptyEdgeProp());
        g.addEdge(2, 0, new EmptyEdgeProp());
        g.addEdge(0, 1, new EmptyEdgeProp());

        System.out.println("euler path: " + g.hasEulerianPath()); //true
        System.out.println("check prop 1: " + g.checkProp1()); //true
        System.out.println("check prop 2: " + g.checkProp2()); //true

    }

    public static void main03(String[] args) {
        GraphService<Integer, EmptyEdgeProp> g = new GraphBuilder<Integer, EmptyEdgeProp>().
                withMultiplicity(GraphService.Multiplicity.SIMPLE).
                withDirected(GraphService.EdgeMode.UNDIRECTED).
                withAcceptSelfLoop(GraphService.SelfLoop.NO).
                withAcceptWeight(GraphService.Weight.NO).
                withStorage(GraphService.Storage.SPARSE).build();

        g.addVertex(5);
        g.addVertex(6);
        g.addEdge(5, 6, new EmptyEdgeProp());
        g.addEdge(3, 4, new EmptyEdgeProp());
        g.addEdge(3, 0, new EmptyEdgeProp());
        g.addEdge(1, 2, new EmptyEdgeProp());
        g.addEdge(2, 0, new EmptyEdgeProp());
        g.addEdge(0, 1, new EmptyEdgeProp());

        System.out.println("euler path: " + g.hasEulerianPath()); //false
        System.out.println("check prop 1: " + g.checkProp1()); //false
        System.out.println("check prop 2: " + g.checkProp2()); //false

    }


    public static void main04(String[] args) {
        GraphService<Integer, EmptyEdgeProp> g = new GraphBuilder<Integer, EmptyEdgeProp>().
                withMultiplicity(GraphService.Multiplicity.SIMPLE).
                withDirected(GraphService.EdgeMode.UNDIRECTED).
                withAcceptSelfLoop(GraphService.SelfLoop.NO).
                withAcceptWeight(GraphService.Weight.NO).
                withStorage(GraphService.Storage.SPARSE).build();

        g.addVertex(5);
        g.addVertex(6);
        g.addEdge(5, 6, new EmptyEdgeProp());
        g.addEdge(6, 11, new EmptyEdgeProp());
        g.addEdge(5, 11, new EmptyEdgeProp());
        g.addEdge(3, 4, new EmptyEdgeProp());
        g.addEdge(3, 0, new EmptyEdgeProp());
        g.addEdge(1, 2, new EmptyEdgeProp());
        g.addEdge(2, 0, new EmptyEdgeProp());
        g.addEdge(0, 1, new EmptyEdgeProp());

        System.out.println("euler path: " + g.hasEulerianPath()); //false
        System.out.println("check prop 1: " + g.checkProp1()); //false
        System.out.println("check prop 2: " + g.checkProp2()); //true

    }


    public static void main05(String[] args) {
        GraphService<Character, WeightedEdge> g =
                GraphFactory.create(GraphService.Multiplicity.SIMPLE,
                        GraphService.EdgeMode.UNDIRECTED,
                        GraphService.SelfLoop.NO,
                        GraphService.Weight.YES,
                        GraphService.Storage.SPARSE);

        g.addEdge('B', 'A', new WeightedEdge(10));
        g.addEdge('C', 'B', new WeightedEdge(3));
        g.addEdge('A', 'D', new WeightedEdge(1));
        g.addEdge('E', 'D', new WeightedEdge(2));
        g.addEdge('E', 'A', new WeightedEdge(2));
        g.addEdge('A', 'C', new WeightedEdge(2));

        System.out.println("euler path: " + g.hasEulerianPath()); //true
        System.out.println("check prop 1: " + g.checkProp1()); //true
        System.out.println("check prop 2: " + g.checkProp2()); //true
    }

    public static void main06(String[] args) {
        GraphService<Character, WeightedEdge> g =
                GraphFactory.create(GraphService.Multiplicity.SIMPLE,
                        GraphService.EdgeMode.UNDIRECTED,
                        GraphService.SelfLoop.NO,
                        GraphService.Weight.YES,
                        GraphService.Storage.SPARSE);

        g.addEdge('B', 'A', new WeightedEdge(10));
        g.addEdge('C', 'B', new WeightedEdge(3));
        g.addEdge('A', 'D', new WeightedEdge(1));
        g.addEdge('E', 'D', new WeightedEdge(2));
        g.addEdge('E', 'A', new WeightedEdge(2));
        g.addEdge('A', 'C', new WeightedEdge(2));
        g.addEdge('E', 'C', new WeightedEdge(-4));

        System.out.println("euler path: " + g.hasEulerianPath()); //true
        System.out.println("check prop 1: " + g.checkProp1()); //true
        System.out.println("check prop 2: " + g.checkProp2()); //true
    }

    public static void main07(String[] args) {
        GraphService<Character, WeightedEdge> g =
                GraphFactory.create(GraphService.Multiplicity.SIMPLE,
                        GraphService.EdgeMode.UNDIRECTED,
                        GraphService.SelfLoop.NO,
                        GraphService.Weight.YES,
                        GraphService.Storage.SPARSE);

        g.addEdge('B', 'A', new WeightedEdge(10));
        g.addEdge('C', 'B', new WeightedEdge(3));
        g.addEdge('A', 'D', new WeightedEdge(1));
        g.addEdge('E', 'D', new WeightedEdge(2));
        g.addEdge('A', 'C', new WeightedEdge(2));
        g.addEdge('D', 'B', new WeightedEdge(4));


        System.out.println("euler path: " + g.hasEulerianPath()); //false
        System.out.println("check prop 1: " + g.checkProp1()); //true
        System.out.println("check prop 2: " + g.checkProp2()); //false
    }

    public static void main08(String[] args) {
        GraphService<Character, WeightedEdge> g =
                GraphFactory.create(GraphService.Multiplicity.SIMPLE,
                        GraphService.EdgeMode.UNDIRECTED,
                        GraphService.SelfLoop.NO,
                        GraphService.Weight.YES,
                        GraphService.Storage.SPARSE);

        g.addEdge('B', 'A', new WeightedEdge(10));
        g.addEdge('C', 'B', new WeightedEdge(3));
        g.addEdge('A', 'C', new WeightedEdge(2));

        g.addEdge('X', 'U', new WeightedEdge(3));
        g.addEdge('X', 'T', new WeightedEdge(3));
        g.addEdge('U', 'T', new WeightedEdge(2));


        System.out.println("euler path: " + g.hasEulerianPath()); //false
        System.out.println("check prop 1: " + g.checkProp1()); //false
        System.out.println("check prop 2: " + g.checkProp2()); //true
    }


    public static void main09(String[] args) {
        GraphService<Integer, EmptyEdgeProp> g = new GraphBuilder<Integer, EmptyEdgeProp>().
                withMultiplicity(GraphService.Multiplicity.SIMPLE).
                withDirected(GraphService.EdgeMode.UNDIRECTED).
                withAcceptSelfLoop(GraphService.SelfLoop.NO).
                withAcceptWeight(GraphService.Weight.NO).
                withStorage(GraphService.Storage.SPARSE).build();

        System.out.println("euler path: " + g.hasEulerianPath()); //true
        System.out.println("check prop 1: " + g.checkProp1()); //true
        System.out.println("check prop 2: " + g.checkProp2()); //true

    }

    // en grafo dirigido se lanza exception
    public static void main10(String[] args) {
        GraphService<Integer, EmptyEdgeProp> g = new GraphBuilder<Integer, EmptyEdgeProp>().
                withMultiplicity(GraphService.Multiplicity.SIMPLE).
                withDirected(GraphService.EdgeMode.DIRECTED).
                withAcceptSelfLoop(GraphService.SelfLoop.NO).
                withAcceptWeight(GraphService.Weight.NO).
                withStorage(GraphService.Storage.SPARSE).build();

        g.addEdge(3, 4, new EmptyEdgeProp());
        g.addEdge(3, 0, new EmptyEdgeProp());
        g.addEdge(1, 2, new EmptyEdgeProp());
        g.addEdge(2, 0, new EmptyEdgeProp());
        g.addEdge(0, 1, new EmptyEdgeProp());

        System.out.println("euler path: " + g.hasEulerianPath()); //exception
        System.out.println("check prop 1: " + g.checkProp1()); //exception
        System.out.println("check prop 2: " + g.checkProp2()); //exception
    }


}