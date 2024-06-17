package xPractica.Recu2020_1C.Ej1;

import java.io.Serial;

public class OperandException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public OperandException(String message) {
        super(message);
    }
}