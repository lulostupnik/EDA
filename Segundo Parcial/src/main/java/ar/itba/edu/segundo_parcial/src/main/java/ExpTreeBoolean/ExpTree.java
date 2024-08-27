package ExpTreeBoolean;

import BinaryTree.BinaryTree;

import java.util.Scanner;

public class ExpTree {
    private Node root;

    public ExpTree(String expression) {
        buildTree(expression);
    }

    private void buildTree(String line) {
        // space separator among tokens
        Scanner lineScanner = new Scanner(line).useDelimiter("\\s+");
        root= new Node(lineScanner);
        lineScanner.close();
    }


    public boolean eval(){
        if(root == null) throw new IllegalArgumentException();
        return evalRec(root);
    }

    private boolean evalRec(Node current){
        if(current.left == null && current.right == null){
            return current.data.equals("true");
        }
        return switch (current.data) {
            case "&&" -> evalRec(current.left) && evalRec(current.right);
            case "||" -> evalRec(current.left) || evalRec(current.right);
            case "=>" -> !evalRec(current.left) || evalRec(current.right);
            default -> throw new IllegalArgumentException();
        };
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

        private Node(){}

        private Node buildExpression() {
            Node n = new Node();

            if (lineScanner.hasNext("\\(")) {
                lineScanner.next(); //lo consumo

                n.left = buildExpression(); //subexpresion

                //operator
                if (!lineScanner.hasNext())
                    throw new UnsupportedOperationException("missing or invalid operator");

                n.data = lineScanner.next();
                /*
                if (!isOperator(n.data))
                    throw new UnsupportedOperationException("missing or invalid operator");

                 */
                n.right = buildExpression();

                if (lineScanner.hasNext("\\)"))
                    lineScanner.next();
                else
                    throw new UnsupportedOperationException("missing )");
                return n;
            }

            if (!lineScanner.hasNext())
                throw new UnsupportedOperationException("missing expression");

            n.data = lineScanner.next();
            /*
            if (!isConstant(n.data))
                throw new UnsupportedOperationException(String.format("illegal term %s", lineScanner));

             */
            return n;
        }
    }

    static private boolean isBoolean(String data) {
        if (data.equals("true") || data.equals("false"))
            return true;
        return false;
    }

    public void printHierarchy() {
        printRec(0, root);
    }

    private void printRec(int level, Node current) {
        if(current == null) {
            System.out.println(" ".repeat(level * 5) + "└── " + "null");
            return;
        }
        System.out.println(" ".repeat(level * 5) + "└── " + current.data);
        if(current.left == null && current.right == null){
            return;
        }
        printRec(level + 1, current.left);
        printRec(level + 1, current.right);
    }

    public static void main(String[] args) {
        ExpTree myExp;
        myExp = new ExpTree("( ( true && false ) || true )\n");

        myExp.printHierarchy();
        System.out.println("--------------");
        System.out.println(myExp.eval());

        myExp = new ExpTree("( ( true * false ) || true )\n");
        try {
            System.out.println(myExp.eval());
        }catch (IllegalArgumentException e) {
            System.out.println(e);
        }

        myExp = new ExpTree("( ( true || ( false => false ) ) => ( true => ( true && false ) ) )\n");
        System.out.println(myExp.eval());


    }

}