package lstupnik.ej2;

 
import java.util.*;


public class SimpleOrDefault<V,E> extends AdjacencyListGraph<V,E> {

	protected SimpleOrDefault(boolean isDirected, boolean acceptSelfLoops, boolean isWeighted) {
		super(true, isDirected, acceptSelfLoops, isWeighted);
	
	}

	private void validateNRegular(){
		StringBuilder message = new StringBuilder();
		boolean error = false;
		if(isDirected){
			message.append("Graph can not be directed\n");
			error = true;
		}
		if(acceptSelfLoop){
			message.append("Graph can not accept self loops\n");
			error = true;
		}
		if(error){
			throw new RuntimeException(message.toString());
		}
	}
	@Override
	public int getNRegular() {
		validateNRegular();
		Collection<V> vertCollection = getVertices();
		if(vertCollection.isEmpty()){
			return -1; //no hay vertices --> no tiene grado.
		}
		Iterator<V> it = vertCollection.iterator();
		//i.next() es seguro porque la coleccion no es empty
		int first_degree = degree(it.next());

		int current_degree;
		while(it.hasNext()){
			current_degree = degree(it.next());
			if(current_degree != first_degree){
				return -1;
			}
		}
		return first_degree;
	}
	
	@Override
	public void addEdge(V aVertex, V otherVertex, E theEdge) {

		// validacion y creacion de vertices si fuera necesario
		super.addEdge(aVertex, otherVertex, theEdge);

		
		Collection<InternalEdge> adjListSrc = getAdjacencyList().get(aVertex);

		// if exists edge with same target...
		for (InternalEdge internalEdge : adjListSrc) {
			if (internalEdge.target.equals(otherVertex))
				throw new IllegalArgumentException( "Simple Graph: cannot have repeated edges" );
		}
		

		// creacion de ejes
		adjListSrc.add(new InternalEdge(theEdge, otherVertex));

		if (!isDirected ) {
			Collection<AdjacencyListGraph<V, E>.InternalEdge> adjListDst = getAdjacencyList().get(otherVertex);
			adjListDst.add(new InternalEdge(theEdge, aVertex));
		}
	
	}

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
