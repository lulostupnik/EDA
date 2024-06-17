package ar.itba.edu.ExpressionTree_;

import java.util.Scanner;

public class Node{
    private String data;
    private Node left, right;
    private Scanner lineScanner;

    public Node(Scanner theLineScanner){
        lineScanner = theLineScanner;
        Node aux = buildExpression();
        data = aux.data;
        left = aux.left;
        right = aux.right;

        if(lineScanner.hasNext()){
            throw new RuntimeException("Bad Expression");
        }
    }

    private Node(){

    }

    public Node buildExpression(){
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



    // Mi implementación
    /*
    public Node buildExpression(){
        Node expression = new Node();

        if(!lineScanner.hasNext()){
            throw new RuntimeException("Bad Expression");
        }

        String current = lineScanner.next();

        if(isOperand(current)){
            expression.left = expression.right = null;
            expression.data = current;
        }
        else if(current.equals("(")){
            expression.left = buildExpression();
            // check that the next token is an operator
            String next = lineScanner.next(); // should I check hasNext()?
            if(!isOperator(next)){
                throw new RuntimeException("Bad Expression");
            }
            expression.data = next;
            expression.right = buildExpression();
            next = lineScanner.next(); // should I check hasNext()?
            // check that the next token is a closing parenthesis ")"
            if(!next.equals(")")){
                throw new RuntimeException("Bad Expression");
            }
        }

        return expression;
    }

     */




}