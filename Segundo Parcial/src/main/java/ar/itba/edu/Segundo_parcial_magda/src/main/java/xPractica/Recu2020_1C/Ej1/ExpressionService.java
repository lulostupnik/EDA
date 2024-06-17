package xPractica.Recu2020_1C.Ej1;

public interface ExpressionService {

    // lanza exception si no se puede evaluar porque hay algo mal formado en la expresión
    double eval();

    void preorder();

    void inorder();

    void postorder();

}
