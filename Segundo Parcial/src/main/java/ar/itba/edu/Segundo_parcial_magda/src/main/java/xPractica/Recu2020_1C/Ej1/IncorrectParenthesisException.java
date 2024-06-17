package xPractica.Recu2020_1C.Ej1;
import java.io.Serial;

public class IncorrectParenthesisException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public IncorrectParenthesisException(String message) {
        super(message);
    }
}