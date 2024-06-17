package Trees.Binary_Tree.src.main.java;
import java.io.IOException;

public interface ParametrizedBinaryTreeService<T> {

    void preorder();

    void postorder();

    void printHierarchy();

    void toFile(String name) throws IOException;

    int getHeight();
}
