package ar.itba.edu.ExpressionTree_;

import java.util.Scanner;


public class ExpTree implements ExpressionService {

    private Node root;
    private String expression;

    public ExpTree(String expression) {
        this.expression = expression;
        buildTree(expression);
    }
    public ExpTree() {
        System.out.print("Introduzca la expresiÛn en notaciÛn infija con todos los parÈntesis y blancos: ");

        // token analyzer
        Scanner inputScanner = new Scanner(System.in).useDelimiter("\\n");
        String line= inputScanner.nextLine();
        inputScanner.close();

        this.expression = line;
        buildTree(expression);
        //buildTree(line);
    }

    private void buildTree(String line) {
        // space separator among tokens
        Scanner lineScanner = new Scanner(line).useDelimiter("\\s+");
        root = new Node(lineScanner);
        lineScanner.close();
    }

    @Override
    public void preorder() {
        root.preorder();
        System.out.println();
    }

    @Override
    public void inorder() {
        root.inorder();
        System.out.println();
    }

    @Override
    public void postorder() {
        root.postorder();
        System.out.println();
    }

    // function that evaluates the expression tree
    @Override
    public double evaluate() {
        return root.evaluate();
    }



    static final class Node {
        private String data;
        private Node left, right;

        private Scanner lineScanner;

        public Node(Scanner theLineScanner) {
            lineScanner= theLineScanner;

            Node aux = buildExpression();
            data = aux.data;
            left = aux.left;
            right = aux.right;

            if (lineScanner.hasNext() )
                throw new RuntimeException("Bad expression");
        }

        // @TODO ¿Hay que hacer algo acá? --> creo que no
        private Node() 	{
        }

        private Node buildExpression() {
            Node n = new Node();

            if(lineScanner.hasNext("\\(")){
                lineScanner.next();

                n.left = buildExpression();

                // operator
                if(!lineScanner.hasNext())
                    throw new RuntimeException("Missing or Invalid Operator");
                n.data = lineScanner.next();
                if(!Utils.isOperator(n.data))
                    throw new RuntimeException("Missing or Invalid Operator");

                n.right = buildExpression();

                if(!lineScanner.hasNext("\\)"))
                    throw new RuntimeException("Missing or Invalid Closing Parenthesis");

                lineScanner.next();
                return n;
            }

            if(!lineScanner.hasNext())
                throw new RuntimeException("Missing Expression");
            n.data = lineScanner.next();
            if(!Utils.isConstant(n.data))
                throw new RuntimeException("Missing or Invalid Operand"); // "illegal termin %s" , lineScanner

            return n;
        }

        private void preorder() {
            System.out.print(this.data + " ");
            if(this.left != null) this.left.preorder();
            if(this.right != null) this.right.preorder();
        }


        private void inorder() {
            if(Utils.isOperator(this.data)) {
                System.out.print("( ");
            }
            if(this.left != null) this.left.inorder();
            System.out.print(this.data + " ");
            if(this.right != null) this.right.inorder();
            if(Utils.isOperator(this.data)) {
                System.out.print(") ");
            }
        }

        private void postorder() {
            if(this.left != null) this.left.postorder();
            if(this.right != null) this.right.postorder();
            System.out.print(this.data + " ");
        }

        private double evaluate() {
            if (Utils.isConstant(this.data))
                return Double.parseDouble(this.data);

            double leftValue = this.left.evaluate();
            double rightValue = this.right.evaluate();

            return switch (this.data) {
                case "+" -> leftValue + rightValue;
                case "-" -> leftValue - rightValue;
                case "*" -> leftValue * rightValue;
                case "/" -> {
                    if (rightValue == 0) {
                        throw new ArithmeticException("Cannot divide by zero");
                    }
                    yield leftValue / rightValue;
                }
                default -> throw new IllegalArgumentException("Invalid operator: " + this.data);
            };


        }
    }  // end Node class



    // hasta que armen los testeos
    public static void main(String[] args) {

        System.out.println("-------------------\nExample:");
        ExpTree myExpTree2 = new ExpTree("( ( 3 + 1 ) * 5 )");
        System.out.println("Resultado de " + "( ( 3 + 1 ) * 5 ): " + myExpTree2.evaluate());
        System.out.println("-------------------:");



        // Create an instance of ExpTree
        ExpTree myExpTree = new ExpTree();

        // Call the preorder, inorder, and postorder methods
        System.out.print("Preorder: ");
        myExpTree.preorder();
        System.out.print("Inorder: ");
        myExpTree.inorder();
        System.out.print("Postorder: ");
        myExpTree.postorder();


        System.out.println("Resultado de la evaluación: " + myExpTree.evaluate());


    }

}

//EJ5
/*
Incorporar la evaluacación de expresiones que contengan variables. Podrían
proporcionarse, por ejemplo, en el constructor.
 */