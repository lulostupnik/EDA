package Trees.Binary_Expression_Tree.src.main.java;

import java.util.Map;
import java.util.Scanner;

public class ExpTreeWithVariables implements ExpressionService{

    private Node root;
    private final Map<String, String> variables;

    public ExpTreeWithVariables(Map<String, String> variables) {
        this.variables = variables;
        System.out.print("Introduzca la expresión en notación infija con todos los paréntesis y blancos: ");

        // token analyzer
        Scanner inputScanner = new Scanner(System.in).useDelimiter("\\n");
        String line = inputScanner.nextLine();
        inputScanner.close();

        buildTree(line);
    }

    public ExpTreeWithVariables() {
        variables = null;
        System.out.print("Introduzca la expresión en notación infija con todos los paréntesis y blancos: ");

        // token analyzer
        Scanner inputScanner = new Scanner(System.in).useDelimiter("\\n");
        String line = inputScanner.nextLine();
        inputScanner.close();

        buildTree(line);
    }

    public boolean modifyVariable(String key, double value) {
        if (variables == null || !variables.containsKey(key)) return false;
        variables.put(key, Double.toString(value));
        return true;
    }

    private void buildTree(String line) {
        // space separator among tokens
        Scanner lineScanner = new Scanner(line).useDelimiter("\\s+");
        root = new Node(lineScanner, variables);
        lineScanner.close();
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

    @Override
    public double eval() {
        return evalRec(root);
    }

    private double evalRec(Node node) {
        if (node.left == null || node.right == null)
            return Utils.getDoubleConstant(getRealData(node.data));

        switch (getRealData(node.data)) {
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

    private String getRealData(String data) {
        return variables.getOrDefault(data, data);
    }

    static final class Node {
        private String data;
        private Node left, right;

        private Scanner lineScanner;
        private final Map<String, String> variables;

        public Node(Scanner theLineScanner, Map<String, String> variables) {
            lineScanner = theLineScanner;
            this.variables = variables;
            Node aux = buildExpression();
            data = aux.data;
            left = aux.left;
            right = aux.right;

            if (lineScanner.hasNext())
                throw new RuntimeException("Bad expression");
        }

        public Node(Map<String, String> variables) {
            this.variables = variables;
        }

        private Node buildExpression() {
            Node node = new Node(variables);

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

            if (!Utils.isConstant(node.data) && (variables == null || !variables.containsKey(node.data)))
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
    }

}
