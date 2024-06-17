package xPractica.Parcial2021_1C.Ej1;
import java.io.IOException;

public interface ParametrizedBinaryTreeService<T> {

    void preorder();

    void postorder();

    void printHierarchy();

    void toFile(String name) throws IOException;

    int getHeight();
}
