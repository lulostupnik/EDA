package ar.edu.itba.eda.Tree;

import java.util.Scanner;


public class ExpTree implements ExpressionService {

	private Node root;

	public ExpTree() {
	    System.out.print("Introduzca la expresi�n en notaci�n infija con todos los par�ntesis y blancos: ");

		// token analyzer
	    Scanner inputScanner = new Scanner(System.in).useDelimiter("\\n");
	    String line= inputScanner.nextLine();
	    inputScanner.close();

	    buildTree(line);
	}
	
	private void buildTree(String line) {	
		  // space separator among tokens
		  Scanner lineScanner = new Scanner(line).useDelimiter("\\s+");
		  root= new Node(lineScanner);
		  lineScanner.close();
	}

	@Override
	public String toString() {
		return root.toString();
	}
	


	
	static final class Node {
		private String data;
		private Node left, right;

		private Scanner lineScanner;

		public Node(Scanner theLineScanner) {
			lineScanner = theLineScanner;

			Node auxi = buildExpression();
			data = auxi.data;
			left = auxi.left;
			right = auxi.right;

			if (lineScanner.hasNext())
				throw new RuntimeException("Bad expression");
		}

		private Node() {
		}

//mal (Hacer)
		private Node buildExpression() {
			Node n = new Node();
			if (lineScanner.hasNext("\\(")) {
				lineScanner.next();
				n.left = new Node(lineScanner);
				n.data = lineScanner.next(); //si hay next tira NoSuchElement
				n.right = new Node(lineScanner);
				if (lineScanner.hasNext("\\)"))
					lineScanner.next();
				else
					throw new IncorrectParenthesisException("Faltan )");
			} else if (lineScanner.hasNext("(-?)(0|([1-9][0-9]*))(\\.[0-9]+)?")) {
				n.data = lineScanner.next();
				n.left = null;
				n.right = null;
			} else
				throw new OperandException("Expresion invalida");
			return n;
        }

		@Override
		public String toString() {
			StringBuilder buffer = new StringBuilder(50);
			print(buffer, "", "");
			return buffer.toString();
		}

		private void print(StringBuilder buffer, String prefix, String childrenPrefix) {
			buffer.append(prefix);
			buffer.append(data);
			buffer.append('\n');
			if(left!=null)
				left.print(buffer, childrenPrefix + "├── ", childrenPrefix + "│   ");
			if(right!=null)
				right.print(buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
		}




	}  // end Node class

	
	
	// hasta que armen los testeos
	public static void main(String[] args) {
		ExpressionService myExp = new ExpTree();
		System.out.println(myExp.toString());
	
	}

}  // end ExpTree class
