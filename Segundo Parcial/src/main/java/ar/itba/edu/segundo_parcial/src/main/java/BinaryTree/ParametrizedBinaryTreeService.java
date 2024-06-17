package BinaryTree;

import java.io.IOException;

public interface ParametrizedBinaryTreeService<T extends Comparable<? super T>> {
    void preorder();

    void postorder();

    void buildTree() throws IllegalArgumentException, SecurityException ;

    void toFile(String name) throws IOException;

    boolean equals(ParametrizedBinaryTree<T> other);

    int getHeightIter();

    int getHeightRec();
}
