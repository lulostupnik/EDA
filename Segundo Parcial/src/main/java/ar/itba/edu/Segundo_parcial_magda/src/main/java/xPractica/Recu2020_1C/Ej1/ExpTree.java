package xPractica.Recu2020_1C.Ej1;

import java.util.Scanner;

public class ExpTree implements ExpressionService {

    private Node root;

    public ExpTree() {
        System.out.print("Introduzca la expresión en notación infija con todos los paréntesis y blancos: ");

        // token analyzer
        Scanner inputScanner = new Scanner(System.in).useDelimiter("\\n");
        String line = inputScanner.nextLine();
        inputScanner.close();

        buildTree(line);
    }

    private void buildTree(String line) {
        // space separator among tokens
        Scanner lineScanner = new Scanner(line).useDelimiter("\\s+");
        root = new Node(lineScanner);
        lineScanner.close();
    }

    public boolean eval2() {
        if (root != null)
            return evalRec2(root);
        return false;
    }

    public boolean evalRec2(Node node){
        if (node.left == null && node.right == null)
            return node.data.equals("true");

        switch (node.data){
            case "&&":
                return evalRec2(node.left) && evalRec2(node.right);
            case "||":
                return evalRec2(node.left) || evalRec2(node.right);
            case "=>":
                return !evalRec2(node.left) || evalRec2(node.right);
            default:
                throw new IllegalArgumentException("Not a valid operand");
        }
    }


    @Override
    public double eval() {
        return evalRec(root);
    }

    private double evalRec(Node node) {
        if (node.left == null || node.right == null)
            return Utils.getDoubleConstant(node.data);

        switch (node.data) {
            case "+" -> {
                return evalRec(node.left) + evalRec(node.right);
            }
            case "-" -> {
                return evalRec(node.left) - evalRec(node.right);
            }
            case "*" -> {
                return evalRec(node.left) * evalRec(node.right);
            }
            case "/" -> {
                return evalRec(node.left) / evalRec(node.right);
            }
            case "" -> {
                return Math.pow(evalRec(node.left), evalRec(node.right));
            }
            default -> throw new IllegalArgumentException("Not a valid operand");
        }
    }

    @Override
    public void preorder() {
        System.out.println(root.preorder());
    }

    @Override
    public void inorder() {
        System.out.println(root.inorder());
    }

    @Override
    public void postorder() {
        System.out.println(root.postorder());
    }


    static final class Node {
        private String data;
        private Node left, right;

        private Scanner lineScanner;

        public Node(Scanner theLineScanner) {
            lineScanner = theLineScanner;

            Node aux = buildExpression();
            data = aux.data;
            left = aux.left;
            right = aux.right;

            if (theLineScanner.hasNext())
                throw new RuntimeException("Bad expression");
        }

        private Node() {
        }


        private Node buildExpression() {
            Node node = new Node();

            if (lineScanner.hasNext("\\(")) {
                lineScanner.next(); // lo consumo

                node.left = buildExpression();

                if (!lineScanner.hasNext())
                    throw new UnsupportedOperationException("missing or invalid operator");

                node.data = lineScanner.next();

                if (!Utils.isOperator(node.data))
                    throw new UnsupportedOperationException("missing or invalid operator");

                node.right = buildExpression();

                if (lineScanner.hasNext("\\)"))
                    lineScanner.next(); // lo consumo
                else
                    throw new IncorrectParenthesisException("missing )");

                return node;
            }

            // es una constante si llega aca

            if (!lineScanner.hasNext())
                throw new IncorrectParenthesisException("missing expression");

            node.data = lineScanner.next();

            if (!Utils.isConstant(node.data))
                throw new OperandException(String.format("illegal term %s", lineScanner));

            return node;
        }

        public String inorder() {
            if (this.right == null)
                return data;

            return "( " + left.inorder() + " " + data + " " + right.inorder() + " )";
        }

        public String postorder() {
            if (this.right == null)
                return data;

            return left.postorder() + " " + right.postorder() + " " + data;
        }

        public String preorder() {
            if (this.right == null)
                return data;

            return data + " " + left.preorder() + " " + right.preorder();
        }

    }  // end Node class


    // hasta que armen los testeos
    public static void main(String[] args) {
        ExpressionService myExp = new ExpTree();

        myExp.inorder();
        myExp.postorder();
        myExp.preorder();
        System.out.println(myExp.eval());
    }

}  // end ExpTree class
