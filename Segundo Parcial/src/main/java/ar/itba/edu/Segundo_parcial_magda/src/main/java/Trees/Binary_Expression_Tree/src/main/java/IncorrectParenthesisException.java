package Trees.Binary_Expression_Tree.src.main.java;
import java.io.Serial;

public class IncorrectParenthesisException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public IncorrectParenthesisException(String message) {
        super(message);
    }
}