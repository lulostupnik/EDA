package Parcial.Ej2;

public class BST {
    private Node root;

    public void printHierarchy() {
        printHierarchy("", root);
    }

    public void printHierarchy(String initial, Node current) {
        if (current == null) {
            System.out.println(initial + "└── " + "null");
            return;
        }
        System.out.println(initial + "└── " + current.data);
        if (!current.isLeaf()) {
            printHierarchy(initial + "    ", current.left);
            printHierarchy(initial + "    ", current.right);
        }
    }

    public BST(int arr[], int cantElements) {
        if (arr == null || cantElements == 0)
            root = null;
        else
            root = makeBST(arr, 0, cantElements - 1);
    }

    private Node makeBST(int arr[], int start, int end) {
        if (start > end)
            return null;

        int mid = (start + end) / 2;
        Node aux = new Node(arr[mid]);
        aux.left = makeBST(arr, start, mid - 1);
        aux.right = makeBST(arr, mid + 1, end);

        return aux;
    }

    class Node {
        private Integer data;
        private Node left;
        private Node right;

        private Node(Integer element) {
            data = element;
        }

        private Integer getData() {
            return data;
        }

        private Node getLeft() {
            return left;
        }

        private Node getRight() {
            return right;
        }

        private boolean isLeaf() {
            return left == null && right == null;
        }
    }

    public static void main01(String[] args) {
        int[] n = new int[]{180, 160, 150, 140, 130, 120, 110, 100, 90, 80, 60, 50, 40, 30, 20};
        BST myEmptyTree = new BST(n, n.length);
        myEmptyTree.printHierarchy();
    }

    public static void main(String[] args) {
        int[] param = new int[100];
        for (int i = 0; i < 8; i++)
            param[i] = 100 - i;
        BST myEmptyTree = new BST(param, 8);
        myEmptyTree.printHierarchy();
    }
}
