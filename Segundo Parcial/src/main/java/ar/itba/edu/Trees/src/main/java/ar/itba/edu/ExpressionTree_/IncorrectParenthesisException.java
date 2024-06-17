package ar.itba.edu.ExpressionTree_;

public class IncorrectParenthesisException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public IncorrectParenthesisException(String message) {
        super(message);
    }
}