package BinaryTree;

import java.io.IOException;

public interface BinaryTreeService {
	void preorder();

	void postorder();

	void buildTree() throws IllegalArgumentException, SecurityException ;

	void toFile(String name) throws IOException;

	boolean equals(BinaryTree other);

	int getHeightIter();

	int getHeightRec();
}