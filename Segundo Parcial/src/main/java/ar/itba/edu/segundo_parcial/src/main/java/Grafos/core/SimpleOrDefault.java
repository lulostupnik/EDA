package Grafos.core;


public class SimpleOrDefault<V,E> extends AdjacencyListGraph<V,E> {

	protected SimpleOrDefault(boolean isDirected, boolean acceptSelfLoops, boolean isWeighted) {
		super(true, isDirected, acceptSelfLoops, isWeighted);
	
	}

	@Override
	public void addEdge(V aVertex, V otherVertex, E theEdge) {
		// validacion y creacion de vertices si fuera necesario
		super.addEdge(aVertex, otherVertex, theEdge);
		if(getAdjacencyList().get(aVertex).contains(new InternalEdge(theEdge, otherVertex))) {
			throw new IllegalArgumentException("simple graphs can´t have multiple edges");
		}
		getAdjacencyList().get(aVertex).add(new InternalEdge(theEdge, otherVertex));
		if(!isDirected) {
			getAdjacencyList().get(otherVertex).add(new InternalEdge(theEdge, aVertex));
		}
	}


}
