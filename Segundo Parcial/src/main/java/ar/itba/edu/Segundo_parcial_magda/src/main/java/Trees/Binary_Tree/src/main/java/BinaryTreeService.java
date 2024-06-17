package Trees.Binary_Tree.src.main.java;
import java.io.IOException;

public interface BinaryTreeService {

    void preorder();

    void postorder();

    void printHierarchy();

    void toFile(String name) throws IOException;

    int getHeight();

}