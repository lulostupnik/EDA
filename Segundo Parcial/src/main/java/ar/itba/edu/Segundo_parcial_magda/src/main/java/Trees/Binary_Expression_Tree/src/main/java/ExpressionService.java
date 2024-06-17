package Trees.Binary_Expression_Tree.src.main.java;

public interface ExpressionService {

    // lanza exception si no se puede evaluar porque hay algo mal formado en la expresión
    double eval();

    void preorder();

    void inorder();

    void postorder();

}
