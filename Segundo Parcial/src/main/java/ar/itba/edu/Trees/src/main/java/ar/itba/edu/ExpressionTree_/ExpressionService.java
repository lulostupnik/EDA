package ar.itba.edu.ExpressionTree_;

public interface ExpressionService {

    // throws exception if the expression is not well-formed
    double evaluate();

    // prints the expression in prefix notation
    void preorder();

    // prints the expression in infix notation
    void inorder();

    // prints the expression in postfix notation
    void postorder();

}
