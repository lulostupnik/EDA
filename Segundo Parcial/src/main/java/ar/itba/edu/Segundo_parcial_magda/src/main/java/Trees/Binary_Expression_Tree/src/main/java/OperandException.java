package Trees.Binary_Expression_Tree.src.main.java;

import java.io.Serial;

public class OperandException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public OperandException(String message) {
        super(message);
    }
}