package BinaryTree;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.function.Function;


public class BinaryTree implements BinaryTreeService {
	
	private Node root;
	private Scanner inputScanner;
	int size = 0;

	public BinaryTree(String fileName) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, FileNotFoundException {
		 InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);

		 if (is == null)
			 throw new FileNotFoundException(fileName);
		 
		 inputScanner = new Scanner(is);
		 inputScanner.useDelimiter("\\s+");
		 buildTree();
		 inputScanner.close();

	}

	public void printHierarchy() {
		printRec(0, root);
	}

	public void removeLeaves(){
		if(root.isLeaf()){
			root = null;
			return;
		}
		removeLeavesRec(root);
	}

	private void removeLeavesRec(Node current){
		if(current == null) return;
		if(current.left != null && current.left.isLeaf()) current.left = null;
		if(current.right != null && current.right.isLeaf()) current.right = null;
		removeLeavesRec(current.left);
		removeLeavesRec(current.right);
	}


	public void buildTree() throws IllegalArgumentException, SecurityException {
		 Queue<NodeHelper> pendingOps = new LinkedList<>();
		 String token;
		 root = new Node();
		 pendingOps.add(new NodeHelper(root, n -> n));
		 
		 while(inputScanner.hasNext()) {
			 token = inputScanner.next();
			 NodeHelper aPendingOp = pendingOps.remove();
			 Node currentNode = aPendingOp.getNode();
			 
			 if (token.equals("?")) {
				 // no hace falta poner en null al L o R porque ya esta con null
				 // reservar el espacio en Queue aunque NULL no tiene hijos para aparear
				pendingOps.add( new NodeHelper(null, null) );  // como si hubiera izq
				pendingOps.add( new NodeHelper(null, null) );  // como si hubiera der
			 }
			 else {
				 Function<Node, Node> anAction = aPendingOp.getAction();
				 currentNode = anAction.apply(currentNode);
				 // armo la info del izq, der o el root
				 currentNode.data = token;
				 
				 // hijos se postergan
				 pendingOps.add(new NodeHelper(currentNode, n -> n.setLeftTree(new Node())));
				 pendingOps.add(new NodeHelper(currentNode, n -> n.setRightTree(new Node())));

			 }
			 size++;//ESTO NO ES UN VERDADERO SIZE

		 }

			
		 if (root.data == null)  // no entre al ciclo jamas 
			 root = null;
	}

	private void printRec(int level, Node current) {
		if(current == null) {
			System.out.println(" ".repeat(level * 5) + "└── " + "null");
			return;
		}
		System.out.println(" ".repeat(level * 5) + "└── " + current.data);
		if(current.isLeaf()){
			return;
		}
		printRec(level + 1, current.left);
		printRec(level + 1, current.right);
	}

	public void toFile(String name) throws IOException {
		String path = "/home/santiago/Documents/facultad/eda/segundo_parcial/src/main/resources/" + name;
		PrintWriter writer = new PrintWriter(path, StandardCharsets.UTF_8);
		writer.print(getString());
		writer.close();
	}

	private String getString() {
		Queue<Node> queue = new LinkedList<>();
		queue.add(root);
		StringBuilder sb = new StringBuilder();
		int count = size;
		int aux = 1;
		int levelAux = 1;
		while(count >= 0) {
			Node current = queue.remove();
			if(current == null) {
				sb.append("?\t");
				queue.add(null);
				queue.add(null);
			} else {
				sb.append(current.data).append("\t");
				queue.add(current.left);
				queue.add(current.right);
			}

			if(aux == size-count+1) {
				levelAux *= 2;
				aux = aux + levelAux;
				sb.append("\n");
			}
			count--;
		}
		return sb.toString();
	}

	public boolean equals(BinaryTree other) {
		if(other.size != size) return false;
		Queue<Node> queue1 = new LinkedList<>();
		Queue<Node> queue2 = new LinkedList<>();
		queue1.add(root);
		queue2.add(other.root);
		int count = size;

		while(count != 0) {
			Node currentThis = queue1.remove();
			Node currentOther = queue2.remove();
			if(currentThis == null || currentOther == null) {
				if(currentThis == null) {
					queue1.add(null);
					queue1.add(null);
				}
				if(currentOther == null) {
					queue2.add(null);
					queue2.add(null);
				}
			} else {
				if(!currentThis.data.equals(currentOther.data)) {
					return false;
				}
				queue1.add(currentThis.left);
				queue1.add(currentThis.right);
				queue2.add(currentOther.left);
				queue2.add(currentOther.right);
			}
			count--;
		}
		return true;
	}

	public int getHeightIter() {
		Queue<Node> queue = new LinkedList<>();
		queue.add(root);
		int height = -1;
		while (!queue.isEmpty()) {
			int size = queue.size();
			height++;
			while (size > 0) {
				Node treeNode = queue.remove();
				if (treeNode.left != null)
					queue.add(treeNode.left);
				if (treeNode.right != null)
					queue.add(treeNode.right);
				size--;
			}
		}
		return height;
	}

	public int getHeightRec() {
		int height = -1;
		int aux = getHeightSubtreeRec(root);
		return height + aux;
	}

	private int getHeightSubtreeRec(Node current) {
		if(current == null) {
			return 0;
		}
		int leftHeight = getHeightSubtreeRec(current.left);
		int rightHeight = getHeightSubtreeRec(current.right);
		return 1 + Math.max(leftHeight, rightHeight);
	}
    
	@Override
	public void preorder() {
		// COMPLETE
	}
	
	@Override
	public void postorder() {
		// COMPLETE
	}


	


	// hasta el get() no se evalua
	class Node {
		private String data;
		private Node left;
		private Node right;
		
		public Node setLeftTree(Node aNode) {
			left = aNode;
			return left;
		}
		
		
		public Node setRightTree(Node aNode) {
			right = aNode;
			return right;
		}
		
		
		
		public Node() {
			// TODO Auto-generated constructor stub
		}

		private boolean isLeaf() {
			return left == null && right == null;
		}


	}  // end Node class
	
	static class NodeHelper {
		private Node aNode;
		private Function<Node, Node> anAction;
		
		public NodeHelper(Node aNode, Function<Node, Node> anAction ) {
			this.aNode= aNode;
			this.anAction= anAction;
		}
		
		
		public Node getNode() {
			return aNode;
		}
		
		public Function<Node, Node> getAction() {
			return anAction;
		}
		
	}
	
	public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		//BinaryTree original = new BinaryTree("data0_3");

		//original.toFile("hola.txt");
		BinaryTree tree = new BinaryTree("dataParcial");
		tree.printHierarchy();
		System.out.println("--------------------------");
		tree.removeLeaves();
		tree.printHierarchy();
		System.out.println("--------------------------");



	}

}  