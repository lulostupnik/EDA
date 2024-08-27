package lstupnik.ej1;

import java.util.HashMap; 


// acepta repetidos
public class BST<T extends Comparable<? super T>> implements BSTreeInterface<T> {

	private Node root;

	@Override
	public void insert(T myData) {
		if (myData == null)
			throw new RuntimeException("element cannot be null");

		root= insert(root, myData);
	}
	
	private Node insert(Node currentNode, T myData) {
		if (currentNode == null) {
			return new Node(myData);
		}
		
		if (myData.compareTo(currentNode.data) <= 0)
			currentNode.left= insert(currentNode.left, myData);
		else
			currentNode.right= insert(currentNode.right, myData);
		
		return currentNode;
	}



	public void inRangeHelper(T lower, T upper, Node currentNode, HashMap<T, Integer> rta){
		if(currentNode == null){
			return;
		}
		if(currentNode.data.compareTo(lower) < 0){
			inRangeHelper(lower, upper, currentNode.right, rta);  //Caso 1: soy mas chico que el min del rango, me voy a la derecha
			return;
		}
		if(currentNode.data.compareTo(upper) > 0){
			inRangeHelper(lower, upper, currentNode.left, rta); //Caso 2: soy mas grande que el max del rango, me voy a la izquierda
			return;
		}
		//Estoy en el rango
		rta.put(currentNode.data, rta.getOrDefault(currentNode.data, 0) + 1);
		//Voy para ambos lados:
		inRangeHelper(lower,upper, currentNode.left, rta);
		inRangeHelper(lower, upper, currentNode.right, rta);
	}

	@Override
	public HashMap<T, Integer> inRange(T lower, T upper) {
		if (lower == null || upper == null){
			throw new RuntimeException("lower and upper cannot be nulls");}
		if(lower.compareTo(upper)>0){
			T aux = lower;
			lower = upper;
			upper = aux;
		}
		HashMap<T, Integer> rta = new HashMap<>();
		inRangeHelper(lower, upper, root, rta);
		return rta;
	}
    
	class Node implements NodeTreeInterface<T> {

		private T data;
		private Node left;
		private Node right;
		
		public Node(T myData) {
			this.data= myData;
		}

		
		public T getData() {
			return data;
		}
		public NodeTreeInterface<T> getLeft() {
			return left;
		}
		
		public NodeTreeInterface<T> getRight() {
			return right;
		}
		

	}
	
	
	

	
	public static void main(String[] args) {
		BST<Integer> myTree = new BST<>();
		myTree.insert(60);
		myTree.insert(40);
		myTree.insert(20);
		myTree.insert(120);
		myTree.insert(300);
		myTree.insert(500);
		myTree.insert(400);
		myTree.insert(30);
		myTree.insert(450);
		myTree.insert(20);
		myTree.insert(20);
		myTree.insert(22);
		myTree.insert(4);
		myTree.insert(100);
		myTree.insert(110);

		HashMap<Integer, Integer> rta;
		
		
		rta= myTree.inRange(1,  10);
		System.out.println(rta);  //  {4: 1}
		
		
		rta = myTree.inRange(23,  91);
		System.out.println(rta);  //  {40=1, 60=1, 30=1}
		
		rta = myTree.inRange(23,  400);
		System.out.println(rta);  //  {400=1, 100=1, 40=1, 120=1, 60=1, 300=1, 30=1, 110=1}

		rta = myTree.inRange(20,  26);
		System.out.println(rta);  //  {20=3, 22=1}
		
		rta = myTree.inRange(200,  430);
		System.out.println(rta);  //  {400=1, 300=1}
		
		rta = myTree.inRange(30,  20);
		System.out.println(rta);  //  {20:3, 22:1, 30:1}

		
		rta = myTree.inRange(121,  200);
		System.out.println(rta);  //  {}
		
		rta = myTree.inRange(900,  800);
		System.out.println(rta);  //  {}
	}
	

	
}